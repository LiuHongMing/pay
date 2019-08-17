package com.github.tiger.test.concurrent.volatiles;

/**
 * 将变量声明成 volatile，解决 doublecheck 的操作不一致问题
 */
public class VolatileDoubleCheckLockingTest {

    private static volatile Instance instance = null;

    static class Instance {}

    public static Instance getInstance() {
        if(instance == null) {
            synchronized (Instance.class) {
                if (instance == null) {
                    instance = new Instance();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        VolatileDoubleCheckLockingTest.getInstance();
    }
}
