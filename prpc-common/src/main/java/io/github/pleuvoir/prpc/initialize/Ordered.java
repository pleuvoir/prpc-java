package io.github.pleuvoir.prpc.initialize;

/**
 * 排序
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public interface Ordered {

  int getOrder();

  Integer HIGHEST_LEVEL = Integer.MIN_VALUE;

  Integer LOWEST_LEVEL = Integer.MAX_VALUE;
}