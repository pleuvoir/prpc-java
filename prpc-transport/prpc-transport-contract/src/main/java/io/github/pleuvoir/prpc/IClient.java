package io.github.pleuvoir.prpc;

/**
 * Client逻辑抽象
 *
 * <p>客户端在应用层实现心跳
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public interface IClient extends IChannel {

  /**
   * 发送心跳报文
   */
  void heartbeat(IRequest request);
}
