package io.github.pleuvoir.prpc.initialization;

import io.github.pleuvoir.prpc.initialize.Initable;
import io.github.pleuvoir.prpc.tookit.SingletonFactoy;
import io.github.pleuvoir.prpc.transport.codec.RemoteCommandCodecHandler;
import io.github.pleuvoir.prpc.transport.codec.RemoteCommandFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * 初始化框架
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@Slf4j
public class FrameworkInit implements Initable {

  @Override
  public void init() {
    log.info("FrameworkInit init.");

    // 注册编码器
    SingletonFactoy.register(RemoteCommandCodecHandler.class, new RemoteCommandCodecHandler());

    // 加载远程命令工厂
    RemoteCommandFactory factory = new RemoteCommandFactory();
    SingletonFactoy.register(RemoteCommandFactory.class, factory);
  }



  @Override
  public int getOrder() {
    return HIGHEST_LEVEL;
  }
}
