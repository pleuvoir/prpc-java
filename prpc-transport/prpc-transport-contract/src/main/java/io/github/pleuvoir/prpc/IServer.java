package io.github.pleuvoir.prpc;

import java.util.Collection;

/**
 * Server逻辑抽象
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public interface IServer extends IChannel {

  /**
   * 是否已经绑定端口
   */
  boolean isBound();

  /**
   * 获取所有已经建联的通道
   */
  Collection<IChannel> getChannels();

}
