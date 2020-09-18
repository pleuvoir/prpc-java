package io.github.pleuvoir.prpc.initialize;

import com.google.common.collect.Lists;
import io.github.pleuvoir.prpc.GlobalConst;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

/**
 * 扫包初始化器实现
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@Slf4j
public class Initializer {

  private static AtomicBoolean init = new AtomicBoolean(false);

  public static void init() {
    if (!init.compareAndSet(false, true)) {
      return;
    }
    Reflections packageInfo = new Reflections(GlobalConst.ALL_SCANNER_PATH);
    Set<Class<? extends Initable>> subs = packageInfo.getSubTypesOf(Initable.class);
    if (subs.isEmpty()) {
      return;
    }

    List<Initable> initables = Lists.newArrayList();
    for (Class<? extends Initable> clazz : subs) {
      if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())
          || !Initable.class.isAssignableFrom(clazz)) {
        continue;
      }
      try {
        Constructor<? extends Initable> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        Initable initable = clazz.newInstance();
        initables.add(initable);
      } catch (Throwable e) {
        log.warn("[Initializer] Init failed with fatal error", e);
        throw new RuntimeException(e);
      }
    }

    log.info("[Initializer] Found Initable=[{}]", Collections.singletonList(initables));

    initables.sort(new OrderComparator());

    for (Initable initable : initables) {
      initable.init();
    }
  }

}