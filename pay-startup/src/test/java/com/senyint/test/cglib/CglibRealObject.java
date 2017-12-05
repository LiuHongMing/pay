package com.senyint.test.cglib;

public class CglibRealObject {

    public String visit() {
        System.out.println("I am 'RealSubject', I am execute method");
        return "I am here";
    }

    public void go() {
        System.out.println("No return value");
    }
}
