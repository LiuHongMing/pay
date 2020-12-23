package com.github.tiger.test.netty;

import io.netty.util.concurrent.*;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liuhongming
 * @className: PromiseListenerTest
 * @description: TODO
 * @date 2020/12/10
 */
public class PromiseListenerTest {

    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
        EventExecutor executor = ImmediateEventExecutor.INSTANCE;
        // root
        Promise<String> root = new DefaultPromise<>(executor);
        Promise<String> p1 = new DefaultPromise<>(executor);
        Promise<String> p2 = new DefaultPromise<>(executor);
        Promise<String> p3 = new DefaultPromise<>(executor);
        Promise<String> p4 = new DefaultPromise<>(executor);
        Promise<String> p5 = new DefaultPromise<>(executor);
        Promise<String> p6 = new DefaultPromise<>(executor);
        Promise<String> p7 = new DefaultPromise<>(executor);
        Promise<String> p8 = new DefaultPromise<>(executor);
        Promise<String> p9 = new DefaultPromise<>(executor);
        Promise<String> p10 = new DefaultPromise<>(executor);
        p1.addListener(new Listener(p2));
        p2.addListener(new Listener(p3));
        p3.addListener(new Listener(p4));
        p4.addListener(new Listener(p5));
        p5.addListener(new Listener(p6));
        p6.addListener(new Listener(p7));
        p7.addListener(new Listener(p8));
        p8.addListener(new Listener(p9));
        p9.addListener(new Listener(p10));
        root.addListener(new Listener(p1));
        root.setSuccess("success");
        Thread.sleep(Long.MAX_VALUE);
    }

    private static class Listener implements GenericFutureListener<Future<String>> {

        private final String name;
        private final Promise<String> promise;

        public Listener(Promise<String> promise) {
            this.name = "listener-" + COUNTER.getAndIncrement();
            this.promise = promise;
        }

        @Override
        public void operationComplete(Future<String> future) throws Exception {
            System.out.println(String.format("监听器[%s]回调成功...", name));
            if (null != promise) {
                promise.setSuccess("success");
            }
        }
    }

}
