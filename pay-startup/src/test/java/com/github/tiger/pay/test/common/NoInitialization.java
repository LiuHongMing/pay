package com.github.tiger.pay.test.common;

public class NoInitialization {

    static class ConstClass {
        static {
            System.out.println("ConstClass init!!!");
        }

        public static final String HELLOWORLD = "hello world";
    }

    public static void main(String[] args) {
        System.out.println(ConstClass.HELLOWORLD);
    }
}
