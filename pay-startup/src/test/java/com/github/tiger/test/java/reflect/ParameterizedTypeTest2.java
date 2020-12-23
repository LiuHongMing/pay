package com.github.tiger.test.java.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author liuhongming
 * @className: ParameterizedTypeTest2
 * @description: TODO
 * @date 2020/09/18
 */
public class ParameterizedTypeTest2 {

    Map<String, String> map;

    public static void main(String[] args) throws Exception {
        Field declaredField = ParameterizedTypeTest2.class.getDeclaredField("map");
        Type genericType = declaredField.getGenericType();
        // java.util.Map<java.lang.String, java.lang.String>
        System.out.println(genericType);
        // true
        System.out.println(genericType instanceof ParameterizedType);

        ParameterizedType pType = (ParameterizedType) declaredField.getGenericType();
        // interface java.util.Map
        System.out.println(pType.getRawType());
        for (Type type : pType.getActualTypeArguments()) {
            // 打印两遍: class java.lang.String
            System.out.println(type.getTypeName());
        }
        // null
        System.out.println(pType.getOwnerType());

        ParameterizedTypeDemoImpl instance = new ParameterizedTypeDemoImpl();
        Type superclass = instance.getClass().getGenericSuperclass();
        // com.zhaopin.op.mofang.third.service.type.ParameterizedTypeTest2
        // .com.zhaopin.op.mofang.third.service.type.ParameterizedTypeTest2$ParameterizedTypeDemo<java.lang.Object, java.lang.Object>
        System.out.println(superclass);
        // true
        System.out.println(superclass instanceof ParameterizedType);

        ParameterizedType pTypeSuperclass = (ParameterizedType) superclass;
        // class com.zhaopin.op.mofang.third.service.type.ParameterizedTypeTest2$ParameterizedTypeDemo
        System.out.println(pTypeSuperclass.getRawType());
        for (Type type : pTypeSuperclass.getActualTypeArguments()) {
            // 打印两遍: class java.lang.Object
            System.out.println(type.getTypeName());
        }
        // class com.zhaopin.op.mofang.third.service.type.ParameterizedTypeTest2
        System.out.println(pTypeSuperclass.getOwnerType());
    }

    public static class ParameterizedTypeDemo<K, V> {
    }

    public static class ParameterizedTypeDemoImpl extends ParameterizedTypeDemo<Object, Object> {
    }
}
