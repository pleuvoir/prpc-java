package io.github.pleuvoir.prpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class Consumer {

    private ConsumerConfig consumerConfig;

    private Object proxy;

    public Consumer(ConsumerConfig consumerConfig) {
        this.consumerConfig = consumerConfig;
    }

    /**
     * 动态代理从注册中心引用一个消费者
     */
    @SuppressWarnings("unchecked")
    public <T> T ref() {
        if (proxy != null) {
            return (T) proxy;
        }
        proxy = getProxy(consumerConfig.getServiceInterface(),
                new ConsumerProxyAdvisor(new ConsumerInvoker()));
        return (T) proxy;
    }


    @SuppressWarnings({"unchecked"})
    public <T> T getProxy(Class<T> serviceInterface, InvocationHandler h) {
        return (T) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{serviceInterface}, h);
    }
}
