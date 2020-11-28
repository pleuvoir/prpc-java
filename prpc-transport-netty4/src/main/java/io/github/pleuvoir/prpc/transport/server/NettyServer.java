package io.github.pleuvoir.prpc.transport.server;

import io.github.pleuvoir.prpc.ChannelState;
import io.github.pleuvoir.prpc.tookit.SingletonFactoy;
import io.github.pleuvoir.prpc.transport.codec.RemoteCommandCodecHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@Slf4j
public class NettyServer extends AbstractNettyServer {


  protected List<Thread> shutdownHooks = new ArrayList<>();
  protected EventLoopGroup bossGroup;
  protected EventLoopGroup workGroup;
  private ServerBootstrap bootstrap;

  private Channel serverChannel;


  @Override
  public boolean open() {

    this.bootstrap = new ServerBootstrap();
    this.bossGroup = new NioEventLoopGroup();
    this.workGroup = new NioEventLoopGroup();

    bootstrap.group(bossGroup, workGroup)
        .localAddress(new InetSocketAddress(url.getPort()))
        .channel(NioServerSocketChannel.class)
        // .option(ChannelOption.SO_BACKLOG, serverConfig.getSoBacklog())
        .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
        .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
        .childOption(ChannelOption.SO_KEEPALIVE, true).childOption(ChannelOption.TCP_NODELAY, true)
        .childHandler(new ChannelInitializer<SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 12, 4));
            ch.pipeline().addLast(SingletonFactoy.get(RemoteCommandCodecHandler.class));
            //   ch.pipeline().addLast(new ServerIdleStateHandler(serverConfig.getAllIdleTime()));
            //     ch.pipeline().addLast(new NettyServerHandler());
          }
        });

    //TODO akka

    ChannelFuture channelFuture = bootstrap.bind(new InetSocketAddress(url.getPort()));
    channelFuture.syncUninterruptibly();
    serverChannel = channelFuture.channel();
    channelState = ChannelState.ALIVE;
    return channelState == ChannelState.ALIVE;
  }

  @Override
  public void close(int timeout) {
    if (channelState == ChannelState.CLOSED) {
      return;
    }

    // close listen socket
    if (serverChannel != null) {
      serverChannel.close();
    }
    if (bossGroup != null) {
      bossGroup.shutdownGracefully();
      bossGroup = null;
    }
    if (workGroup != null) {
      workGroup.shutdownGracefully();
      workGroup = null;
    }
  }

  @Override
  public boolean isBound() {
    return Objects.nonNull(serverChannel) && serverChannel.isActive();
  }

}
