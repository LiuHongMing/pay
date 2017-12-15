package com.github.tiger.pay.test.concurrent.locks;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {

    static Thread mainThread;

    public static void main(String[] args) throws InterruptedException {

        mainThread = Thread.currentThread();

        // 先调用unpark释放许可，
        LockSupport.unpark(mainThread);
        LockSupport.park();

        ThreadA ta = new ThreadA("ta");

        System.out.println(Thread.currentThread().getName() + " start ta ...");
        ta.start();

        System.out.println(Thread.currentThread().getName() + " block ...");
        // 主线程阻塞
        LockSupport.park();

        System.out.println(Thread.currentThread().getName() + " continue ...");
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
