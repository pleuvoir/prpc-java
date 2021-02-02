package io.github.pleuvoir.prpc.proxy;

/**
 * 发起远程调用
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class ConsumerInvoker implements IRpcInvoker {


    private final NoopInvokerListener invokerListener = new NoopInvokerListener();
    private final NoopRpcConnector rpcConnector = new NoopRpcConnector();

    @Override
    public RpcResponse invoke(RpcContext context) {

        invokerListener.preHandlerRequest(context);

        final Object o = rpcConnector.invokeSync(context);

        final RpcResponse response = new RpcResponse();
        response.setData(o);

        invokerListener.postHandlerResponse(response);

        return response;
    }
}
