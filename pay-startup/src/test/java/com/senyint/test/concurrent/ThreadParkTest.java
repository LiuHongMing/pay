package com.senyint.test.concurrent;

import java.util.concurrent.locks.LockSupport;

/**
 * 测试LockSupport
 */
public class ThreadParkTest {

    public static void main(String[] args) {
        Thread main = Thread.currentThread();

        Thread go = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(600 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LockSupport.unpark(main);
            }
        });
        go.start();

        LockSupport.park();

        System.out.println("main thread end ...");
    }
}
