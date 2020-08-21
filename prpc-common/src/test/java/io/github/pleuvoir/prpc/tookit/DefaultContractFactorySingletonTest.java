package io.github.pleuvoir.prpc.tookit;

import io.github.pleuvoir.prpc.contract.DefaultContractFactory;
import io.github.pleuvoir.prpc.contract.DefaultContractFactorySingleton;
import org.junit.Test;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class DefaultContractFactorySingletonTest {

    @Test
    public void test() {
        DefaultContractFactorySingleton singleton = new DefaultContractFactorySingleton();
        singleton.set(DefaultContractFactory.INSTANCE);

        DefaultContractFactory o = SingletonFactory.get(DefaultContractFactorySingleton.class);

        System.out.println(o);
    }
}
