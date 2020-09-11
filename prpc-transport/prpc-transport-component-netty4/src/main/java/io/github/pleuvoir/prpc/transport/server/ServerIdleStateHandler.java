package io.github.pleuvoir.prpc.transport.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务端空闲事件处理，当服务端出现空闲时，代表N个客户端空闲心跳周期未触发通信，说明客户端已经断开，可以直接关闭连接
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class ServerIdleStateHandler extends IdleStateHandler {

  private static final Logger LOG = LoggerFactory.getLogger(ServerIdleStateHandler.class);

  private final long allIdleTime;

  public ServerIdleStateHandler(long allIdleTime) {
    super(0, 0, allIdleTime, TimeUnit.SECONDS);
    this.allIdleTime = allIdleTime;
  }

  @Override
  public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
    LOG.info("服务端{}秒内未发生读写，关闭连接", allIdleTime);
    ctx.channel().close();
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    ctx.close();
    LOG.error("ctx close, cause:", cause);
  }

}