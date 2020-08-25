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
package io.github.pleuvoir.prpc.contract;

import com.google.common.base.Preconditions;
import io.github.pleuvoir.prpc.exception.PRpcRuntimeException;
import io.github.pleuvoir.prpc.tookit.ISingleton;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * 契约工厂单例
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public enum DefaultContractFactorySingleton implements ISingleton<DefaultContractFactory> {

  INSTANCE;

  private AtomicBoolean init = new AtomicBoolean(false);

  private DefaultContractFactory impl;

  @Override
  public void set(DefaultContractFactory instance) {
    Preconditions.checkArgument(instance != null);

    boolean b = init.compareAndSet(false, true);
    if (!b) {
      throw new PRpcRuntimeException("{} 已经初始化，请勿重复设置。", instance.getClass());
    }
    impl = instance;
  }

  @Override
  public DefaultContractFactory getInstance() {
    if (impl == null) {
      throw new IllegalStateException(String.format("%s 未初始化，请设置后再调用。", this.getClass()));
    }
    return DefaultContractFactorySingleton.INSTANCE.impl;
  }
}
