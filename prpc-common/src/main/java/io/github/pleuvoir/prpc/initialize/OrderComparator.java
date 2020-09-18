package io.github.pleuvoir.prpc.initialize;

import java.util.Comparator;

/**
 * 排序比较
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class OrderComparator implements Comparator<Ordered> {

  @Override
  public int compare(Ordered o1, Ordered o2) {
    int i1 = (o1 instanceof Ordered ? o1.getOrder() : Integer.MAX_VALUE);
    int i2 = (o2 instanceof Ordered ? o2.getOrder() : Integer.MAX_VALUE);
    return Integer.compare(i1, i2);
  }

}
