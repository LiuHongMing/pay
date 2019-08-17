package com.github.tiger.test.concurrent.interrupt;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 线程中断机制测试。
 * <p>
 * 线程中断并不会停止线程的运行，而是通过设置中断标志位表示中断。
 */
public class TestInterrupt extends Thread {

    public static void main(String[] args) throws InterruptedException {
        TestInterrupt task = new TestInterrupt();
        task.start();
        System.out.println("start task ...");
        TimeUnit.SECONDS.sleep(1);
        task.interrupt();
        System.out.println("task interrupt ...");
        TimeUnit.SECONDS.sleep(5);
        LockSupport.unpark(task);
    }

    @Override
    public void run() {
        int count = 0;
        for (; ; ) {
            /**
             * 中断后调用，第一次会返回 true，并清除中断标志位。
             *
             * 再次调用会返回 false
             */
            if (interrupted()) {
                System.out.println("interrupted ...");
                break;
            }
            count++;
            if (count % 1000 == 0) {
                /**
                 * 会受到线程中断影响退出阻塞状态，继续执行
                 *
                 * 不抛出 InterruptedException
                 */
//                LockSupport.park();
//                System.out.println("count=" + count);
                /**
                 * 中断后被阻塞，会清除中断标志位
                 *
                 * 抛出 InterruptedException
                 */
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println("throw InterruptedException");
                    break;
                }
            }
        }

        System.out.println("finish task ...");
    }
}
