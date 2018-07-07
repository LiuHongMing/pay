package com.github.tiger.test.java.lang;

import java.io.Serializable;

public class OverloadTest {

    public static void sayHello(Object arg) {
        System.out.println("hello object");
    }

    public static void sayHello(int arg) {
        System.out.println("hello int");
    }

    public static void sayHello(long arg) {
        System.out.println("hello long");
    }

    public static void sayHello(Character arg) {
        System.out.println("hello character");
    }

    public static void sayHello(char arg) {
        System.out.println("hello char");
    }

    public static void sayHello(char... arg) {
        System.out.println("hello char...");
    }

    public static void sayHello(Serializable arg) {
        System.out.println("hello serializable");
    }


    public static void main(String[] args) {
        // 静态分派
        // char -> int -> long -> character -> serializable -> object -> char...
        sayHello('a');
    }
}
