package com.senyint.test.concurrent;

public class ThreadTest {

    public static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("pong");
        }
    }

    public static void main(String[] args) {
        MyThread t = new MyThread();
        t.run();
        System.out.println("ping");

        Thread.currentThread().interrupt();
        System.out.println(Thread.interrupted());
        System.out.println(Thread.interrupted());
    }

}
