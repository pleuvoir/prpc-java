package io.github.pleuvoir.prpc.proxy;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class ConsumerTest {

    public static void main(String[] args) {

        final MemoryServiceRegisterDiscover discover = MemoryServiceRegisterDiscover.getInstance();

        final URL url = new URL();
        url.setPort(4399);
        url.setHost("127.0.0.1");

        discover.register("test", url);

        final ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setServiceInterface(IHello.class);
        final Consumer consumer = new Consumer(consumerConfig);

        final IHello ref = consumer.ref();

        System.out.println("获取代理对象：" +  ref);

        ref.say("pleuvoir");
    }
}
