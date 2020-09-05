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

import java.util.Map;

/**
 * Request Interface
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public interface IRequest {

  /**
   * 接口名称
   */
  String getInterfaceName();

  /**
   * 方法名称
   */
  String getMethodName();

  /**
   * 方法入参
   */
  Object[] getArguments();

  /**
   * 额外传递的参数
   */
  Map<String, String> getAttachments();

  /**
   * 请求traceID
   */
  long getRequestId();

  /**
   * 设置超时时间
   */
  void setTimeout(long milliseconds);

  /**
   * 获取超时时间
   */
  long timeoutMillis();

}
