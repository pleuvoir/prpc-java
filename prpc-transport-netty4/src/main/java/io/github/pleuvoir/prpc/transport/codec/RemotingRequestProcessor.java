package io.github.pleuvoir.prpc.transport.codec;

import io.netty.channel.ChannelHandlerContext;

/**
 * 远程命令处理器
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public interface RemotingRequestProcessor<REQ extends RemotingCommand, RSP extends RemotingCommand> {

  RSP handler(ChannelHandlerContext ctx, REQ request);
}