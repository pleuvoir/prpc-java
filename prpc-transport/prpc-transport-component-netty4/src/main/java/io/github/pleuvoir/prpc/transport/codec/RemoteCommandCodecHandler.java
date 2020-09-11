package io.github.pleuvoir.prpc.transport.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@ChannelHandler.Sharable
public class RemoteCommandCodecHandler extends MessageToMessageCodec<ByteBuf, RemotingCommand> {

  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) {
    RemotingCommand response = RemoteCommandCodecHelper.decode(byteBuf);
    out.add(response);
  //  LOG.debug("解码成功。{}", JSON.toJSONString(response));
  }

  @Override
  protected void encode(ChannelHandlerContext ctx, RemotingCommand request, List<Object> out) {
    ByteBuf byteBuf = ctx.channel().alloc().ioBuffer();
    RemoteCommandCodecHelper.encode(byteBuf, request);
    out.add(byteBuf);
  //  LOG.debug("编码成功。{}", JSON.toJSONString(request));
  }

  private static final Logger LOG = LoggerFactory.getLogger(RemoteCommandCodecHandler.class);
}