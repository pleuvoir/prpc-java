package io.github.pleuvoir.prpc.tookit;

import io.github.pleuvoir.prpc.IRequest;

/**
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class MixUtils {

  public static void sleep(Integer sec) {
    if (sec == null || sec == 0) {
      return;
    }
    try {
      Thread.sleep(sec * 1000);
    } catch (InterruptedException ignored) {
    }
  }

  /**
   * 输出请求信息
   */
  public static String toString(IRequest request) {
    StringBuilder builder = new StringBuilder();
    builder.append(" requestId=").append(request.getRequestId()).append(" interface=")
        .append(request.getInterfaceName()).append(" method=").append(request.getMethodName());
    return builder.toString();
  }

}
