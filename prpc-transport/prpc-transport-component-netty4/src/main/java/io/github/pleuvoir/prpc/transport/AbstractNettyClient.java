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

import io.github.pleuvoir.prpc.ChannelState;
import io.github.pleuvoir.prpc.IEndpoint;
import io.github.pleuvoir.prpc.URL;
import java.net.InetSocketAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public abstract class AbstractNettyClient implements IEndpoint {

  protected final Logger logger = LoggerFactory.getLogger(this.getClass());

  protected InetSocketAddress localAddress;
  protected InetSocketAddress remoteAddress;
  protected URL url;

  protected volatile ChannelState channelState = ChannelState.UN_INIT;

  public AbstractNettyClient(URL url) {
    this.url = url;
  }

  @Override
  public InetSocketAddress getLocalAddress() {
    return this.localAddress;
  }

  @Override
  public InetSocketAddress getRemoteAddress() {
    return this.remoteAddress;
  }

  @Override
  public URL getUrl() {
    return this.url;
  }

}
