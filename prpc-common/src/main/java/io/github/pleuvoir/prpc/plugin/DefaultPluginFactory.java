package io.github.pleuvoir.prpc.plugin;

import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;

/**
 * 默认的插件工厂实现
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@Slf4j
public class DefaultPluginFactory implements IPluginFactory {

  public static final String DEFAULT_PLUGIN_DIRECTORY = "META-INF/plugins/";

  private String location;

  private Table<Class<?>, String, Object> PLUGIN_INSTANCE_TABLE = Tables
      .newCustomTable(new ConcurrentHashMap<>(), ConcurrentHashMap::new);

  @Override
  public void setLocation(String location) {
    this.location = location;
  }

  @Override
  public synchronized void load() throws Exception {

    File dir = new File(this.location);
    if (!dir.isDirectory() || !dir.exists()) {
      return;
    }

    File[] files = dir.listFiles();
    if (files == null || files.length == 0) {
      return;
    }

    //约定：每个文件名都是接口全路径
    for (File file : files) {
      String interfaceName = file.getName();

      for (String line : Files.readAllLines(Paths.get(file.getPath()))) {
        //跳过注释
        if (line.startsWith("#")) {
          continue;
        }

        String[] pairs = line.split("=");
        String name = pairs[0];
        String impl = pairs[1];

        final Class<?> interfaceClazz = Class.forName(interfaceName);
        Object instance = Class.forName(impl).newInstance();
        PLUGIN_INSTANCE_TABLE.put(interfaceClazz, name, instance);
      }
    }
    log.info("load plugin finish, count size is {}", PLUGIN_INSTANCE_TABLE.size());
  }

  @Override
  public <T> T getOrEmpty(Class<T> clazz, String name) {
    Object instance = PLUGIN_INSTANCE_TABLE.get(clazz, name);
    return instance == null ? null : (T) instance;
  }

}
