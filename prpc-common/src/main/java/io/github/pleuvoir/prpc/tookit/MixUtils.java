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
package io.github.pleuvoir.prpc.tookit;

import io.github.pleuvoir.prpc.IRequest;

/**
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class MixUtils {

  public static void sleep(Integer sec) {
    if (sec == null || sec == 0) {
      return;
    }
    try {
      Thread.sleep(sec * 1000);
    } catch (InterruptedException ignored) {
    }
  }

  /**
   * 输出请求信息
   */
  public static String toString(IRequest request) {
    StringBuilder builder = new StringBuilder();
    builder.append(" requestId=").append(request.getRequestId()).append(" interface=")
        .append(request.getInterfaceName()).append(" method=").append(request.getMethodName());
    return builder.toString();
  }

}
