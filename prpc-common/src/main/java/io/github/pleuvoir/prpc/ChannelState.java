/*
 * Copyright © 2020 pleuvoir (pleuvior@foxmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
