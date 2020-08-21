package io.github.pleuvoir.prpc.tookit;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public enum  SingletonAbstract2 implements ISingleton<Object>{

    INSTANCE;

    @Override
    public void set(Object instance) {

    }

    @Override
    public Object getInstance() {
        return null;
    }
}
