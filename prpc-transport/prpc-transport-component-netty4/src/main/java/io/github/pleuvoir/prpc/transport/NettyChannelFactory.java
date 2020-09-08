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

import io.github.pleuvoir.prpc.URL;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool.BasePoolableObjectFactory;

/**
 * 池化对象工厂
 *
 * <p>这个对象需要URL相关信息，因此会传递 NettyClient
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@Slf4j
public class NettyChannelFactory extends BasePoolableObjectFactory<NettyChannel> {

  private String factoryName = "";
  private NettyClient nettyClient;

  public NettyChannelFactory(NettyClient nettyClient) {
    super();
    this.nettyClient = nettyClient;
    this.factoryName = "NettyChannelFactory_" + nettyClient.getUrl().getUri();
  }


  @Override
  public String toString() {
    return factoryName;
  }

  @Override
  public NettyChannel makeObject() {
    NettyChannel nettyChannel = new NettyChannel(nettyClient);
    nettyChannel.open();
    return nettyChannel;
  }

  @Override
  public void destroyObject(final NettyChannel nettyChannel) {
    URL url = nettyClient.getUrl();
    try {
      nettyChannel.close();
      log.info(factoryName + " client disconnect success: " + url.getUri());
    } catch (Exception e) {
      log.error(factoryName + " client disconnect error: " + url.getUri(), e);
    }
  }


  @Override
  public boolean validateObject(final NettyChannel nettyChannel) {
    try {
      return nettyChannel.isAvailable();
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public void activateObject(NettyChannel nettyChannel) {
    if (!nettyChannel.isAvailable()) {
      nettyChannel.open();
    }
  }

  @Override
  public void passivateObject(NettyChannel nettyChannel) {
  }
}
