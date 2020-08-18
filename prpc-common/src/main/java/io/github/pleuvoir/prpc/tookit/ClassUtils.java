package io.github.pleuvoir.prpc.tookit;

import io.github.pleuvoir.prpc.exception.PRpcRuntimeException;

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

}