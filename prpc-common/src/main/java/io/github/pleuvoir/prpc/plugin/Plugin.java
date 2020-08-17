package io.github.pleuvoir.prpc.plugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Plugin 标记，支持 <name, value> 的形式
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Plugin {

  /**
   * 名称
   */
  String name() default "";

}
