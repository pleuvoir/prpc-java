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

import io.github.pleuvoir.prpc.exception.PRpcRuntimeException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

/**
 * 类帮助
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class ClassUtils {

  /**
   * 获取类加载器
   * <p>首先尝试获取当前线程的类加载器，若未找到继续获取加载类的类加载器，若还未找到返回系统类加载器
   */
  public static ClassLoader getClassLoader(Class<?> clazz) {
    ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
    if (contextClassLoader != null) {
      return contextClassLoader;
    }
    ClassLoader classLoader = clazz.getClassLoader();
    if (classLoader != null) {
      return classLoader;
    }
    return ClassLoader.getSystemClassLoader();
  }

  /**
   * 获取默认类加载器
   */
  public static ClassLoader getDefaultClassLoader() {
    return getClassLoader(ClassUtils.class);
  }


  @SuppressWarnings("unchecked")
  public static <A> A cast(Class<A> clazz, Object instance) {
    return (A) instance;
  }

  public static Object newInstanceNoConstructor(String clazzName) {
    try {
      return Class.forName(clazzName).newInstance();
    } catch (Exception e) {
      throw new PRpcRuntimeException(e);
    }
  }

  public static Class<?> forName(String clazzName) {
    try {
      return Class.forName(clazzName);
    } catch (Exception e) {
      throw new PRpcRuntimeException(e);
    }
  }

  /**
   * 获取所有ClassPath下路径文件夹，依赖JAR包中的ClassPath也会被扫到
   */
  public static Set<URL> findAllClassRootDirResources(String path) throws IOException {
    Set<URL> result = new LinkedHashSet<>(16);
    Enumeration<URL> resourceUrls = getDefaultClassLoader().getResources(path);
    while (resourceUrls.hasMoreElements()) {
      URL url = resourceUrls.nextElement();
      result.add(url);
    }
    return result;
  }

  /**
   * 获取所有ClassPath下文件，依赖JAR包中的ClassPath也会被扫到
   */
  public static Set<Path> findAllClassRootFilesPath(String path) throws IOException {
    Set<URL> result = new LinkedHashSet<>(16);
    Enumeration<URL> resourceUrls = getDefaultClassLoader().getResources(path);
    while (resourceUrls.hasMoreElements()) {
      URL url = resourceUrls.nextElement();
      result.add(url);
    }
    Set<Path> fileSets = new LinkedHashSet<>(16);
    for (URL folder : result) {
      String folderPath = folder.getPath();
      if (StringUtils.startsWith(folderPath, "/")) {
        folderPath = folderPath.substring(1);
      }
      Set<Path> paths = listFile(Paths.get(folderPath));
      fileSets.addAll(paths);
    }
    return fileSets;
  }

  /**
   * 列出路径下所有格式的文件
   */
  public static Set<Path> listFile(Path path) {
    return listFile(path, "*.*");
  }

  /**
   * 列出路径下所有格式的文件
   */
  public static Set<Path> listFile(URL folder) {
    return listFile(Paths.get(folder.getPath()), "*.*");
  }

  /**
   * 列出路径下所有格式的文件
   */
  public static Set<Path> listFile(URL folder, String pattern) {
    return listFile(Paths.get(folder.getPath()), pattern);
  }

  /**
   * 列出路径下满足格式的文件
   */
  public static Set<Path> listFile(Path path, String pattern) {
    Set<Path> result = new LinkedHashSet<>(16);
    // 列出指定目录下的所有文件，不会递归
    try (DirectoryStream<Path> newDirectoryStream = Files.newDirectoryStream(path, pattern);) {
      for (Path filePath : newDirectoryStream) {
        result.add(filePath);
      }
    } catch (IOException e) {
      throw new PRpcRuntimeException(e);
    }
    return result;
  }
}