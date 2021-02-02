package io.github.pleuvoir.prpc.proxy;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface IInvokerListener {

    void preHandlerRequest(RpcContext context);

    void postHandlerResponse(RpcResponse response);

}
