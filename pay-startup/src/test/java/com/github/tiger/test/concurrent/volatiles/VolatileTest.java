package com.github.tiger.test.concurrent.volatiles;

/**
 * volatile对变量的操作非原子性，不能保证一致性
 */
public class VolatileTest {

    private volatile int race = 0;

    public void increase() {
        race++;
    }

    public static final int THREAD_COUNT = 10;

    public static void main(String[] args) {
        final VolatileTest test = new VolatileTest();

        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    test.increase();
                }
            }).start();
        }

        // 等待所有累加线程都结束
        while (Thread.activeCount() > 1)
            Thread.yield();

        System.out.println(test.race);
    }

}
