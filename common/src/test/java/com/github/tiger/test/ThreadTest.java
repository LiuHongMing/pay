package com.github.tiger.test;

public class ThreadTest {

    public static void main(String[] args) {
        ThreadA threadA = new ThreadA();
        ThreadB threadB = new ThreadB();

        threadA.start();
        threadB.start();
    }

    static class ThreadA extends Thread {

        static String a = "I am ThreadA.a";

        @Override
        public void run() {
            ThreadB.b = "I am ThreadB.b, changed by ThreadA";
            System.out.println(a);
        }
    }

    static class ThreadB extends Thread {

        static String b = "I am ThreadB.b";

        @Override
        public void run() {
            ThreadA.a = "I am ThreadA.a, Changed by ThreadB";
            System.out.println(b);
        }
    }
}
