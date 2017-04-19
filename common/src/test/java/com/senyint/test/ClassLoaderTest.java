package com.senyint.test;

import org.junit.Test;

public class ClassLoaderTest {

    @Test
    public void testCompareClassLoader() {
        ClassLoader parentClassLoader = ClassLoaderTest.class.getClassLoader().getParent();
        System.out.println("parentClassLoader: " + parentClassLoader);

        ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
        System.out.println("classLoader: " + classLoader);
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        System.out.println("contextClassLoader: " + contextClassLoader);
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println("systemClassLoader: " + systemClassLoader);

        assert classLoader == contextClassLoader;
        System.out.println("classLoader == contextClassLoader is true");
        assert classLoader == systemClassLoader;
        System.out.println("classLoader == systemClassLoader is true");
        assert contextClassLoader == systemClassLoader;
        System.out.println("contextClassLoader == systemClassLoader is true");
    }

}
