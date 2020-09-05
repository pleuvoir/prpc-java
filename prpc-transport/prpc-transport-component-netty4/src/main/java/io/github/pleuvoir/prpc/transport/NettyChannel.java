/*
 * Copyright © 2020 pleuvoir (pleuvior@foxmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.pleuvoir.prpc.transport;

import com.google.common.base.Stopwatch;
import io.github.pleuvoir.prpc.ChannelState;
import io.github.pleuvoir.prpc.IChannel;
import io.github.pleuvoir.prpc.IRequest;
import io.github.pleuvoir.prpc.IResponse;
import io.github.pleuvoir.prpc.URL;
import io.github.pleuvoir.prpc.exception.TransportException;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * Netty Channel不区分Client or Server side
 *
 * <p>这个类传递了 NettyClient的引用，说白了是一种代码结构的问题。NettyClient完成了Netty的初始化，而具体的
 * 请求模型可以由该类实现，从而屏蔽复杂性。
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@Slf4j
public class NettyChannel implements IChannel {

  private volatile ChannelState channelState = ChannelState.UN_INIT;

  private NettyClient nettyClient;
  private InetSocketAddress remoteAddress;
  private InetSocketAddress localAddress;

  private Channel realNettyChannel;

  /**
   * 保存正在处理的消息请求<br> 因为Netty是以异步发送，所以发送后接受到的响应通过此寻找之前的对应关系
   */
  private final ConcurrentHashMap<Long /* messageId */, RemotingFuture> pending =
      new ConcurrentHashMap<>(256);

  public NettyChannel(NettyClient nettyClient) {
    this.nettyClient = nettyClient;
    this.remoteAddress = new InetSocketAddress(nettyClient.getUrl().getIp(),
        nettyClient.getUrl().getPort());
  }

  @Override
  public IResponse request(IRequest request) throws TransportException {
    long messageId = request.getRequestId();
    long timeoutMillis = request.timeoutMillis();
    final RemotingFuture remotingFuture = new RemotingFuture(messageId, timeoutMillis, null);
    // 保存请求响应对应关系
    pending.put(messageId, remotingFuture);
    //异步写入
    this.realNettyChannel.writeAndFlush(request).addListener((ChannelFutureListener) future -> {
      if (future.isSuccess()) {
        remotingFuture.setSendRequestOK(true);
      } else {
        remotingFuture.setSendRequestOK(false);
        remotingFuture.setCause(future.cause());
        remotingFuture.completeResponse(null); // 通知发送端等待结束
        log.warn("send a request command to channel <" + NettyChannel.this.realNettyChannel
            .remoteAddress() + "> failed.");
      }
    });
    // 同步等待响应
    IResponse response = remotingFuture.waitResponse(timeoutMillis);
    if (response == null) {
      if (remotingFuture.isSendRequestOK()) {
        // 如果发送成功了还返回null，只能是等待对端响应超时了
        throw new TransportException("response return null");
      } else {
        // 发送失败
        throw new TransportException();
      }
    }
    return response;
  }

  @Override
  public InetSocketAddress getLocalAddress() {
    return this.localAddress;
  }

  @Override
  public InetSocketAddress getRemoteAddress() {
    return this.remoteAddress;
  }

  @Override
  public synchronized boolean open() {
    if (isAvailable()) {
      log.warn("NettyChannel already open,uri={}", this.getUrl().getUri());
      return true;
    }
    Stopwatch stopwatch = Stopwatch.createStarted();
    ChannelFuture future = nettyClient.getBootstrap().connect(remoteAddress);
    //不带超时时间，其实可以加上
    future.awaitUninterruptibly();
    if (future.isSuccess()) {
      this.realNettyChannel = future.channel();
      this.channelState = ChannelState.ALIVE;
      log.info("客户端连接{}完成，耗时：{}ms", this.getUrl().getUri(),
          stopwatch.elapsed(TimeUnit.MILLISECONDS));
      return true;
    }
    return false;
  }


  @Override
  public synchronized void close() {
    close(0);
  }

  @Override
  public synchronized void close(int timeout) {
    if (channelState == ChannelState.CLOSED) {
      log.warn("realNettyChannel already closed,uri={}", this.getUrl().getUri());
      return;
    }
    channelState = ChannelState.CLOSED;
    if (realNettyChannel != null) {
      try {
        realNettyChannel.close();
      } catch (Exception e) {  //防御
        log.error("close realNettyChannel Error：", e);
      }
    }
  }

  @Override
  public boolean isClosed() {
    return this.channelState == ChannelState.CLOSED;
  }

  @Override
  public boolean isAvailable() {
    return this.channelState == ChannelState.ALIVE;
  }

  @Override
  public URL getUrl() {
    return this.nettyClient.getUrl();
  }
}
