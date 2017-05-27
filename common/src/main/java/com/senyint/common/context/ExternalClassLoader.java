package com.senyint.common.context;

public class ExternalClassLoader extends ClassLoader {

    public Class<?> defineClass(String name, byte[] data) {
        return super.defineClass(name, data, 0, data.length);
    }
}
