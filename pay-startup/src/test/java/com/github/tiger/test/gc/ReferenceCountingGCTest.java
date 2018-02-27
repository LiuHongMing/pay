package com.github.tiger.test.gc;

public class ReferenceCountingGCTest {

    public ReferenceCountingGCTest instance = null;

    private static final int _1M = 1024 * 1024;

    private byte[] bigSize = new byte[2 * _1M];


    public static void testGC() {
        ReferenceCountingGCTest a = new ReferenceCountingGCTest();
        ReferenceCountingGCTest b = new ReferenceCountingGCTest();

        a.instance = b;
        b.instance = a;

        a = null;
        b = null;

        System.gc();
    }

    public static void main(String[] args) {
        testGC();
    }

}
