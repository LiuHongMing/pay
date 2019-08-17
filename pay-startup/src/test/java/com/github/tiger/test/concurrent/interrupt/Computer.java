package com.github.tiger.test.concurrent.interrupt;

import java.util.concurrent.TimeUnit;

public class Computer {

    public static void main(String[] args) {

        Digital digital = new Digital();

        Runnable runnable = () -> {
            try {
                while (true) {
                    String value = digital.call();
                    String message = String.format("%s get %s ...",
                            Thread.currentThread().getName(), value);
                    System.out.println(message);
                }
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        };

        Thread go1 = new Thread(runnable, "t1");
        go1.start();

        Thread go2 = new Thread(runnable, "t2");
        go2.start();

        InterruptThread go3 = new InterruptThread("go-interrupt");
        go3.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("t1.interrupt() ...");

        go1.interrupt();

        go3.interrupt();
    }

    public static class InterruptThread extends Thread {

        public InterruptThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            for (; ; ) {
                /**
                 * 清除中断状态
                 *
                 * isInterrupted()=false
                 */
                // if (interrupted()) {
                /**
                 * 不清除中断状态
                 *
                 * isInterrupted()=true
                 */
                if (isInterrupted()) {
                    String message = String.format("%s is Interrupted ...",
                            Thread.currentThread().getName());
                    System.out.println(message);
                    break;
                }
            }
        }
    }

}
