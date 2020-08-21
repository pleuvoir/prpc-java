package io.github.pleuvoir.prpc.tookit;

/**
 * 单例接口
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface ISingleton<T> {

    void set(T instance);

    T getInstance();

    default boolean autoRegisterFactory() {
        return true;
    }

}
