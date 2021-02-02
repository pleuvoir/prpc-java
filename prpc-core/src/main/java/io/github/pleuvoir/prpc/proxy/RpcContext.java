package io.github.pleuvoir.prpc.proxy;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 上下文
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Data
public class RpcContext {

    private RpcRequest rpcRequest;
}
