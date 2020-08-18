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

import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import io.github.pleuvoir.prpc.exception.PRpcRuntimeException;
import io.github.pleuvoir.prpc.tookit.ClassUtils;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;

/**
 * 默认的契约工厂实现
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@Slf4j
public class DefaultContractFactory implements IContractFactory {

  public static final String DEFAULT_CONTRACT_DIRECTORY = "META-INF/contracts/";

  private String location;

  private Table<Class<?>, String, Object> CONTRACT_INSTANCE_TABLE = Tables
      .newCustomTable(new ConcurrentHashMap<>(), ConcurrentHashMap::new);

  @Override
  public void setLocation(String location) {
    this.location = location;
  }

  /**
   * 加载当前类所在契约包{@link #setLocation(String)}
   */
  private <T> void loadFromClass(Class<T> clazz) throws Exception {
    if (clazz.getAnnotation(Contract.class) == null) {
      log.error("try to load contract from class {}, but not found @Contract.", clazz);
      return;
    }

    URL url = ClassUtils.getClassLoader(clazz).getResource(this.location);
    if (url == null) {
      log.error("try to load contract from class {}, but url is null.", clazz);
      return;
    }

    //绝对路径
    String path = url.getPath();
    log.info("load contract, path={}", path);

    File dir = new File(path);
    if (!dir.isDirectory() || !dir.exists()) {
      return;
    }

    File[] files = dir.listFiles();
    if (files == null || files.length == 0) {
      return;
    }

    //约定：每个文件名都是接口全路径
    for (File file : files) {
      String interfaceName = file.getName();

      for (String line : Files.readAllLines(Paths.get(file.getPath()))) {
        if (line.startsWith("#")) {
          continue;
        }

        String[] pairs = line.split("=");
        String name = pairs[0];
        String impl = pairs[1];

        final Class<?> interfaceClazz = ClassUtils.forName(interfaceName);
        Object instance = ClassUtils.newInstanceNoConstructor(impl);
        CONTRACT_INSTANCE_TABLE.put(interfaceClazz, name, instance);
      }
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

    synchronized (this) {
      try {
        // lock after double check
        Object next = this.getInner(clazz, name);
        if (next != null) {
          return ClassUtils.cast(clazz, next);
        }

        this.loadFromClass(clazz);
      } catch (Exception e) {
        throw new PRpcRuntimeException(e);
      }
    }

    Object current = this.getInner(clazz, name);
    if (current == null) {
      log.error("get contract but return null, clazz={}, name={}", clazz, name);
    }
    return current == null ? null : ClassUtils.cast(clazz, current);
  }

}
