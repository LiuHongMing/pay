package com.senyint.test.concurrent.locks;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

public class FIFOMutex {

    private final AtomicBoolean locked = new AtomicBoolean(false);
    private final Queue<Thread> waiters = new ConcurrentLinkedQueue<>();

    public void lock() {
        boolean wasInterrupt = false;
        Thread current = Thread.currentThread();
        waiters.add(current);

        while (waiters.peek() != current ||
                !locked.compareAndSet(false, true)) {
            LockSupport.park(this);
            if (Thread.interrupted()) {
                wasInterrupt = true;
            }
        }

        waiters.remove();
        if (wasInterrupt)
            current.interrupt();
    }

    public void unlock() {
        locked.set(false);
        LockSupport.unpark(waiters.peek());
    }

    public static void main(String[] args) {
        FIFOMutex mutex = new FIFOMutex();
        mutex.lock();
        System.out.println("main lock");
        mutex.unlock();
        System.out.println("main unlock");
    }
}
