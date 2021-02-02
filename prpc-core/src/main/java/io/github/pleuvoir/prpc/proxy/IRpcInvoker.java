package io.github.pleuvoir.prpc.proxy;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface IRpcInvoker {

    RpcResponse invoke(RpcContext context);
}
