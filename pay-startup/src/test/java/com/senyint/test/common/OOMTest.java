package com.senyint.test.common;

import org.junit.Test;

import java.io.File;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * 模拟内存溢出
 */
public class OOMTest {

    public static void main(String[] args) throws Exception {
        URL url = new File("F:\\Workspace@2016\\pay\\pay-startup\\target\\test-classes").toURI().toURL();
        URL[] urls = {url};

        ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();

        List<ClassLoader> classLoaders = new ArrayList<>();
        List<Class> classes = new ArrayList<>();

        while (true) {
            ClassLoader cl = new URLClassLoader(urls);
            classLoaders.add(cl);
            Class clz = cl.loadClass("com.senyint.test.common.MainTest");
            classes.add(clz);

            System.out.println("total: " + classLoadingMXBean.getTotalLoadedClassCount());
            System.out.println("active: " + classLoadingMXBean.getLoadedClassCount());
            System.out.println("unloaded: " + classLoadingMXBean.getUnloadedClassCount());
        }
    }

    @Test
    public void testPermGen() throws Exception {

    }
}
