package com.senyint.test.nio;

import sun.nio.ch.DirectBuffer;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

public class DirectByteBufferTest {

    public static void main(String[] args) throws InterruptedException {
        ByteBuffer bb = ByteBuffer.allocateDirect(1024 * 1024 * 512);

        TimeUnit.SECONDS.sleep(60);

        ((DirectBuffer) bb).cleaner().clean();

        TimeUnit.SECONDS.sleep(10);

        System.out.println("ok");
    }
}
