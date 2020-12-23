package com.github.tiger.test.java.reflect;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * TypeVariable 是各种类型变量的公共高级接口。类型变量在反射方法首次需要时创建（在此包中指定）。
 * 如果类型变量 t 由类型（即类、接口或注释类型）T 引用，而且 T 由 T 的第 n 个封闭类声明（请参见 JLS 8.1.2），那么创建 t 需要解析（请参见 JVMS 5）T 的第 i 个封闭类，其中 i = 0 到 n（包含）。
 * 创建某个类型变量决不能导致创建其边界。重复创建类型变量没有效果。
 *
 * 可以在运行时将多个对象实例化，以表示一个给定的类型变量。尽管类型变量仅创建一次，这并不意味着任何缓存实例的要求都表示类型变量。
 * 不过，表示类型变量的所有实例彼此必须相等 (equal())。
 * 因此，使用类型变量的用户决不能依靠实现此接口的类实例的身份。
 *
 * @param <T>
 */
public class TypeVariableTest<T extends CharSequence> {

    public static void main(String[] args) {

        System.out.println(byte[].class.getComponentType().equals(Byte.TYPE));

        TypeVariable<Class<TypeVariableTest>>[] t = TypeVariableTest.class.getTypeParameters();
        for (TypeVariable<Class<TypeVariableTest>> m : t) {
            /**
             * 获得类型变量在声明的时候的名称，此例中为 T
             */
            System.out.println(m.getName());

            /**
             * 获得类型变量的上边界，若无显式的定义（extends）, 默认为 Object;
             * 类型变量的上边界可能不止一个，因为可以用 & 符号限定多个（这其中有且只能有一个为类或抽象类，且必须放在 extends 后的第一个，
             * 即若有多个上边界，则第一个 & 后必须为接口）
             *
             * interface java.lang.CharSequence
             */
            Type[] bounds = m.getBounds();
            for (Type t1 : bounds) {
                System.out.println(t1);
            }

            /**
             * 获得声明这个类型变量的类型及名称
             * 类中：class com.github.tiger.test.java.reflect.TypeVariableTest
             */
            System.out.println(m.getGenericDeclaration());
        }
    }
}
