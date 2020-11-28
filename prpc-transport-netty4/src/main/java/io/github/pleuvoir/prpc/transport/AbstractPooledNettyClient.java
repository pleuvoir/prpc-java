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
import io.github.pleuvoir.prpc.exception.PRpcRuntimeException;
import java.util.Objects;
import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;

/**
 * 池化客户端
 *
 * <p>基于 apache.commons.pool
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public abstract class AbstractPooledNettyClient extends AbstractNettyClient {

  protected GenericObjectPool pool;
  protected GenericObjectPool.Config poolConfig;
  protected PoolableObjectFactory factory;

  public AbstractPooledNettyClient(URL url) {
    super(url);
  }

  /**
   * 初始化连接池
   */
  private void initPool() {
    this.poolConfig = new GenericObjectPool.Config();
    //设置池参数 TODO

    this.factory = createChannelFactory();

    this.pool = new GenericObjectPool(factory, poolConfig);

    //初始化最小空闲连接数
    for (int i = 0; i < this.poolConfig.minIdle; i++) {
      try {
        this.pool.addObject();
      } catch (Exception e) {
        //打个日志就行，其实还可以设置告警或者重试策略，比如到达3/4阈值
        logger.error("AbstractPooledNettyClient create minIdle Error,", e);
      }
    }
  }


  /**
   * 创建由子类完成<br>
   *
   */
  protected abstract BasePoolableObjectFactory<NettyChannel> createChannelFactory();

  /**
   * 获取一个连接
   */
  protected IChannel borrowObject() throws Exception {
    IChannel channel = (IChannel) pool.borrowObject();
    if (Objects.nonNull(channel) && channel.isAvailable()) {
      return channel;
    }
    //如果状态异常则尝试失效
    invalidateObject(channel);

    String errorMessage =
        this.getClass().getSimpleName() + " borrowObject Error: url={}" + url.getUri();
    logger.error(errorMessage);
    throw new PRpcRuntimeException(errorMessage);
  }

  /**
   * 失效一个连接
   */
  protected void invalidateObject(IChannel channel) {
    if (channel == null) {
      return;
    }
    try {
      this.pool.invalidateObject(channel);
    } catch (Exception e) {
      logger.error("invalidate client Error: url={}", url.getUri(), e);
    }
  }

  /**
   * 归还一个连接
   */
  protected void returnObject(IChannel channel) {
    if (channel == null) {
      return;
    }
    try {
      this.pool.returnObject(channel);
    } catch (Exception e) {
      logger.error("return client Error: url={}", url.getUri(), e);
    }
  }

}
