package com.github.tiger.test;

public class NullTest {

    public static void haha() {
        System.out.println("haha");
    }

    public static void main(String[] args) {
        ((NullTest) null).haha();
    }

}
