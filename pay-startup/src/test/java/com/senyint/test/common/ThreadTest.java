package com.senyint.test.common;

public class ThreadTest {

    private static class MyExecutor implements Runnable {

        private Thread thread;

        public MyExecutor() {
            thread = new Thread(this);
            thread.setName("MyExecutor.thread");
        }

        @Override
        public void run() {
            if (inEventLoop()) {
                System.out.println(thread.getName());
            }
        }

        public boolean inEventLoop() {
            if(thread == Thread.currentThread()) {
                return true;
            }
            return false;
        }

        public void execute() {
            thread.start();
        }
    }

    public static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("pong");
        }
    }

    public static void main(String[] args) {
//        MyExecutor myExecutor = new MyExecutor();
//        myExecutor.execute();

        MyThread t = new MyThread();
        t.run();
        System.out.println("ping");
    }

}
