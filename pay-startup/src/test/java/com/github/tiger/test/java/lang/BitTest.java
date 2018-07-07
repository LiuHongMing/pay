package com.github.tiger.test.java.lang;

import org.junit.Test;

public class BitTest {

    /**
     * 二进制、八进制、十六进制
     *
     * @throws Exception
     */
    @Test
    public void testLiterals() throws Exception {
        // 0b前缀
        int binary = 0b10; // 2
        System.out.println(binary);

        // 0前缀
        int octal = 010; // 8
        System.out.println(octal);

        // 0x前缀
        int hex = 0x10; // 16
        System.out.println(hex);

    }

    /**
     * 语义
     *
     * 原码、反码、补码
     */
    @Test
    public void testSemantic() {
        int positive = 1;
        /**
         * 补码
         */
        System.out.println(Integer.toBinaryString(positive));
        /**
         * 取反
         */
        System.out.println(Integer.toBinaryString(~positive));

        int negative = -1;
        /**
         * 补码
         */
        System.out.println(Integer.toBinaryString(negative));
        /**
         * 取反
         */
        System.out.println(Integer.toBinaryString(~negative));
    }

    /**
     * Next
     *
     * @throws Exception
     */
    @Test
    public void testNext() throws Exception {
        int children = 10;
        int length = 64;
        if (isPowerOfTwo(length)) {
            // 2^n-1，低位都是1，例如 8-1=7(0111)
            int next = ++children & length - 1;
            System.out.println(next);
        }
    }

    public boolean isPowerOfTwo(int val) {
        return (val & -val) == val;
    }


    /**
     * 位移
     *
     * @throws Exception
     */
    @Test
    public void testShift() throws Exception {
        System.out.println(Long.MIN_VALUE);
        System.out.println(Long.MAX_VALUE);
        System.out.println(1L << 63 == Long.MIN_VALUE);
        System.out.println((1L << 63) - 1 == Long.MAX_VALUE);
        System.out.println(1L << 105 == 1L << 41);

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

        System.out.println(0x40000000);
    }

    /**
     * 双倍增长
     *
     * @throws Exception
     */
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

    /**
     * 掩码
     *
     * @throws Exception
     */
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

        int SHIFT = 16;
        System.out.println(Integer.toHexString((1 << SHIFT) - 1));
        System.out.println(Integer.toBinaryString((1 << SHIFT) - 1));

        System.out.println(Integer.toBinaryString(-1 << 29));
        System.out.println(Integer.toBinaryString((1 << 29) - 1));
        System.out.println(Integer.toBinaryString(((1 << 29) - 1) & (-1 << 29)));

    }

}
