package io.github.pleuvoir.prpc;

import io.github.pleuvoir.prpc.exception.TransportException;
import java.net.InetSocketAddress;

/**
 * 通道
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public interface IChannel {

  /**
   * 发送请求
   */
  IResponse request(IRequest request) throws TransportException;

  /**
   * 获取本地地址
   */
  InetSocketAddress getLocalAddress();

  /**
   * 获取远程地址，当为Client时存在
   */
  InetSocketAddress getRemoteAddress();

  /**
   * 开启连接，对客户端是连接，对服务端是开放端口
   */
  boolean open();

  /**
   * 关闭连接
   */
  void close();

  void close(int timeout);

  boolean isClosed();

  boolean isAvailable();

  URL getUrl();

}
