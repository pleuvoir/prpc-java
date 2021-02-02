package io.github.pleuvoir.prpc.proxy;

/**
 * 远程接口代理工厂
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface RpcProxyFactory {

    IRpcProxy getProxy(String lookup);

}
