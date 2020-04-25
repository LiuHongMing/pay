package com.github.tiger.test.java;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * 字节序列：Big Endian、Little Endian
 *
 * @author jason.liuhongming
 *
 */
public class EndianTest {

    @Test
    public void testOrder() {

        int x = 0x01020304;

        ByteBuffer bb = ByteBuffer.wrap(new byte[4]);
        bb.asIntBuffer().put(x);
        String ss_before = Arrays.toString(bb.array());

        System.out.println("默认字节序 " + bb.order().toString() + "," + " 内存数据 " + ss_before);

        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.asIntBuffer().put(x);
        String ss_after = Arrays.toString(bb.array());

        System.out.println("修改字节序 " + bb.order().toString() + "," + " 内存数据 " + ss_after);
    }

}
