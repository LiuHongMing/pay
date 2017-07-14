package com.senyint.test;

public class CodeBlockTest {

    static {
        System.out.println("I'm a static block!");
    }

    {
        System.out.println("I'm a block!");
    }

    public CodeBlockTest() {
        System.out.println("I'm constructor block!");
    }

    public static void main(String[] args) {
        new CodeBlockTest();
    }
}
