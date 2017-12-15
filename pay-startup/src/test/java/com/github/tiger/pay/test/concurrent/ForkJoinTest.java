package com.github.tiger.pay.test.concurrent;

import java.util.concurrent.*;

/**
 * Fork/Join模式
 *
 * 工作窃取算法
 */
public class ForkJoinTest {

    static class CountTask extends RecursiveTask<Integer> {

        private static final int THRESHOLD = 2;
        private int start;
        private int end;

        public CountTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            int sum = 0;

            boolean canCompute = (end - start) <= THRESHOLD;
            if (canCompute) {
                for (int i = start; i <= end; i++) {
                    sum += i;
                }
            } else {
                int middle = (start + end)  / 2; // 计算中间值
                CountTask leftTask = new CountTask(start, middle);
                CountTask rightTask = new CountTask(middle + 1, end);

                leftTask.fork();
                rightTask.fork();

                int left = leftTask.join();
                int right = rightTask.join();

                sum = left + right;
            }

            return sum;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask countTask = new CountTask(1, 4);

        Future<Integer> forkJoinTask = forkJoinPool.submit(countTask);
        Integer result = forkJoinTask.get();

        System.out.println(result);
    }

}
