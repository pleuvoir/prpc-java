package io.github.pleuvoir.prpc.proxy;

/**
 * 服务注册与发现
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface IServiceRegisterDiscover {

    void register(String serviceName, URL url);

    void unregister(String serviceName);

    URL getURL(String serviceName);

}
