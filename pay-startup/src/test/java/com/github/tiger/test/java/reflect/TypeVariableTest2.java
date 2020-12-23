package com.github.tiger.test.java.reflect;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * @className: TypeVariableTest2
 * @description: TODO
 * @author liuhongming
 * @date 2020/09/18
 * @version
 */
public class TypeVariableTest2<K extends Comparable & Serializable, V> {
    K key;
    V value;

    public static void main(String[] args) throws Exception {
        // 获取字段的类型
        Field fk = TypeVariableTest2.class.getDeclaredField("key");
        Field fv = TypeVariableTest2.class.getDeclaredField("value");
        // true
        System.out.println(fk.getGenericType() instanceof TypeVariable);
        // true
        System.out.println(fv.getGenericType() instanceof TypeVariable);
        TypeVariable keyType = (TypeVariable) fk.getGenericType();
        TypeVariable valueType = (TypeVariable) fv.getGenericType();
        // getName 方法
        // K
        System.out.println(keyType.getName());
        // V
        System.out.println(valueType.getName());
        // getGenericDeclaration 方法
        // class com.zhaopin.op.mofang.third.service.type.TypeVariableTest2
        System.out.println(keyType.getGenericDeclaration());
        // class com.zhaopin.op.mofang.third.service.type.TypeVariableTest2
        System.out.println(valueType.getGenericDeclaration());
        // getBounds 方法
        // 有两个
        // interface java.lang.Comparable
        // interface java.io.Serializable
        System.out.println("K 的上界:");
        for (Type type : keyType.getBounds()) {
            System.out.println(type);
        }
        // 没明确声明上界的, 默认上界是 Object
        // class java.lang.Object
        System.out.println("V 的上界:");
        for (Type type : valueType.getBounds()) {
            System.out.println(type);
        }
    }
}
