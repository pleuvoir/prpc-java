package io.github.pleuvoir.prpc.tookit;

import com.google.common.collect.Maps;
import io.github.pleuvoir.prpc.contract.DefaultContractFactorySingleton;
import java.util.Map;

/**
 * 单例注册类工厂
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@SuppressWarnings("all")
public class SingletonFactory {

    private static Map<Class<?>, ISingleton> INSTANCES = Maps.newConcurrentMap();


    public static <T> T get(Class<DefaultContractFactorySingleton> o) {
        ISingleton singleton = INSTANCES.get(o);
        Object instance = singleton.getInstance();
        return (T) instance;
    }


    public static void register(Object o) {
        INSTANCES.put(o.getClass(), (ISingleton) o);
    }

}
