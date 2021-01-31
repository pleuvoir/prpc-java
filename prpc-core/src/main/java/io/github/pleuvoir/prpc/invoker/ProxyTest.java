package io.github.pleuvoir.prpc.invoker;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import javassist.bytecode.SignatureAttribute;
import javassist.bytecode.SignatureAttribute.ClassSignature;
import javassist.bytecode.SignatureAttribute.TypeParameter;
import javax.sound.midi.Soundbank;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class ProxyTest {


    public static void main(String[] args) throws Exception {

//        final HelloServiceProxy serviceProxy = new HelloServiceProxy(new HelloService() {
//            @Override
//            public String sayHello(String name) {
//                System.out.println("目标接口实现：name=" + name);
//                return "null";
//            }
//        });
//
//        serviceProxy.sayHello("pleuvoir");

        //创建类，这是一个单例对象

     //   final ClassPool classPool = new ClassPool(true);
        ClassPool pool = ClassPool.getDefault();
        pool.appendClassPath(Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath());
        //我们需要构建的类
        CtClass ctClass = pool.makeClass("io.github.pleuvoir.prpc.invoker.HelloServiceJavassistProxy");
        //这个类实现了哪些接口

        ctClass.setInterfaces(new CtClass[]{
                pool.getCtClass("io.github.pleuvoir.prpc.invoker.HelloService"),
                pool.getCtClass("io.github.pleuvoir.prpc.invoker.IProxy")});

        //新增字段
        CtField field$name = new CtField(pool.get("io.github.pleuvoir.prpc.invoker.HelloService"), "helloService", ctClass);
        //设置访问级别
        field$name.setModifiers(Modifier.PRIVATE);
        ctClass.addField(field$name);

        //新增构造函数
        //无参构造函数
        CtConstructor cons$noParams = new CtConstructor(new CtClass[]{}, ctClass);
        cons$noParams.setBody("{}");
        ctClass.addConstructor(cons$noParams);

        //重写sayHello方方法，可以通过构造字符串的形式
        CtMethod m = CtNewMethod.make(buildSayHello(), ctClass);
        ctClass.addMethod(m);


        // 创建一个名为 setProxy 的方法
        CtMethod ctMethod = new CtMethod(CtClass.voidType, "setProxy",
                new CtClass[]{pool.getCtClass("java.lang.Object")}, ctClass);
        ctMethod.setModifiers(Modifier.PUBLIC);
        // // $0=this  $1,$2,$3... 代表方法参数
        ctMethod.setBody("{$0.helloService =   $1;}");
        ctClass.addMethod(ctMethod);

        ctClass.writeFile(Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath());

        //获取实例对象
        final Object instance = ctClass.toClass().newInstance();

        System.out.println(Arrays.toString(instance.getClass().getDeclaredMethods()));
        //设置目标方法
        if (instance instanceof IProxy) {
            IProxy proxy = (IProxy) instance;
            proxy.setProxy(new HelloService() {
                @Override
                public String sayHello(String name) {
                    System.out.println("目标接口实现：name=" + name);
                    return "null";
                }
            });
        }

        if (instance instanceof HelloService) {
            HelloService service = (HelloService) instance;
            service.sayHello("pleuvoir");
        }

    }

    private static String buildSayHello() {
        String methodString = "   public String sayHello(String name) {\n"
                + "        System.out.println(\"静态代理前 ..\");\n"
                + "        helloService.sayHello(name);\n"
                + "        System.out.println(\"静态代理后 ..\");\n"
                + "        return name;\n"
                + "    }";
        return methodString;
    }
}
