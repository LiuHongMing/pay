package com.github.tiger.test.nio;

import java.nio.channels.SelectionKey;

public class SelectKeyTest {

    public static void main(String[] args) {
        int ops = SelectionKey.OP_READ | SelectionKey.OP_WRITE |
                SelectionKey.OP_CONNECT | SelectionKey.OP_ACCEPT;
        System.out.println(Integer.toBinaryString(ops));
        System.out.println(Integer.toBinaryString(ops & ~SelectionKey.OP_READ));
    }

}
