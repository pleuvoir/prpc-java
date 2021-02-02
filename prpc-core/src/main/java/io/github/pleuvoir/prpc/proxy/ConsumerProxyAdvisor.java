package io.github.pleuvoir.prpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 完成服务节点寻找，传递给 Invoker 进行网络层调用
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class ConsumerProxyAdvisor implements InvocationHandler {

    private ConsumerInvoker consumerInvoker;

    public ConsumerProxyAdvisor(ConsumerInvoker consumerInvoker) {
        this.consumerInvoker = consumerInvoker;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //服务发现
        final IServiceRegisterDiscover discover = MemoryServiceRegisterDiscover.getInstance();

        final URL url = discover.getURL("test");

        final RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setUrl(url);
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParams(args);
        rpcRequest.setImpl("io.github.pleuvoir.prpc.proxy.HelloImpl");
        rpcRequest.setMethodName("say");

        final RpcContext rpcContext = new RpcContext();
        rpcContext.setRpcRequest(rpcRequest);

        final RpcResponse rpcResponse = consumerInvoker.invoke(rpcContext);

        return rpcResponse;
    }

}
