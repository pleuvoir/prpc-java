package io.github.pleuvoir.prpc.transport;

import io.github.pleuvoir.prpc.ChannelState;
import io.github.pleuvoir.prpc.IChannel;
import io.github.pleuvoir.prpc.IRequest;
import io.github.pleuvoir.prpc.IResponse;
import io.github.pleuvoir.prpc.URL;
import io.github.pleuvoir.prpc.exception.RemoteClientException;
import io.github.pleuvoir.prpc.exception.TransportException;
import io.github.pleuvoir.prpc.tookit.MixUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;
import org.apache.commons.pool.BasePoolableObjectFactory;

/**
 * 客户端通信均采用连接池的形式
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class NettyClient extends AbstractPooledNettyClient {


  private Bootstrap bootstrap;
  private NioEventLoopGroup eventLoopGroup;

  public NettyClient(URL url) {
    super(url);
  }

  @Override
  protected BasePoolableObjectFactory createChannelFactory() {
    return null;
  }

  @Override
  public IResponse request(IRequest request) throws TransportException {
    //检查连接状态
    if (!isAvailable()) {
      throw new TransportException("NettyChannel is unavaliable: message=%s",
          url.getUri() + MixUtils.toString(request));
    }
    //发送实际请求
    return null;
  }

  private IResponse request(IRequest request, boolean async){
    IChannel channel = null;

    IResponse response = null;

    //从连接池拿连接
    try {
      channel = this.borrowObject();
      if(channel == null){
        throw new TransportException("borrowObject return null: message=%s",
            url.getUri() + MixUtils.toString(request));
      }



    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }


  @Override
  public synchronized boolean open() {
    bootstrap = new Bootstrap();
    eventLoopGroup = new NioEventLoopGroup();

    bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 500) //TODO
        .option(ChannelOption.SO_KEEPALIVE, true)
        .option(ChannelOption.TCP_NODELAY, true)
        .handler(new ChannelInitializer<SocketChannel>() {

          @Override
          protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 12, 4));
            //  ch.pipeline().addLast(SingletonFactoy.get(RemoteCommandCodecHandler.class));
            ch.pipeline()
                .addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS)); // 30秒没有读事件发送心跳到服务端
            //   ch.pipeline().addLast(new HealthyChecker(SimpleClient.this));
            //   ch.pipeline().addLast(new NettyClientHandler());
          }
        });

    return false;
  }

  @Override
  public synchronized void close() {
    if (channelState == ChannelState.CLOSED) {
      logger.warn("already closed,uri={}", this.getUrl().getUri());
      return;
    }

    if (channelState == ChannelState.UN_INIT) {
      logger.warn("channel not init ,uri={}", this.getUrl().getUri());
      return;
    }

    try {
      // 关闭连接池
      pool.close();
      // 改状态
      channelState = ChannelState.CLOSED;
    } catch (Exception e) {
      logger.error("NettyClient close Error: uri={}", url.getUri(), e);
    }
  }

  @Override
  public synchronized void close(int timeout) {
    throw new RemoteClientException("unSupport method.");
  }

  @Override
  public boolean isClosed() {
    return channelState == ChannelState.CLOSED;
  }

  @Override
  public boolean isAvailable() {
    return channelState == ChannelState.ALIVE;
  }


  /**
   * 带重试的连接
   *
   * @param remoteAddress 远端地址
   * @param retryTimes    重试次数
   */
  protected ChannelFuture doConnectWithRetry(SocketAddress remoteAddress, int retryTimes) {
    ChannelFuture future = bootstrap.connect(remoteAddress);
    future.awaitUninterruptibly(); // connect不可以使用sync会报错
    if (future.isSuccess()) {
      logger.info("客户端已连接远程节点：[{}]", future.channel().remoteAddress());
      return future;
    } else if (retryTimes == 0) {
      logger.error("客户端连接远程节点{}：尝试重连到达上限，不再进行连接。原因：{}", remoteAddress, future.cause().getMessage());
      throw new RemoteClientException(future.cause().getMessage());
    } else {
      // 第几次重连
      int sequence = 10 - retryTimes + 1;  //TODO 10改配置
      int delay = 1 << sequence;
      logger.warn("客户端连接远程节点第{}次连接失败，{} {}秒后尝试重试。", sequence, future.cause().getMessage(), delay);
      MixUtils.sleep(delay);
      return doConnectWithRetry(remoteAddress, retryTimes - 1);
    }
  }

}
