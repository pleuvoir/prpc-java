package io.github.pleuvoir.prpc.proxy;

/**
 * 远程接口代理实现
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface IRpcProxy {

    RpcResponse invoke(RpcContext context);
}
