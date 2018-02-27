package com.github.tiger.test.netty;

import io.netty.util.concurrent.*;

public class FutureTest {

    static class ThisCompleteFuture extends CompleteFuture {

        /**
         * Creates a new instance.
         *
         * @param executor the {@link EventExecutor} associated with this future
         */
        protected ThisCompleteFuture(EventExecutor executor) {
            super(executor);
        }

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public Throwable cause() {
            return null;
        }

        @Override
        public Object getNow() {
            return null;
        }
    }

    public static void main(String[] args) {
        Promise<String> promise = new DefaultPromise<>(GlobalEventExecutor.INSTANCE);
        promise.addListener(new GenericFutureListener<Future<? super String>>() {
            @Override
            public void operationComplete(Future<? super String> future) throws Exception {
                if (future.isDone()) {
                    System.out.println("");
                }
            }
        });
    }
}
