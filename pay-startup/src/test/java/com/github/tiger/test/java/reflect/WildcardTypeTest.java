package com.github.tiger.test.java.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.WildcardType;
import java.util.List;

/**
 * @className: WildcardTypeTest
 * @description: TODO
 * @author liuhongming
 * @date 2020/09/18
 * @version
 */
public class WildcardTypeTest {

    // a没有下界, 取下界会抛出ArrayIndexOutOfBoundsException
    private List<? extends Number> a;
    private List<? super String> b;

    public static void main(String[] args) throws Exception {
        Field fieldA = WildcardTypeTest.class.getDeclaredField("a");
        Field fieldB = WildcardTypeTest.class.getDeclaredField("b");
        // 先拿到范型类型
        System.out.println(fieldA.getGenericType() instanceof ParameterizedType);
        System.out.println(fieldB.getGenericType() instanceof ParameterizedType);
        ParameterizedType pTypeA = (ParameterizedType) fieldA.getGenericType();
        ParameterizedType pTypeB = (ParameterizedType) fieldB.getGenericType();
        // 再从范型里拿到通配符类型
        System.out.println(pTypeA.getActualTypeArguments()[0] instanceof WildcardType);
        System.out.println(pTypeB.getActualTypeArguments()[0] instanceof WildcardType);
        WildcardType wTypeA = (WildcardType) pTypeA.getActualTypeArguments()[0];
        WildcardType wTypeB = (WildcardType) pTypeB.getActualTypeArguments()[0];
        // 方法测试
        // class java.lang.Number
        System.out.println(wTypeA.getUpperBounds()[0]);
        // class java.lang.String
        System.out.println(wTypeB.getLowerBounds()[0]);
        // 看看通配符类型到底是什么, 打印结果为: ? extends java.lang.Number
        System.out.println(wTypeA);
        System.out.println(wTypeB);
    }

}
