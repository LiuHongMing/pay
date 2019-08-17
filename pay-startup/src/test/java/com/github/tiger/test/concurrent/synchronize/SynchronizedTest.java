package com.github.tiger.test.concurrent.synchronize;

/**
 * synchronized 方法或语句 提供了对与每个对象相关的隐式监视器锁的访问
 *
 * 使用 yield() 等待所有线程执行完毕
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
            new Thread(() -> {
                try {
                    for (int j = 0; j < 10; j++) {
                        test.counter();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        while (Thread.activeCount() > 1)
            Thread.yield();

        System.out.println(test.count);
    }
}
