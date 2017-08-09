package com.senyint.test.common;

import org.junit.Test;

public class BitTest {

    @Test
    public void testOpt() throws Exception {
        int id = 2, depth = 1;
        int shift = id ^ 1 << depth;
        System.out.println(shift);
        System.out.println(1 << 24 - 10);

        id = 1;
        shift = 3;
        for (int i = 0; i < shift; i++) {
            id <<= 1;
            System.out.println(id);
        }

    }

    @Test
    public void testDouble() throws Exception {
        int reqCapacity = 9000;
        int normalizedCapacity = reqCapacity;
        normalizedCapacity--;
        normalizedCapacity |= normalizedCapacity >>> 1;
        normalizedCapacity |= normalizedCapacity >>> 2;
        normalizedCapacity |= normalizedCapacity >>> 4;
        normalizedCapacity |= normalizedCapacity >>> 8;
        normalizedCapacity |= normalizedCapacity >>> 16;
        normalizedCapacity++;
    }

    // 掩码
    @Test
    public void testMask() throws Exception {
        // 8192
        int pageSize = 1 << 13;
        int overflowMask = ~(pageSize - 1);

        int num = Integer.numberOfLeadingZeros(pageSize);
        System.out.print("pageSize=" + pageSize + ", binary:\n");
        for (int i = 0; i < num; i++) {
            System.out.print("0");
        }
        System.out.println(Integer.toBinaryString(pageSize));

        int capacity = 100;
        num = Integer.numberOfLeadingZeros(capacity);
        System.out.print("capacity=" + capacity + ", binary:\n");
        for (int i = 0; i < num; i++) {
            System.out.print("0");
        }
        System.out.println(Integer.toBinaryString(capacity));
        System.out.println("overflowMask=" + overflowMask + ", binary:\n" + Integer.toBinaryString(overflowMask));

        // capacity & overflowMask == 0 : capacity < pageSize
        System.out.println("capacity & overflowMask == " + (capacity & overflowMask));

    }

}
