package io.github.pleuvoir.prpc.proxy;

import java.lang.reflect.Method;

/**
 * 空实现连接器
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class NoopRpcConnector implements IRpcConnector {

    @Override
    public Object invokeSync(Object o) {
        if(o instanceof RpcContext){
            RpcContext context = (RpcContext) o;
            final RpcRequest rpcRequest = context.getRpcRequest();
            final String impl = rpcRequest.getImpl();
            final String methodName = rpcRequest.getMethodName();
            final Object[] params = rpcRequest.getParams();


            try {
                final Object instance = Class.forName(impl).newInstance();
                System.out.println("实现类 instance={} "+instance);
                System.out.println();
                System.out.println(methodName);
                System.out.println(params);

                final Method method = instance.getClass().getDeclaredMethod(methodName, String.class);
                final Object result = method.invoke(instance, "nihao");
                System.out.println(result);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
