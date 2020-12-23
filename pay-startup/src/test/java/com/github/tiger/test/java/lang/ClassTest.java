package com.github.tiger.test.java.lang;

public class ClassTest {

    public static void main(String[] args) {

        // 基本类型：true
        System.out.println(byte.class.isPrimitive());

        // 基本类型：false
        System.out.println(byte[].class.isPrimitive());

        // 数组组件类型的 Class : byte
        System.out.println(byte[].class.getComponentType());

        // 基本类型 byte 的 Class 实例 : byte
        System.out.println(Byte.TYPE);

        // true
        System.out.println(byte[].class.getComponentType()
            .equals(Byte.TYPE));

        // 包装类 Byte 的 Class 实例 : class java.lang.Byte
        Class<Byte> byteClass = Byte.class;
        System.out.println(byteClass);

        // 同一个 Class 对象实例
        System.out.println(ClassTest.class == new ClassTest().getClass());
    }

}
