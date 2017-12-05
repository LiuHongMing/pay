package com.senyint.test.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 线程中断机制测试。
 *
 * 线程中断并不会停止线程的运行，而是通过设置中断标志位表示中断。
 */
public class InterruptTest extends Thread {

    public static void main(String[] args) throws InterruptedException {
        InterruptTest task = new InterruptTest();
        task.start();
        System.out.println("start task ...");
        TimeUnit.SECONDS.sleep(1);
        task.interrupt();
        System.out.println("task interrupt ...");
        TimeUnit.SECONDS.sleep(5);
    }

    @Override
    public void run() {
        int count = 0;
        for(;;) {
            // 中断后调用，第一次会返回true，并清除中断标志位。再次调用会返回false
            if (interrupted()) {
                break;
            }
            count++;
            if (count % 1000 == 0) {
                LockSupport.park(); // 不会抛出InterruptedException，会受到线程中断影响，继续执行
                System.out.println("count=" + count);
                // 中断后被阻塞，会清除中断标志位，抛出InterruptedException
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    break;
//                }
            }
        }

        System.out.println("finish task ...");
    }
}
