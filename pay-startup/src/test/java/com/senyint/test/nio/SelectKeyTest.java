package com.senyint.test.nio;

import org.apache.ibatis.annotations.SelectKey;

import java.nio.channels.SelectionKey;

public class SelectKeyTest {

    public static void main(String[] args) {
        int ops = SelectionKey.OP_READ | SelectionKey.OP_WRITE |
                SelectionKey.OP_CONNECT | SelectionKey.OP_ACCEPT;
        System.out.println(Integer.toBinaryString(SelectionKey.OP_READ));
        System.out.println(Integer.toBinaryString(SelectionKey.OP_WRITE));
        System.out.println(Integer.toBinaryString(SelectionKey.OP_CONNECT));
        System.out.println(Integer.toBinaryString(SelectionKey.OP_ACCEPT));
        System.out.println(ops & ~SelectionKey.OP_READ);
        System.out.println(Integer.toBinaryString(~SelectionKey.OP_READ));
        System.out.println(~SelectionKey.OP_READ);
        System.out.println(~SelectionKey.OP_READ + 1);
    }

}
