package com.github.tiger.test.concurrent;

import java.util.concurrent.*;

/**
 * Executor 将有限边界用于最大线程和工作队列容量，且已经饱和时，
 *
 * 在方法 execute(java.lang.Runnable) 中提交的新任务将被拒绝
 *
 * 任务拒绝策略默认AbortPolicy
 */
public class ThreadPoolTest {

    static class ThreadPoolWithHook extends ThreadPoolExecutor {

        public ThreadPoolWithHook(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }

        public ThreadPoolWithHook(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            System.out.println(Thread.currentThread().getName() + " afterExecute");

        }
    }

    public static void main(String[] args) {
        /**
         * keepAliveTime: 当线程数大于核心时，这是多余空闲线程在终止之前等待新任务的最长时间。
         */
        ThreadPoolExecutor tpe = new ThreadPoolWithHook(3, 5,
                10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3),
                new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 0; i < 10 ; i++) {
            final int j = i;
            tpe.execute(() -> System.out.println(Thread.currentThread().getName() + ", i=" + j));
        }

    }
}
