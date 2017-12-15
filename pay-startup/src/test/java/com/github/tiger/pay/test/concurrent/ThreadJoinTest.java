package com.github.tiger.pay.test.concurrent;

/**
 * Thread.join()
 *
 * 等待其他线程完成才返回
 */
public class ThreadJoinTest {

    public static void main(String[] args) throws InterruptedException {

        final Thread busy = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("I'm busy");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        busy.start();

        Thread free = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    busy.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("I'm free");
            }
        });
        free.start();
    }
}
