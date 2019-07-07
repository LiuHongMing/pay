package com.github.tiger.test.concurrent;

/**
 * @author liuhongming
 */
public class WaitNotifyTest {

    public static void main(String[] args) throws InterruptedException {
        ThreadA ta = new ThreadA("ta");

        synchronized (ta) {
            System.out.println(Thread.currentThread().getName() + " start ta ...");
            ta.start();

            System.out.println(Thread.currentThread().getName() + " block ...");
            // 主线程等待，释放锁
            ta.wait();

            System.out.println(Thread.currentThread().getName() + " continue ...");
        }
    }

    static class ThreadA extends Thread {

        public ThreadA(String name) {
            super(name);
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " synchronized ...");
            synchronized (this) { // 获取锁
                System.out.println(Thread.currentThread().getName() + " wake up others ...");
                notify(); // 唤醒"当前对象上的等待线程"
            }
        }
    }
}
