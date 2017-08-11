package com.senyint.test.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import org.junit.Test;

public class ByteBufAllocatorTest {

    final int maxOrder = 11;
    final int pageSize = 8192;
    final PooledByteBufAllocator pooled = new PooledByteBufAllocator(false);

    @Test
    public void testPooled() throws Exception {
        ByteBuf buf = pooled.heapBuffer(4096);
        System.out.println(buf);
    }

    @Test
    public void testPoolChunk() throws Exception {
        int chunkSize = (1 << maxOrder) * pageSize;
        System.out.println(chunkSize);
    }

    @Test
    public void testPoolSubpage() throws Exception {
    }

}
