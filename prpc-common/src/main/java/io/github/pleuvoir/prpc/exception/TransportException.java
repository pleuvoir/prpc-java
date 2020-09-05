package io.github.pleuvoir.prpc.exception;

/**
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class TransportException extends Exception{

  public TransportException() {
  }

  public TransportException(String message) {
    super(message);
  }

  public TransportException(String message, Throwable cause) {
    super(message, cause);
  }

  public TransportException(Throwable cause) {
    super(cause);
  }

  public TransportException(String message, Object... args) {
    super(String.format(message, args));
  }
}
