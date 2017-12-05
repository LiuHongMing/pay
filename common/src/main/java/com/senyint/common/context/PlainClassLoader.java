package com.senyint.common.context;

/**
 * @author liuhongming
 */
public class PlainClassLoader extends ClassLoader {

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        /**
         * 重写findClass
         */
        return super.findClass(name);
    }

}
