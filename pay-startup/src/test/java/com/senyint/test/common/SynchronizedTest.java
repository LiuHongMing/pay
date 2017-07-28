package com.senyint.test.common;

public class SynchronizedTest {

    public synchronized void write() {
        System.out.println("This is a synchroized method");
    }

    public void read() {
        synchronized (this) {
            System.out.println("This is a synchroized block");
        }
    }
}
