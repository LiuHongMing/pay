package com.github.tiger.test.java;

public class NullTest {

    public static void haha() {
        System.out.println("haha");
    }

    public void hehe() {
        System.out.println("hehe");
    }

    public static void main(String[] args) {
        ((NullTest) null).haha();
    }

}
