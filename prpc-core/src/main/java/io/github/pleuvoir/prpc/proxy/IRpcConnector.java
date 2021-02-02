package io.github.pleuvoir.prpc.proxy;

/**
 * 连接器
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public interface IRpcConnector {

    Object invokeSync(Object o);

}
