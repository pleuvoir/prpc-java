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

import com.google.common.base.Preconditions;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 单例抽象
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class SingletonAbstract<T> implements ISingleton<T> {

  private final AtomicBoolean init = new AtomicBoolean(false);

  private T impl;

  @Override
  public void set(T instance) {
    Preconditions.checkArgument(instance != null);
    boolean b = init.compareAndSet(false, true);
    if (!b) {
      throw new IllegalStateException(String.format("%s 已经初始化，请勿重复设置。", instance.getClass()));
    }
    impl = instance;
  }

  @Override
  public T getInstance() {
    if (init.get()) {
      return impl;
    }
    throw new IllegalStateException(String.format("%s 暂未设置，请先进行设置操作。", this.getClass()));
  }
}
