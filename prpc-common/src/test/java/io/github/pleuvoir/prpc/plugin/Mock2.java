package io.github.pleuvoir.prpc.plugin;

/**
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class Mock2 implements IMock {

  @Override
  public void say(String text) {
    System.out.println(2 + text);
  }
}
