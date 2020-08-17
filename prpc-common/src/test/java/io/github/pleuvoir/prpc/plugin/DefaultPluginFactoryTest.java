package io.github.pleuvoir.prpc.plugin;


import org.junit.Test;

/**
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class DefaultPluginFactoryTest {

  @Test
  public void load() throws Exception {
    DefaultPluginFactory pluginFactory = new DefaultPluginFactory();

    pluginFactory.setLocation("D:\\space\\git\\prpc-java\\prpc-common\\src\\test\\resources\\META-INF\\plugins");
    pluginFactory.load();

    IMock mock1 = pluginFactory.getOrEmpty(IMock.class, "mock1");

    mock1.say("hello");

    IMock mock2 = pluginFactory.getOrEmpty(IMock.class, "mock2");
    mock2.say("hello");
  }
}