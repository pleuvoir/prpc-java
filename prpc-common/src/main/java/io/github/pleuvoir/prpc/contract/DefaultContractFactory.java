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

import com.google.common.base.Stopwatch;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import io.github.pleuvoir.prpc.exception.PRpcRuntimeException;
import io.github.pleuvoir.prpc.tookit.ClassUtils;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 默认的契约工厂实现
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@Slf4j
public class DefaultContractFactory implements IContractFactory {

  public static final String DEFAULT_CONTRACT_DIRECTORY = "META-INF/contracts";

  private String location;

  private Table<Class<?>, String, Object> CONTRACT_INSTANCE_TABLE = Tables
      .newCustomTable(new ConcurrentHashMap<>(), ConcurrentHashMap::new);

  private final AtomicBoolean loaded = new AtomicBoolean(false);

  @Override
  public void setLocation(String location) {
    this.location = location;
  }

  @Override
  public void load() throws Exception {
    if (!loaded.compareAndSet(false, true)) {
      log.error("DefaultContractFactory has already loaded.");
      return;
    }

    log.info("load contracts begin, start @{}", System.currentTimeMillis());

    Stopwatch stopwatch = Stopwatch.createStarted();

    this.resetLocation();

    //约定：每个文件名都是接口全路径
    for (Path filePath : ClassUtils.findAllClassRootFilesPath(location)) {
      log.info("loop load contract path={}", filePath.toAbsolutePath());

      for (String line : Files.readAllLines(filePath)) {
        String[] pairs = line.split("=");
        String name = pairs[0];
        String impl = pairs[1];

        final Class<?> interfaceClazz = ClassUtils.forName(filePath.toFile().getName());
        //必须要有注解
        this.checkContract(interfaceClazz);

        Object instance = ClassUtils.newInstanceNoConstructor(impl);
        CONTRACT_INSTANCE_TABLE.put(interfaceClazz, name, instance);
      }
    }

    loaded.set(true);
    log.info("load contracts end, cost {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
  }

  private void resetLocation() {
    if (StringUtils.isBlank(location)) {
      log.warn("not found contract location, use default location: {}", location);
      this.location = DEFAULT_CONTRACT_DIRECTORY;
    }
  }

  /**
   * 强制使用注解标记，统一规范
   */
  private void checkContract(Class<?> clazz) {
    if (clazz.getAnnotation(Contract.class) == null) {
      log.error("try to load contract from class {}, but not found @Contract.", clazz);
      throw new PRpcRuntimeException("must use @Contract bind class {}", clazz);
    }
  }

  private Object getInner(Class<?> clazz, String name) {
    return this.CONTRACT_INSTANCE_TABLE.get(clazz, name);
  }

  @Override
  public <T> T getOrEmpty(Class<T> clazz, String name) {
    Object previous = this.getInner(clazz, name);
    if (previous != null) {
      return ClassUtils.cast(clazz, previous);
    }
    return null;
  }

}
