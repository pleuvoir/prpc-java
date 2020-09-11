package io.github.pleuvoir.prpc.transport.server;

import io.github.pleuvoir.prpc.ChannelState;
import io.github.pleuvoir.prpc.IChannel;
import io.github.pleuvoir.prpc.IRequest;
import io.github.pleuvoir.prpc.IResponse;
import io.github.pleuvoir.prpc.IServer;
import io.github.pleuvoir.prpc.URL;
import io.github.pleuvoir.prpc.exception.TransportException;
import java.net.InetSocketAddress;
import java.util.Collection;

/**
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public abstract class AbstractNettyServer implements IServer {

  protected InetSocketAddress localAddress;
  protected InetSocketAddress remoteAddress;

  protected URL url;

  protected volatile ChannelState channelState = ChannelState.UN_INIT;


  @Override
  public Collection<IChannel> getChannels() throws TransportException {
    throw new TransportException("unSupport method");
  }

  @Override
  public IResponse request(IRequest request) throws TransportException {
    throw new TransportException("unSupport method");
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

  @Override
  public void close() {
    close(0);
  }

  @Override
  public boolean isClosed() {
    return channelState == ChannelState.CLOSED;
  }

  @Override
  public boolean isAvailable() {
    return channelState == ChannelState.ALIVE;
  }
}
