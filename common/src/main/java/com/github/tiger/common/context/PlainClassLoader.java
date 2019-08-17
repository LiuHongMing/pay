package com.github.tiger.common.context;

/**
 * @author liuhongming
 */
public class PlainClassLoader extends ClassLoader {

    /**
     * 重写 loadClass
     */
    @Override
    public Class<?> loadClass(String name) {
        return findClass(name);
    }

    /**
     * 重写 findClass
     */
    @Override
    protected Class<?> findClass(String name) {

        final String myClass = "MyClass";

        byte[] bytes = new byte[0];

        return defineClass(myClass, bytes, 0, bytes.length);
    }

}
