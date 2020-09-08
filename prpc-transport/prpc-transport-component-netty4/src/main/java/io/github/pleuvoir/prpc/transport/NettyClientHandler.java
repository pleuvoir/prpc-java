package io.github.pleuvoir.prpc.transport;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 处理客户端收到的请求
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<Object> {

  @Override
  protected void channelRead0(ChannelHandlerContext context,
      Object object) throws Exception {

    if (object instanceof DefaultRequest) {
      DefaultRequest request = (DefaultRequest) object;

      this.processRequest(context, request);
    } else if (object instanceof DefaultResponse) {
      DefaultResponse response = (DefaultResponse) object;
      this.processResponse(context, response);
    }
  }


  private void processRequest(ChannelHandlerContext context, DefaultRequest request) {

  }

  private void processResponse(ChannelHandlerContext context, DefaultResponse response) {

  }

}
