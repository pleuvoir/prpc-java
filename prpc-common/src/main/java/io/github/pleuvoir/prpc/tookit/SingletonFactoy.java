package io.github.pleuvoir.prpc.tookit;

import com.google.common.collect.Maps;
import java.util.concurrent.ConcurrentMap;

/**
 * 单例工厂
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class SingletonFactoy {

  static ConcurrentMap<Class<?>, Object> caches = Maps.newConcurrentMap();

  @SuppressWarnings("unchecked")
  public static <T> T get(Class<T> t) {
    Object object = caches.get(t);
    if (object == null) {
      throw new IllegalStateException(t + " not register!");
    }
    return (T) object;
  }

  public static <T> void register(Class<T> t, T obj) {
    Object prev = caches.putIfAbsent(t, obj);
    if (prev != null) {
      throw new IllegalStateException(t + " has already register!");
    }
  }

}
