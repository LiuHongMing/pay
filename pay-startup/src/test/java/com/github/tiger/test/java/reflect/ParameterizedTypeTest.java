package com.github.tiger.test.java.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * ParameterizedType 表示参数化类型，如 Collection<String>。
 * <p>
 * 参数化类型在反射方法首次需要时创建（在此包中指定）。当创建参数化类型 p 时，p 实例化的一般类型声明会被解析，并且按递归方式创建 p 的所有类型参数。有关类型变量创建过程的详细信息，请参阅 TypeVariable。重复创建的参数化类型无效。
 * <p>
 * 实现此接口的类的实例必须实现 equals() 方法，该方法用于比较两个共享相同一般类型声明和具有相同类型参数的任何实例。
 * <p>
 * ----------
 * <p>
 * TypeParameterizedTest 构造函数为 protected。
 * <p>
 * 创建匿名内部类继承 TypeReferenceTest。
 * <p>
 * TypeParameterizedTest refer = new TypeParameterizedTest<List<Map>>() {};
 * <p>
 * 解析泛型类型。
 *
 * @param <T>
 */
public class ParameterizedTypeTest<T> {

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
    public Type rawType;
    public Type ownerType;

    protected ParameterizedTypeTest() {
        Type superClass = getClass().getGenericSuperclass();
        Type type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
        Type rawType = ((ParameterizedType) superClass).getRawType();
        Type ownerType = ((ParameterizedType) superClass).getOwnerType();

        this.type = type;
        this.rawType = rawType;
        this.ownerType = ownerType;
    }

    public static void main(String[] args) throws NoSuchMethodException {
        ParameterizedTypeTest refer = new ParameterizedTypeTest<List<Map>>() {
        };
        System.out.println(refer.getType());
        System.out.println(refer.getRawType());
        System.out.println(refer.getOwnerType());

        Method method = ParameterizedTypeTest.class.getMethod(
                "parameterizedTypeTest", Map.Entry.class);
        Type[] types = method.getGenericParameterTypes();
        for (Type type : types) {
            ParameterizedType pType = (ParameterizedType) type;
            System.out.println(pType + " ★ " + pType.getOwnerType() + " ★ " + pType.getRawType() //
                    + " ★ " + Arrays.toString(pType.getActualTypeArguments()));
            // java.util.Map.java.util.Map$Entry<T, U> ★ interface java.util.Map ★ interface java.util.Map$Entry ★ [T, U]
        }
    }

    /**
     * 返回表示此类型实际类型参数的 Type 对象的数组
     *
     * 简单来说就是：获得参数化类型中 <> 里的类型参数的类型。
     * 因为可能有多个类型参数，例如 Map<K, V>，所以返回的是一个 Type[] 数组。
     * 注意：无论 <> 中有几层 <> 嵌套，这个方法仅仅脱去最外层的 <>，之后剩下的内容就作为这个方法的返回值，所以其返回值类型不一定。
     *
     * @return
     */
    public Type getType() {
        return this.type;
    }

    /**
     * 返回 Type 对象，表示此类型是其成员之一的类型。
     *
     * 如果此类型为顶层类型，则返回 null（大多数情况都是这样）。
     *
     * Map.Entry<T, U> 返回 interface java.util.Map
     *
     * @return
     */
    public Type getOwnerType() {
        return this.ownerType;
    }

    /**
     * 返回 Type 对象，表示声明此类型的类或接口
     *
     * 简单来说就是：返回最外层 <> 前面那个类型，例如 Map<K ,V>，返回的是 Map 类型。
     *
     * @return
     */
    public Type getRawType() {
        return this.rawType;
    }

    public static <T, U> void parameterizedTypeTest(Map.Entry<T, U> mapEntry) {
    }
}
