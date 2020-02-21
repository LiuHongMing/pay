package com.github.tiger.test.concurrent.future;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(JUnit4.class)
public class TestCompletableFuture {

    @Test
    public void testEither() {
        Random random = new Random();

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "from future1";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "from future2";
        });

        /**
         * acceptEither
         *
         * 当任意一个CompletableFuture完成的时候，action这个消费者就会被执行。
         */
        CompletableFuture<Void> future = future1.acceptEither(future2,
                str -> System.out.println("the future is " + str));

        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        /**
         * applyToEither
         *
         * 当任意一个 CompletableFuture 完成的时候，Function 会被执行，
         * 它的返回值会当作新的 CompletableFuture<U> 的计算结果。
         */
        CompletableFuture<String> future3 = future1.applyToEither(future2,
                str -> "the future is " + str
        );

        try {
            String ret = future3.get();
            System.out.println(ret);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAllOf() {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "tony");
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "cafei");
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "aaron");
        /**
         * allOf
         *
         * 在所有 Future 对象完成后结束，并返回一个 Future
         */
        CompletableFuture.allOf(future1, future2, future3)
                .thenApply(v ->
                        Stream.of(future1, future2, future3)
                                .map(CompletableFuture::join)
                                .collect(Collectors.joining("  ")))
                .thenAccept(System.out::print);
    }

    @Test
    public void testAnyOf() {
        Random rand = new Random();

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(rand.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "from future1";
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(rand.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "from future2";
        });
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(rand.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "from future3";
        });

        /**
         * anyOf
         *
         * 在任何一个 Future 对象结束后结束，并返回一个 Future。
         */
        CompletableFuture<Object> future = CompletableFuture.anyOf(future1, future2, future3);

        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAsync() {
        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().isDaemon());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("done=" + cf.isDone());
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("done=" + cf.isDone());
    }

    @Test
    public void testExceptionally() {

        /**
         * exceptionally
         */
        CompletableFuture.supplyAsync(() -> "hello world")
                .thenApply(s -> {
                    s = null;
                    int length = s.length();
                    return length;
                }).thenAccept(i -> System.out.println(i))
                .exceptionally(t -> {
                    System.out.println("Unexpected error:" + t);
                    return null;
                });

        CompletableFuture.supplyAsync(() -> "hello world")
                .thenApply(s -> {
//                    s = null;
                    int length = s.length();
                    return length;
                }).thenAccept(i -> System.out.println(i))
                .exceptionally(t -> {
                    System.out.println("Unexpected error:" + t);
                    return null;
                });

        /**
         * whenComplete
         */
        CompletableFuture.supplyAsync(() -> "hello world")
                .thenApply(s -> {
                    s = null;
                    int length = s.length();
                    return length;
                }).thenAccept(i -> System.out.println(i))
                .whenComplete((result, throwable) -> {

                    if (throwable != null) {
                        System.out.println("Unexpected error:" + throwable);
                    } else {
                        System.out.println(result);
                    }

                });
    }

}
