package io.github.pleuvoir.prpc.proxy;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class HelloImpl implements IHello {

    @Override
    public String say(String name) {
        return "名字：" + name;
    }
}
