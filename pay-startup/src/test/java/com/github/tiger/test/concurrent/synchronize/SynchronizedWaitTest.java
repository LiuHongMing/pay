package com.github.tiger.test.concurrent.synchronize;

public class SynchronizedWaitTest {

    public static void main(String[] args) throws InterruptedException {
        final Object obj = new Object();

        new Thread(() -> {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            synchronized (obj) {
                System.out.println("main thread wakeup by "
                        + Thread.currentThread().getName() + " ...");
                obj.notify();
            }

        }, "thread-notify").start();

        synchronized (obj) {
            System.out.println("main thread waiting ...");
            obj.wait();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        System.out.println("main thread end ...");
    }

}
