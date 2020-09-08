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