package com.github.tiger.pay.test.concurrent;

/**
 * synchronized 方法或语句的使用提供了对与每个对象相关的隐式监视器锁的访问
 */
public class SynchronizedTest {

    public synchronized void write() {
        System.out.println("This is a synchroized method");
    }

    public void read() {
        synchronized (this) {
            System.out.println("This is a synchroized block");
        }
    }

    int count = 0;

    public synchronized void counter() throws InterruptedException {
        count++;
        System.out.println(Thread.currentThread().getName() + " come in..., count=" + count);
        if (count % 2 == 0) {
            wait(1000);
        }
    }

    public static void main(String[] args) {
        final SynchronizedTest test = new SynchronizedTest();
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int j = 0; j < 10; j++) {
                            test.counter();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        while (Thread.activeCount() > 1)
            Thread.yield();

        System.out.println(test.count);
    }
}
