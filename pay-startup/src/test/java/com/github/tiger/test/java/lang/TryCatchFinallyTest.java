package com.github.tiger.test.java.lang;

public class TryCatchFinallyTest {

    public static void main(String[] args) {
        int k = 0;
        System.out.println(testReturn(k));
    }

    public static int testReturn(int k) {
        try {
            k = k + 1;
            System.out.println("try" + k);
            return k;
        } catch (Exception e) {
            k = k + 2;
            System.out.println("catch" + k);
            return k;
        } finally {
            k = k + 3;
            System.out.println("finally" + k);
            return k;
        }


    }
}
