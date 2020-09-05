package io.github.pleuvoir.prpc.transport;

import io.github.pleuvoir.prpc.ChannelState;
import io.github.pleuvoir.prpc.IEndpoint;
import io.github.pleuvoir.prpc.URL;
import java.net.InetSocketAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public abstract class AbstractNettyClient implements IEndpoint {

  protected final Logger logger = LoggerFactory.getLogger(this.getClass());

  protected InetSocketAddress localAddress;
  protected InetSocketAddress remoteAddress;
  protected URL url;

  protected volatile ChannelState channelState = ChannelState.UN_INIT;

  public AbstractNettyClient(URL url) {
    this.url = url;
  }

  @Override
  public InetSocketAddress getLocalAddress() {
    return this.localAddress;
  }

  @Override
  public InetSocketAddress getRemoteAddress() {
    return this.remoteAddress;
  }

  @Override
  public URL getUrl() {
    return this.url;
  }

}
