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
package io.github.pleuvoir.prpc.transport;

import io.github.pleuvoir.prpc.IRequest;
import java.util.Map;

/**
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class DefaultRequest implements IRequest {

  @Override
  public String getInterfaceName() {
    return null;
  }

  @Override
  public String getMethodName() {
    return null;
  }

  @Override
  public Object[] getArguments() {
    return new Object[0];
  }

  @Override
  public Map<String, String> getAttachments() {
    return null;
  }

  @Override
  public long getRequestId() {
    return 0;
  }

  @Override
  public void setTimeout(long milliseconds) {

  }

  @Override
  public long timeoutMillis() {
    return 0;
  }
}
