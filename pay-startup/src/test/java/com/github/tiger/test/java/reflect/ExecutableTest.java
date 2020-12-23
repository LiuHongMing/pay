package com.github.tiger.test.java.reflect;

import java.lang.reflect.Executable;
import java.lang.reflect.Parameter;

/**
 * Executable：方法和构造函数的通用功能的共享超类。
 */
public class ExecutableTest {

    public static void main(String[] args) throws NoSuchMethodException {
        Executable demo = ExecutableTest.class.getMethod("demo",
                CharSequence.class, CharSequence[].class);
        Parameter[] parameters = demo.getParameters();
        for (Parameter parameter : parameters) {
            System.out.println(parameter.getName());
        }
    }

    public void demo(CharSequence delimiter, CharSequence... element) {

    }
}
