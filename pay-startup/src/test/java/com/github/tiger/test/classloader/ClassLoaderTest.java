package com.github.tiger.test.classloader;

import java.lang.reflect.Method;
import java.net.URL;

public class ClassLoaderTest {

    public static void main(String[] args) throws Exception {
        URL[] urls = new URL[1];
        urls[0] = Thread.currentThread().getContextClassLoader().getResource("");

        CustomClassLoader customClassLoader = new CustomClassLoader(urls);
        Thread.currentThread().setContextClassLoader(customClassLoader);

        Class clazz = customClassLoader.loadClass("com.github.tiger.test.java.NullTest");

        /**
         * 反例：
         *
         * NullTest nullTest = (NullTest) clazz.newInstance();
         * nullTest.hehe();
         *
         * 强转会抛出 java.lang.ClassCastException 异常
         *
         * 因为强转的类型 NullTest.class 对应的 InstanceKlass 是由系统默认的 ClassLoader 生成的。
         *
         * 正例：
         */
        Object object = clazz.newInstance();
        Method method = clazz.getDeclaredMethod("hehe");
        method.invoke(object);
    }

}
