package io.github.pleuvoir.prpc.invoker;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class JavassistInvoker {


    public static void main(String[] args) throws Exception {
        //创建类，这是一个单例对象
        ClassPool pool = ClassPool.getDefault();
        //我们需要构建的类
        CtClass ctClass = pool.makeClass("io.github.pleuvoir.prpc.invoker.Person");

        //新增字段
        CtField field$name = new CtField(pool.get("java.lang.String"), "name", ctClass);
        //设置访问级别
        field$name.setModifiers(Modifier.PRIVATE);
        //也可以给个初始值
        ctClass.addField(field$name, CtField.Initializer.constant("pleuvoir"));
        //生成get/set方法
        ctClass.addMethod(CtNewMethod.setter("setName", field$name));
        ctClass.addMethod(CtNewMethod.getter("getName", field$name));

        //新增构造函数
        //无参构造函数
        CtConstructor cons$noParams = new CtConstructor(new CtClass[]{}, ctClass);
        cons$noParams.setBody("{name = \"pleuvoir\";}");
        ctClass.addConstructor(cons$noParams);
        //有参构造函数
        CtConstructor cons$oneParams = new CtConstructor(new CtClass[]{pool.get("java.lang.String")}, ctClass);
        // $0=this  $1,$2,$3... 代表方法参数
        cons$oneParams.setBody("{$0.name = $1;}");
        ctClass.addConstructor(cons$oneParams);

        // 创建一个名为 print 的方法，无参数，无返回值，输出name值
        CtMethod ctMethod = new CtMethod(CtClass.voidType, "print", new CtClass[]{}, ctClass);
        ctMethod.setModifiers(Modifier.PUBLIC);
        ctMethod.setBody("{System.out.println(name);}");
        ctClass.addMethod(ctMethod);

//        //当前工程的target目录，会自动在目录下根据包名类名生成文件
//        final String targetClassPath = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath();
//
//        //生成.class文件
//        ctClass.writeFile(targetClassPath);

//        final Object person = ctClass.toClass().newInstance();
//        System.out.println(person);
//
//        // 输出值
//        Method printMethod = person.getClass().getMethod("print");
//        printMethod.invoke(person);
//
//        // 设置值
//        Method setName = person.getClass().getMethod("setName", String.class);
//        setName.invoke(person, "hehe");
//
//        // 再次输出值
//        printMethod.invoke(person);

        //如果生成的类没有放在classpath下需要自己指定类加载器加载的位置，否则加载不到。这里我们不需要设置
      //  pool.appendClassPath(Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath());
        final CtClass loadCtClass = pool.get("io.github.pleuvoir.prpc.invoker.Person");
        loadCtClass.toClass().newInstance();

    }
}
