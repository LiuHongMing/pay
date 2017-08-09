package com.senyint.test.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import org.junit.Test;

public class ByteBufAllocatorTest {

    @Test
    public void testPooled() throws Exception {
        PooledByteBufAllocator pooled = new PooledByteBufAllocator(false);
        ByteBuf buf = pooled.heapBuffer(9000);
        System.out.println(buf);
    }

}
