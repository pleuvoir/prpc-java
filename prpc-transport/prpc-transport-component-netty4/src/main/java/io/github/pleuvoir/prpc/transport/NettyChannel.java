package io.github.pleuvoir.prpc.transport;

import io.github.pleuvoir.prpc.ChannelState;
import io.github.pleuvoir.prpc.IChannel;
import io.github.pleuvoir.prpc.IRequest;
import io.github.pleuvoir.prpc.IResponse;
import io.github.pleuvoir.prpc.URL;
import io.github.pleuvoir.prpc.exception.TransportException;
import io.netty.channel.Channel;
import java.net.InetSocketAddress;
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

  private Channel realNettyChannel = null;

  public NettyChannel(NettyClient nettyClient) {
    this.nettyClient = nettyClient;
    this.remoteAddress = new InetSocketAddress(nettyClient.getUrl().getIp(),
        nettyClient.getUrl().getPort());
  }

  @Override
  public IResponse request(IRequest request) throws TransportException {
    return null;
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
    return this.channelState == ChannelState.ALIVE;
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
    return null;
  }
}
