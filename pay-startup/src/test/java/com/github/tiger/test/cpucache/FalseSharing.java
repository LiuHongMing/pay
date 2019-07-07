package com.github.tiger.test.cpucache;

/**
 * 伪共享（False Sharing）：指的是多个线程同时读写同一个缓存行的不同变量时导致的 CPU 缓存失效。
 * 尽管这些变量之间没有任何关系，但由于在主内存中邻近，存在于同一个缓存行之中，
 * 它们的相互覆盖会导致频繁的缓存未命中，引发性能下降。
 */
public final class FalseSharing implements Runnable {

    public final static int NUM_THREADS = 4;
    public final static long ITERATIONS = 500L * 1000L * 1000L;
    private static VolatileLong[] longs = new VolatileLong[NUM_THREADS];

    static {
        for (int i = 0; i < longs.length; i++) {
            longs[i] = new VolatileLong();
        }
    }

    private final int arrayIndex;

    public FalseSharing(final int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    public static void main(final String[] args) throws Exception {
        final long start = System.currentTimeMillis();
        runTest();
        System.out.println("duration = " + (System.currentTimeMillis() - start));
    }

    private static void runTest() throws InterruptedException {
        Thread[] threads = new Thread[NUM_THREADS];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new FalseSharing(i));
        }
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
    }

    public void run() {
        long i = ITERATIONS + 1;
        while (0 != --i) {
            longs[arrayIndex].value = i;
        }
    }

    public final static class VolatileLong {
        public volatile long value = 0L;
        public long p1, p2, p3, p4, p5, p6; // 缓存行填充，可以注释后对比测试
    }


}

