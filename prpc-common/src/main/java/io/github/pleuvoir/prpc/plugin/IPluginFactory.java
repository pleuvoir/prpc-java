package io.github.pleuvoir.prpc.plugin;

/**
 * 插件工厂
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public interface IPluginFactory {


  /**
   * 设置扫描位置
   */
  void setLocation(String location);

  /**
   * 加载插件
   */
  void load() throws Exception;

  /**
   * 获取具体的实现类，获取失败时返回null
   */
  <T> T getOrEmpty(Class<T> clazz, String name);

}
