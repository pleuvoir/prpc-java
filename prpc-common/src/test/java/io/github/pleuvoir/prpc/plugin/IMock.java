package io.github.pleuvoir.prpc.plugin;

/**
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public interface IMock {

  void say(String text);

  /**
   * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
   */
  class Mock2 implements IMock {

    @Override
    public void say(String text) {
      System.out.println(2 + text);
    }
  }
}
