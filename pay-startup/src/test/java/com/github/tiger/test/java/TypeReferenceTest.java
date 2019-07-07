package com.github.tiger.test.java;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 由匿名内部类解析泛型类型
 *
 * @param <T>
 */
public class TypeReferenceTest<T> {

    private final static Set<Class<?>> primitiveClasses = new HashSet<Class<?>>();

    static {
        Class<?>[] classes = new Class[]{
                boolean.class,
                byte.class,
                short.class,
                int.class,
                long.class,
                float.class,
                double.class,

                Boolean.class,
                Byte.class,
                Short.class,
                Integer.class,
                Long.class,
                Float.class,
                Double.class,

                BigInteger.class,
                BigDecimal.class,
                String.class
        };

        for (Class<?> clazz : classes) {
            primitiveClasses.add(clazz);
        }
    }

    public Type type;

    protected TypeReferenceTest() {
        Type superClass = getClass().getGenericSuperclass();
        Type type = ((ParameterizedType) superClass).getActualTypeArguments()[0];

        this.type = type;
    }

    public static void main(String[] args) {
        TypeReferenceTest refer = new TypeReferenceTest<List<Map>>() {
        };
        System.out.println(refer.getType());

        System.out.println(int.class);
    }

    public Type getType() {
        return this.type;
    }
}
