package com.github.tiger.test.netty;

import io.netty.util.ThreadDeathWatcher;

public class ThreadDeathWatcherTest {

    public static void main(String[] args) throws InterruptedException {

        Runnable mainTask = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("main thread done");
            }
        };

        Thread main = new Thread(mainTask);
        main.start();

        TestRunnable delayedTask = new TestRunnable(main);

        /**
         * ThreadDeathWatcher是netty的util包下的一个工具类。
         *
         * watch(Thread thread, Runnable task):
         * - 第一个参数可以认为是提交线程，只有提交线程运行结束，
         * - 第二个参数代表的Runnable任务才会开始执行。
         *
         * ReferenceCountUtil的public static <T> T releaseLater(T msg) 方法实现的关键
         */
        ThreadDeathWatcher.watch(main, delayedTask);

        Thread.sleep(10000);

    }

    private static class TestRunnable implements Runnable {

        private Thread thread;

        public TestRunnable(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {

            System.out.println("the watched thread is alive? : " + thread.isAlive());

            System.out.println("the watched thread is dead. so, i can run");
        }
    }
}
