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

import com.google.common.collect.Maps;
import java.util.Map;

/**
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class DefaultRequest {

  private String interfaceName = "";
  private String methodName = "";
  private String requestDesc = "";
  private Object[] arguments = new Object[]{};
  private long requestId = 0;
  private Map<String, String> attachments = Maps.newHashMap();

  public String getRequestDesc() {
    return requestDesc;
  }

  public void setRequestDesc(String requestDesc) {
    this.requestDesc = requestDesc;
  }

  public String getInterfaceName() {
    return interfaceName;
  }

  public void setInterfaceName(String interfaceName) {
    this.interfaceName = interfaceName;
  }

  public String getMethodName() {
    return methodName;
  }

  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }

  public Object[] getArguments() {
    return arguments;
  }

  public void setArguments(Object[] arguments) {
    this.arguments = arguments;
  }

  public long getRequestId() {
    return requestId;
  }

  public void setRequestId(long requestId) {
    this.requestId = requestId;
  }

  public Map<String, String> getAttachments() {
    return attachments;
  }

  public void setAttachments(Map<String, String> attachments) {
    this.attachments = attachments;
  }
}
