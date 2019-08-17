package com.github.tiger.test.concurrent.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class TestSynchronousQueue {

    public static void main(String[] args) throws InterruptedException {

        final BlockingQueue<Integer> queue = new SynchronousQueue<>();

        Thread putThread = new Thread(() -> {
            System.out.println("put thread start ...");
            try {
                queue.put(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("put thread end ...");
        });

        Thread takeThread = new Thread(() -> {
            System.out.println("take thread start ...");
            try {
                System.out.println("take from putThread: " + queue.take());
            } catch (InterruptedException e) {
            }
            System.out.println("take thread end ...");
        });

        putThread.start();
        Thread.sleep(1000);
        takeThread.start();
    }
}
