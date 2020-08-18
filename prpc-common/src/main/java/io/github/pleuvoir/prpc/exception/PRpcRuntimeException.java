package io.github.pleuvoir.prpc.exception;

/**
 * 运行时异常
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class PRpcRuntimeException extends RuntimeException {

  public PRpcRuntimeException() {
  }

  public PRpcRuntimeException(String message) {
    super(message);
  }

  public PRpcRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  public PRpcRuntimeException(Throwable cause) {
    super(cause);
  }

}
