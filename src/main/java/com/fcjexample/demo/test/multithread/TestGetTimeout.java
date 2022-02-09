/*************************************************************************
 *
 * Copyright (c) 2020, DATAVISOR, INC.
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

package com.fcjexample.demo.test.multithread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TestGetTimeout {

    private static final Logger logger = LoggerFactory.getLogger(TestGetTimeout.class);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPool2 = new ThreadPoolExecutor(2, 2, 0, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(100), new ThreadFactory() {
            AtomicInteger integer = new AtomicInteger();

            @Override public Thread newThread(Runnable r) {
                return new Thread(r, "test-ha" + integer.getAndIncrement());
            }
        }, new ThreadPoolExecutor.AbortPolicy());
        //        ExecutorService threadPool2 = Executors.newSingleThreadExecutor();

        List<Future<String>> futureList = new ArrayList<>();

        List<Integer> timeoutList = new ArrayList<>();
        timeoutList.add(2000);
        timeoutList.add(3000);
        timeoutList.add(8000);
        timeoutList.add(8000);
        timeoutList.add(1000);

        List<Integer> timeoutList02 = new ArrayList<>();
        //        timeoutList02.add(3000);
        //        timeoutList02.add(4000);
        //        timeoutList02.add(8000);
        //        timeoutList02.add(2000);
        //        timeoutList02.add(1000);

        timeoutList02.add(8000);
        timeoutList02.add(1000);
        timeoutList02.add(8000);

        List<CallTestClass> callTestClassList = new ArrayList<>();
        for (Integer integer : timeoutList) {
            // invokeAll
            callTestClassList.add(new CallTestClass(integer));

            /**
             * invokeAll和submit不能一起用，因为两者调用后都会开始执行call里面的方法，会重复执行
             * 执行与future.get()是没有关系的，get()只是用于获取结果
             */
            // awaitTermination 和invokeAll二选一
            //            try {
            //                futureList.add(threadPool2.submit(new CallTestClass(integer)));
            //            } catch (Exception e) {
            //                logger.error("fail submit. ", e);
            //            }
        }

        /**
         * invokeAll
         */
        try {

            //            threadPool2.shutdown();

        } catch (Exception e) {
            logger.error("Failed. ", e);
        }

        // part 2

        List<CallTestClass> callTestClassList02 = new ArrayList<>();
        for (Integer integer : timeoutList02) {
            // invokeAll
            callTestClassList02.add(new CallTestClass(integer));

            /**
             * invokeAll和submit不能一起用，因为两者调用后都会开始执行call里面的方法，会重复执行
             * 执行与future.get()是没有关系的，get()只是用于获取结果
             */
            // awaitTermination 和invokeAll二选一
            //            try {
            //                futureList.add(threadPool2.submit(new CallTestClass(integer)));
            //            } catch (Exception e) {
            //                logger.error("fail submit. ", e);
            //            }
        }

        /**
         * invokeAll
         */
        try {

            //            threadPool2.shutdown();

        } catch (Exception e) {
            logger.error("Failed. ", e);
        }

        try {

            //
            Thread thread = new Thread(new Runnable() {
                @Override public void run() {
                    System.out.println("start to invokeAll");
                    long startInvokeAll = System.currentTimeMillis();
                    List<Future<String>> futureList = new ArrayList<>();
                    try {

                        futureList = threadPool2
                                .invokeAll(callTestClassList, 4000, TimeUnit.MILLISECONDS);
                    } catch (Exception e) {
                        logger.error("haha", e);

                    }

                    List<Future<String>> finalFutureList = futureList;

                    System.out
                            .println("last01 for " + (System.currentTimeMillis() - startInvokeAll));
                    for (Future<String> future : finalFutureList) {
                        long start = System.currentTimeMillis();
                        try {
                            //                System.out.println(futureList.get(i).isDone());
                            System.out.println("future getha: " + future.get());
                            //                    System.out.println(futureList.get(i).get(1200, TimeUnit.MILLISECONDS));// todo get里面的timeout用处不是很大
                        } catch (Exception e) {
                            logger.error("error", e);
                            //                    threadPool2.shutdown();
                        }
                        System.out.println(System.currentTimeMillis() - start);
                    }
                }
            });
            thread.start();

            //            Thread.sleep(200);
            Thread.sleep(4000);

            //
            Thread thread02 = new Thread(new Runnable() {
                @Override public void run() {
                    System.out.println("start to invokeAll");
                    long startInvokeAll02 = System.currentTimeMillis();
                    List<Future<String>> futureList02 = new ArrayList<>();

                    try {
                        futureList02 = threadPool2
                                .invokeAll(callTestClassList02, 4000, TimeUnit.MILLISECONDS);
                        //                                .invokeAll(callTestClassList02);
                    } catch (Exception e) {
                        logger.error("haha", e);

                    }
                    System.out.println(
                            "last02 for " + (System.currentTimeMillis() - startInvokeAll02));

                    List<Future<String>> finalFutureList02 = futureList02;
                    for (Future<String> future : finalFutureList02) {
                        long start = System.currentTimeMillis();
                        try {
                            //                System.out.println(futureList.get(i).isDone());
                            System.out.println("future getha02: " + future.get());
                            //                    System.out.println(futureList.get(i).get(1200, TimeUnit.MILLISECONDS));// todo get里面的timeout用处不是很大
                        } catch (Exception e) {
                            logger.error("error", e);
                            //                    threadPool2.shutdown();
                        }
                        System.out.println(System.currentTimeMillis() - start);
                    }
                }
            });

            thread02.start();

        } catch (Exception e) {
            logger.error("get() failed. ", e);
        }

        System.out.println("haha01: " + threadPool2.isShutdown());
        //        threadPool2.shutdown();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(threadPool2.isTerminated());

        // ========================

        /**
         * awaitTermination
         */
        //        System.out.println("start to await");
        //        long startAwait = System.currentTimeMillis();
        //        try {
        //            threadPool2.shutdown();
        //            if (!threadPool2.awaitTermination(6300, TimeUnit.MILLISECONDS)) {
        //                logger.info("timeout!");
        //                threadPool2.shutdownNow();
        //            }
        //        } catch (Exception e) {
        //            logger.error("threadPool failed. ", e);
        //            threadPool2.shutdownNow();
        //        }
        //        System.out.println("last for " + (System.currentTimeMillis() - startAwait));
        //
        //        for (int i = 0; i < futureList.size(); i++) {
        //            long start = System.currentTimeMillis();
        //            System.out.println("start..");
        //            try {
        //                //                System.out.println(futureList.get(i).isDone());
        //                System.out.println(futureList.get(i).get());
        //                //                System.out.println(futureList.get(i).get(1200, TimeUnit.MILLISECONDS));
        //            } catch (Exception e) {
        //                logger.error("error", e);
        //            }
        //            System.out.println("gap is: " + (System.currentTimeMillis() - start));
        //        }

    }

    static class CallTestClass implements Callable<String> {

        private final int sleepTime;

        public CallTestClass(int sleepTime) {
            this.sleepTime = sleepTime;
        }

        @Override public String call() throws Exception {
            Thread.sleep(sleepTime);
            return Thread.currentThread().getName() + "test: " + sleepTime;
        }
    }
}
