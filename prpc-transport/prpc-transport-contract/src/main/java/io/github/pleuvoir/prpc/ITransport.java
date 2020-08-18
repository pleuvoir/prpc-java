package io.github.pleuvoir.prpc;

import io.github.pleuvoir.prpc.contract.Contract;

/**
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@Contract
public interface ITransport {

  String echo(String text);

}
