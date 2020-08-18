package io.github.pleuvoir.prpc.transport;

import io.github.pleuvoir.prpc.ITransport;

/**
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class DefaultTransport implements ITransport {

  @Override
  public String echo(String text) {
    return text;
  }
}