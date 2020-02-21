package com.github.tiger.test.concurrent.locks;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport 提供 park() 和 unpark() 方法实现阻塞线程和解除线程阻塞，
 * LockSupport 和每个使用它的线程都与一个许可(permit)关联。permit 相当于 1，0 的开关，默认是 0，
 * 调用一次 unpark 就加 1 变成 1，调用一次 park 会消费 permit, 也就是将 1 变成 0，同时 park 立即返回。
 * 再次调用 park 会变成 block（因为 permit 为 0 了，会阻塞在这里，直到 permit 变为 1）, 这时调用 unpark 会把 permit 置为1。
 * 每个线程都有一个相关的 permit, permit 最多只有一个，重复调用 unpark 也不会积累。
 */
public class LockSupportTest {

    static Thread mainThread;

    public static void main(String[] args) {

        mainThread = Thread.currentThread();
        /**
         * 如果给定线程尚不可用，则为其提供许可。
         * 如果线程在park时被阻塞，则它将解锁。 否则，它的下一次park可以保证不会阻塞。
         * 如果给定的线程尚未启动，则此操作根本不会产生任何影响。
         */
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
                // 唤醒"当前对象上的等待线程"
                LockSupport.unpark(mainThread);
            }
        }
    }
}
