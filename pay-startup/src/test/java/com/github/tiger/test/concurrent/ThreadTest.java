package com.github.tiger.test.concurrent;

public class ThreadTest {

    public static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("pong");
        }
    }

    public static void main(String[] args) {
        MyThread t = new MyThread();
        t.start();
        System.out.println("ping");

        Thread.currentThread().interrupt();

        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " interrupt ...");
        System.out.println(threadName + " interrupted is " + Thread.interrupted());
        System.out.println(threadName + " interrupted is " + Thread.interrupted());
    }

}
