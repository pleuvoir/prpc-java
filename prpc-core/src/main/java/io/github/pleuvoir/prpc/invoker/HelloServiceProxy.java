package io.github.pleuvoir.prpc.invoker;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class HelloServiceProxy implements  HelloService {

    private HelloService helloService;

    public HelloServiceProxy() {
    }

    public HelloServiceProxy(HelloService helloService) {
        this.helloService = helloService;
    }


    // 这里会做修改
    @Override
    public String sayHello(String name) {
        System.out.println("静态代理前 ..");
        helloService.sayHello(name);
        System.out.println("静态代理后 ..");
        return name;
    }

}