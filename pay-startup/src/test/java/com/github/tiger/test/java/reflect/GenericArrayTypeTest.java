package com.github.tiger.test.java.reflect;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @className: GenericArrayTypeTest
 * @description: TODO
 * @author liuhongming
 * @date 2020/09/18
 * @version
 */
public class GenericArrayTypeTest {

    public static void main(String[] args) {
        Method method = Demo.class.getDeclaredMethods()[0];
        // public void com.test.Test.show(java.util.List[],java.lang.Object[],java.util.List,java.lang.String[],int[])
        System.out.println(method);
        // 这是 Method 中的方法
        Type[] types = method.getGenericParameterTypes();
        for (Type type : types) {
            System.out.println(type);
            if(type instanceof GenericArrayType){
                System.out.println(((GenericArrayType) type).getGenericComponentType());
            }
            System.out.println("**********");
        }
    }

    class Demo<T> {
        public void show(List<String>[] pTypeArray, T[] vTypeArray, List<String> list, String[] strings, int[] ints) {
        }
    }
}
