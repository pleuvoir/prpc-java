package io.github.pleuvoir.prpc;

import java.util.Map;

/**
 * Request Interface
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public interface IRequest {

  /**
   * 接口名称
   */
  String getInterfaceName();

  /**
   * 方法名称
   */
  String getMethodName();


  /**
   * 方法入参
   */
  Object[] getArguments();

  /**
   * 额外传递的参数
   */
  Map<String, String> getAttachments();

  /**
   * 请求traceID
   */
  long getRequestId();

}
