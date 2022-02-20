/*************************************************************************
 *
 * Copyright (c) 2022, DATAVISOR, INC.
 * All rights reserved.
 * __________________
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of DataVisor, Inc.
 * The intellectual and technical concepts contained
 * herein are proprietary to DataVisor, Inc. and
 * may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from DataVisor, Inc.
 */

package com.fcjexample.demo.test.multithread.completablefuture;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.BiFunction;

public class CompletableFutureTest {
    public static void main(String[] args) throws Exception {
        // 1.
        //        //        Future<String> completableFuture = calculateAsync();
        //        Future<String> completableFuture =
        //                //                                CompletableFuture.completedFuture("Hello11");
        //
        //                CompletableFuture.supplyAsync(new Supplier<String>() {
        //                    @Override public String get() {
        //                        return "hello supplier";
        //                    }
        //                });
        //        String result = completableFuture.get();
        //        System.out.println("result is " + result);

        // 2.
        //        CompletableFuture<String> completableFuture
        //                = CompletableFuture.supplyAsync(() -> "Hello");
        //
        //        CompletableFuture<String> future = completableFuture
        //                .thenApply(s -> s + " World");
        //        String result = completableFuture.get();
        //        System.out.println("result is " + result);

        // 3.
        //        CompletableFuture<String> completableFuture
        //                = CompletableFuture.supplyAsync(new Supplier<String>() {
        //            @Override public String get() {
        //                return "Hello";
        //            }
        //        });
        //
        //        CompletableFuture<Void> future = completableFuture
        //                //                .thenAccept(s -> {
        //                .thenAcceptAsync(s -> {
        //                    try {
        //                        Thread.sleep(1000);
        //                    } catch (InterruptedException e) {
        //                        e.printStackTrace();
        //                    }
        //                    System.out.println("Computation returned: " + s);
        //                });
        //        System.out.println("666");
        //        future.get();

        // 4.

        //        CompletableFuture<String> completableFuture
        //                = CompletableFuture.supplyAsync(() -> "Hello");
        //
        //        CompletableFuture<Void> future = completableFuture
        //                .thenRun(new Runnable() { // run vs runAsync
        //                    //                .thenRunAsync(new Runnable() {
        //                    @Override public void run() {
        //                        try {
        //                            Thread.sleep(1000);
        //                        } catch (InterruptedException e) {
        //                            e.printStackTrace();
        //                        }
        //                        System.out.println("Computation finished.");
        //                    }
        //                });
        //
        //        System.out.println("777");
        //        future.get();

        // 5. 
        //        CompletableFuture<String> future1
        //                = CompletableFuture.supplyAsync(() -> "Hello");
        //        CompletableFuture<String> future2
        //                = CompletableFuture.supplyAsync(new Supplier<String>() {
        //            @Override public String get() {
        //                return "Beautiful";
        //                //                throw new RuntimeException("exceptionHa!");
        //            }
        //        });
        //        CompletableFuture<String> future3
        //                = CompletableFuture.supplyAsync(() -> "World");
        //
        //        CompletableFuture<Void> combinedFuture
        //                = CompletableFuture.allOf(future1, future2, future3);
        //
        //        // ...
        //
        //        String combined = Stream.of(future1, future2, future3)
        //                //                .map(CompletableFuture::get)// cant' use get
        //                .map(CompletableFuture::join)
        //                .collect(Collectors.joining(" "));
        //        System.out.println("combined is " + combined);

        // 6.
        String name = null;
        //        String name = "tom";
        //        String name = "david";

        // ...

        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> {
            //            if (name.equals("tom")) {
            if (name == null) {
                throw new RuntimeException("Computation error!");
            }
            return "Hello, " + name;
        }).handle(new BiFunction<String, Throwable, String>() {
            @Override public String apply(String s, Throwable t) {
                //                return s != null ? s : "Hello, Stranger!";
                return t == null ? s : "Hello, Stranger!";

            }
        });
        System.out.println("exception case is: " + completableFuture.get());

        //        String result = future.get();
        //        System.out.println("result is " + result);
    }

    public static Future<String> calculateAsync() throws InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(new Callable<Object>() {
            @Override public Object call() throws Exception {
                Thread.sleep(500);
                completableFuture.complete("Hello");
                return null;
            }
        });

        return completableFuture;
    }
}
