package com.github.tiger.pay.test.cglib;

import java.lang.reflect.Modifier;

/**
 * Cglib基于继承类生成委托类的代理对象
 * <p>
 * 对于final类的修改无法使用Cglib来生成代理对象
 */
public class CglibClient {

    public static void main(String[] args) {
        CglibDynamicProxy cglib = new CglibDynamicProxy();
        CglibRealObject realObject = (CglibRealObject) cglib.getProxyInstance(new CglibRealObject());
        realObject.go();
        System.out.println(realObject.visit());

        System.out.println(CglibRealObject.class.getModifiers());
        System.out.println(CglibRealObject.class.getModifiers() & Modifier.FINAL);
        System.out.println("public: " + Modifier.isPublic(CglibRealObject.class.getModifiers()));
        System.out.println("final: " + Modifier.isFinal(CglibRealObject.class.getModifiers()));
    }

}
