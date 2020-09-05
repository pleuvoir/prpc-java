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

import io.github.pleuvoir.prpc.exception.TransportException;
import java.net.InetSocketAddress;

/**
 * 通道
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public interface IChannel {

  /**
   * 发送请求
   */
  IResponse request(IRequest request) throws TransportException;

  /**
   * 获取本地地址
   */
  InetSocketAddress getLocalAddress();

  /**
   * 获取远程地址，当为Client时存在
   */
  InetSocketAddress getRemoteAddress();

  /**
   * 开启连接，对客户端是连接，对服务端是开放端口
   */
  boolean open();

  /**
   * 关闭连接
   */
  void close();

  void close(int timeout);

  boolean isClosed();

  boolean isAvailable();

  URL getUrl();

}
