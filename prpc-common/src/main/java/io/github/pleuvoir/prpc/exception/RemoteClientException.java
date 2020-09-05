package io.github.pleuvoir.prpc.exception;

/**
 * 远程客户端异常
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class RemoteClientException extends RuntimeException {

  private static final long serialVersionUID = -7486436135397777942L;

  public RemoteClientException() {
    super();
  }

  public RemoteClientException(String message, Throwable cause) {
    super(message, cause);
  }

  public RemoteClientException(String message) {
    super(message);
  }

  public RemoteClientException(Throwable cause) {
    super(cause);
  }

}