package io.github.pleuvoir.prpc;

import lombok.Getter;

/**
 * 通道生命周期状态
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public enum ChannelState {

  /**
   * 未初始化
   */
  UN_INIT(-1),

  /**
   * 已初始化
   */
  INIT(0),

  /**
   * 存活
   */
  ALIVE(1),

  /**
   * 未存活
   */
  UN_ALIVE(2),

  /**
   * 已关闭
   */
  CLOSED(3);

  @Getter
  public final int value;

  private ChannelState(int value) {
    this.value = value;
  }
}
