package com.senyint.test.concurrent.locks;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest2 {

    static Thread mainThread;

    public static void main(String[] args) throws InterruptedException {

        mainThread = Thread.currentThread();

        ThreadA ta = new ThreadA("ta");

        System.out.println(Thread.currentThread().getName() + " start ta ...");
        ta.start();

        System.out.println(Thread.currentThread().getName() + " block ...");

        Blocker blocker = new Blocker();
        synchronized (blocker) {
            // 主线程阻塞
            LockSupport.park(blocker);
        }

        System.out.println(Thread.currentThread().getName() + " continue ...");
    }

    static class Blocker {
    }

    static class ThreadA extends Thread {

        public ThreadA(String name) {
            super(name);
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " synchronized ...");
            synchronized (this) {
                System.out.println(Thread.currentThread().getName() + " wake up others ...");
                LockSupport.unpark(mainThread); // 唤醒"当前对象上的等待线程"
            }
        }
    }
}
