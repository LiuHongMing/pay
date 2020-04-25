package com.github.tiger.test.concurrent;

import java.util.concurrent.TimeUnit;

public class ThreadTest {

    public static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("pong");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyThread t = new MyThread();
        t.start();
        System.out.println("ping");
        // 主线程等待t线程结束
        t.join();
        System.out.println("Thread t is terminated");

        Thread.currentThread().interrupt();
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " interrupt ...");
        System.out.println(threadName + " interrupted is " + Thread.interrupted());
        System.out.println(threadName + " interrupted is " + Thread.interrupted());
    }

}
