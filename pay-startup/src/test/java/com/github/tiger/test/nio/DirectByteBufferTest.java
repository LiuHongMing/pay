package com.github.tiger.test.nio;

import sun.nio.ch.DirectBuffer;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

/**
 *
 * Class：DirectByteBuffer
 *
 * VM：-XX:MaxDirectMemorySize
 *
 * 直接内存回收：
 *
 * 内部关联{@link sun.misc.Cleaner}，该类继承PhantomReference
 *
 * 通过{@link sun.misc.Unsafe}.freeMemory() 释放DirectByteBuffer对应的直接内存
 *
 */
public class DirectByteBufferTest {

    public static void main(String[] args) throws InterruptedException {

        /**
         * 未设置-XX:MaxDirectMemorySize时，
         *
         * 直接内存 directMemory = Runtime.getRuntime().maxMemory();
         *
         * Please refer to {@link sun.misc.VM}
         */
        long maxMemory, directMemory;
        maxMemory = directMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();

        System.out.println("内存总量：" + totalMemory);
        System.out.println("最大内存：" + maxMemory);
        System.out.println("直接内存：" + directMemory);
        System.out.println("空闲内存：" + freeMemory);

        ByteBuffer bb = ByteBuffer.allocateDirect(1024 * 1024 * 512);

        TimeUnit.SECONDS.sleep(60);

        ((DirectBuffer) bb).cleaner().clean();

        TimeUnit.SECONDS.sleep(10);

        System.out.println("ok");
    }
}
