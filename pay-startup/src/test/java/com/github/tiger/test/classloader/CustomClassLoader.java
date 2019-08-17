package com.github.tiger.test.classloader;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author liuhongming
 */
public class CustomClassLoader extends URLClassLoader {

    public CustomClassLoader(URL[] urls) {
        super(urls);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (name.startsWith("com.github.tiger")) {
            return findClass(name);
        }

        return super.loadClass(name);
    }
}
