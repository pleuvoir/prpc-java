package io.github.pleuvoir.prpc.proxy;

/**
 * 空实现
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class NoopInvokerListener implements IInvokerListener {

    @Override
    public void preHandlerRequest(RpcContext context) {

    }

    @Override
    public void postHandlerResponse(RpcResponse response) {

    }
}
