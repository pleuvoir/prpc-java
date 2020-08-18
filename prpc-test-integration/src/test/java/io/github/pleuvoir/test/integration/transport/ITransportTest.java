package io.github.pleuvoir.test.integration.transport;

import io.github.pleuvoir.prpc.ITransport;
import io.github.pleuvoir.prpc.contract.DefaultContractFactory;
import org.junit.Test;

/**
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class ITransportTest {


  @Test
  public void load() throws Exception {
    DefaultContractFactory factory = new DefaultContractFactory();
    factory.setLocation(DefaultContractFactory.DEFAULT_CONTRACT_DIRECTORY);

    ITransport mock1 = factory.getOrEmpty(ITransport.class, "mock1");

    System.out.println(mock1.echo("hello"));

  }

}
