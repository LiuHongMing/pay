package com.github.tiger.test.gc;

import java.lang.management.ManagementFactory;

public class GcObserver {

    private static void grow() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        // pid
        String pid = name.split("@")[0];
        System.out.println("Pid is:" + pid);

        while (true) {
            byte[] b = null;
            for (int i = 0; i < 10; i++) {
                b = new byte[1 * 1024 * 1024];
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        grow();
    }
}
