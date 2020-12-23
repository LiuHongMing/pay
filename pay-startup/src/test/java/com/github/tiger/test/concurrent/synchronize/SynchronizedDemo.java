package com.github.tiger.test.concurrent.synchronize;

/**
 * 0: ldc           #2    // class com/github/tiger/test/concurrent/synchronize/SynchronizedDemo
 * 2: dup
 * 3: astore_1
 * 4: monitorenter
 * 5: aload_1
 * 6: monitorexit
 * 7: goto          15
 * 10: astore_2
 * 11: aload_1
 * 12: monitorexit
 * 13: aload_2
 * 14: athrow
 * 15: invokestatic  #3    // Method method:()V
 * 18: return
 *
 * 为什么会有两个monitorexit ？
 *
 * 一个 monitorexit 是正常退出同步时执行，一个 monitorexit 是抛出异常时 monitorenter 和 monitorexit 指令依然可以正确配对执行。
 *
 * 编译器会自动产生一个异常处理器，这个异常处理器声明可处理所有的异常，它的目的就是用来执行 monitorexit 指令
 */
public class SynchronizedDemo {

    public static void main(String[] args) {
        synchronized (SynchronizedDemo.class) {
        }
        method();
    }

    public static void method() {
    }

}
