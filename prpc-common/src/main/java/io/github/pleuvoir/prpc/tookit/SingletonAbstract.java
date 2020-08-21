package io.github.pleuvoir.prpc.tookit;

import com.google.common.base.Preconditions;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 单例抽象
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class SingletonAbstract<T> implements ISingleton<T> {

    private AtomicBoolean inited = new AtomicBoolean(false);

    private T impl;

    @Override
    public void set(T instance) {
        Preconditions.checkArgument(instance != null);
        boolean b = inited.compareAndSet(false, true);
        if (!b) {
            throw new IllegalStateException(String.format("%s 已经初始化，请勿重复设置。", instance.getClass()));
        }
        impl = instance;
        if (autoRegisterFactory()) {
            SingletonFactory.register(impl);
        }
    }

    @Override
    public T getInstance() {
        if (inited.get()) {
            return impl;
        }
        throw new IllegalStateException(String.format("%s 暂未设置，请先进行设置操作。", this.getClass()));
    }
}
