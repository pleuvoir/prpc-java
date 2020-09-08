package io.github.pleuvoir.prpc.transport;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * 健康检查
 *
 * <p>
 * 当IdleStateHandler 30秒没有读事件会触发 {@link #userEventTriggered(ChannelHandlerContext, Object)}
 * 开启向服务端发送心跳报文的定时任务。
 * <p>
 * 客户端和服务端之间双方维持着心跳，当服务端接收到PING后会回复给客户端PONG，这样双方都是健康的。 当因为某种原因，服务端没有收到客户端PING，超过一定时间后会主动断开连接 这会触发
 * {@link #channelInactive(ChannelHandlerContext)} 方法，从而进行客户端的断线重连。
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@Slf4j
public class HealthyChecker extends SimpleChannelInboundHandler<PongResponse> {

  public static final Integer HEART_BEAT_INTERVAL = 30;

  private final NettyClient nettyClient;

  public HealthyChecker(NettyClient nettyClient) {
    this.nettyClient = nettyClient;
  }

  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    if (evt instanceof IdleStateEvent) {
      sendHeartPacketPeriodicity(ctx);
    } else {
      super.userEventTriggered(ctx, evt);
    }
  }

  private void sendHeartPacketPeriodicity(ChannelHandlerContext ctx) {
    ctx.executor().schedule(() -> {
      if (ctx.channel().isActive()) {
        PingRequest request = new PingRequest();
        ctx.writeAndFlush(request);
        log.debug("[client]发送心跳报文到对端。心跳间隔{}s，requestId={}", HEART_BEAT_INTERVAL,
            request.getRequestId());
        this.sendHeartPacketPeriodicity(ctx);
      }
    }, HEART_BEAT_INTERVAL, TimeUnit.SECONDS);
  }

  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext,
      PongResponse pongResponse) throws Exception {
    log.debug("[client]接收到服务端心跳响应。response requestId={} ", pongResponse.getRequestId());
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    if (nettyClient.isAvailable()) {
      ctx.executor().schedule(() -> {
        log.info("[{}] Try to reconnecting...", HealthyChecker.class.getSimpleName());
        nettyClient.doConnectWithRetry(ctx.channel().remoteAddress(), 1);
      }, 5, TimeUnit.SECONDS);
      ctx.fireChannelInactive();
    }
  }

}
