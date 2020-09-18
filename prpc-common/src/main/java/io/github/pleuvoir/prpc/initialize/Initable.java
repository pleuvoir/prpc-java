package io.github.pleuvoir.prpc.initialize;

/**
 * 代表可初始化
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public interface Initable extends Ordered {

  void init();

}