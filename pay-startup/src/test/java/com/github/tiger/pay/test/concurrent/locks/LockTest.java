package com.github.tiger.pay.test.concurrent.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 显式锁
 *
 * 包括提供了：
 * 一个非块结构的获取锁尝试 (tryLock())
 * 一个获取可中断锁的尝试 (lockInterruptibly())
 * 一个获取超时失效锁的尝试 (tryLock(long, TimeUnit))
 */
public class LockTest {

    private int race = 0;

    Lock lock = new ReentrantLock();

    public void increase() {
        lock.lock();
        try {
            race++;
        } finally {
            lock.unlock();
        }
    }

    public static final int THREAD_COUNT = 10;

    public static void main(String[] args) {
        final LockTest test = new LockTest();

        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        test.increase();
                    }
                }
            }).start();
        }

        // 等待所有累加线程都结束
        while (Thread.activeCount() > 1)
            Thread.yield();

        System.out.println(test.race);
    }


}
