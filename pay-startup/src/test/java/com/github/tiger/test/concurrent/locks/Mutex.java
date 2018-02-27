package com.github.tiger.test.concurrent.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 互斥锁，通过Lock接口提供的加锁原语，实现互斥锁
 *
 * 内部类Sync继承AbstractQueuedSynchronizer
 */
public class Mutex implements Lock {


    static class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int acquire) {
            assert acquire == 1;
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int release) {
            assert release == 0;
            if (getState() == 0) throw new IllegalMonitorStateException();
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        public Condition newCondition() {
            return new ConditionObject();
        }
    }


    private final Sync sync = new Sync();
    public boolean isLock() {
        return sync.isHeldExclusively();
    }

    public boolean hasQueuedThreads() {
        return sync.hasQueuedThreads();
    }

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(0);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    public static void main(String[] args) {
        Mutex mutex = new Mutex();

        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                mutex.lock();
                System.out.println("ThreadA locked");
                mutex.unlock();
                System.out.println("ThreadA unlock");
            }
        });

        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                mutex.lock();
                System.out.println("ThreadB locked");
                mutex.unlock();
                System.out.println("ThreadB unlock");
            }
        });

        threadA.start();
        threadB.start();
    }
}
