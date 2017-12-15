package com.github.tiger.pay.test.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

/**
 * 引用计数
 */
public class ReferenceCountUtilTest {

    @Test
    public void testRelease() {
        ByteBuf input = Unpooled.buffer(1);
        c(b(a(input)));
    }

    public ByteBuf a(ByteBuf input) {
        input.writeByte(42);
        return input;
    }

    public ByteBuf b(ByteBuf input) {
        ByteBuf out;
        try {
            out = input.alloc().buffer(input.readableBytes() + 1);
            out.writeBytes(input);
            out.writeByte(42);
            return out;
        } finally {
            input.release();
        }
    }

    public void c(ByteBuf input) {
        System.out.println(input);
        input.release();
    }
}
