package io.github.pleuvoir.prpc.contract;

import com.google.common.base.Preconditions;
import io.github.pleuvoir.prpc.exception.PRpcRuntimeException;
import io.github.pleuvoir.prpc.tookit.ISingleton;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public enum DefaultContractFactorySingleton2 implements ISingleton<DefaultContractFactory> {

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
        return DefaultContractFactorySingleton2.INSTANCE.impl;
    }
}
