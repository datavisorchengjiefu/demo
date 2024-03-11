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

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int replayThreadCount = 4;
        ExecutorService signoffThreadPool = Executors.newFixedThreadPool(replayThreadCount);
        AtomicLong atomicLong = new AtomicLong(50);
        signoffThreadPool.submit(new ConsumerSignoff(atomicLong, 1));
        signoffThreadPool.submit(new ConsumerSignoff(atomicLong, 2));
    }

    public static void mainV2(String[] args) throws ExecutionException, InterruptedException {
        int replayThreadCount = 4;
        ExecutorService signoffThreadPool = Executors.newFixedThreadPool(replayThreadCount);

        ExecutorService signoffThreadPool2 = new ThreadPoolExecutor(30, 30, 0, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(100), new ThreadFactory() {
            AtomicInteger integer = new AtomicInteger();

            @Override public Thread newThread(Runnable r) {
                return new Thread(r, "test-ha" + integer.getAndIncrement());
            }
        });

        //
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4,
                new ThreadFactory() {
                    AtomicInteger integer = new AtomicInteger();

                    @Override public Thread newThread(Runnable r) {
                        return new Thread(r, "testfcj-consumer-" + integer.getAndIncrement());
                    }
                });

        int count = 40;
        List<Future<String>> futureList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            //            futureList.add(signoffThreadPool.submit(new ConsumerSignoff()));
            //            futureList.add(signoffThreadPool2.submit(new ConsumerSignoff()));
            //            futureList.add(signoffThreadPool.submit(new CallFcjClass()));
            try {

                futureList.add(signoffThreadPool2.submit(new CallFcjClass()));

                //                futureList.add(executorService.scheduleWithFixedDelay(new Runnable() {
                //                    @Override public void run() {
                //                        try {
                //                            Thread.sleep(1000);
                //                        } catch (InterruptedException e) {
                //                            e.printStackTrace();
                //                        }
                //                        //            int a = 8 / 0;
                //
                //                        LOGGER.info("callha02!!");
                //                    }
                //                }, 200, 200, TimeUnit.MILLISECONDS));
            } catch (Exception e) {
                LOGGER.error("submit failed. ", e);
            }

            LOGGER.info("fcjha01 finished.");

        }

        for (Future<String> future : futureList) {
            try {
                // CallFcjClass线程池里的抛错，如果没有future.get()，这外面是不会看到报错的。所以最好在线程池里自己try catch住打log。
                System.out.println("result: " + future.get());
                System.out.println("haha0101");

            } catch (Exception e) {
                LOGGER.error("get() failed. ", e);
            }
        }

        signoffThreadPool2.shutdown();

        System.out.println("end!!!");

    }

    // 下面这个在这个类里没有用
    @PreDestroy
    public void after() {
        System.out.println("after!! ");
    }

    static class ConsumerSignoff implements Runnable {
        private AtomicLong atomicLong;

        private Integer num;

        public ConsumerSignoff() {
        }

        public ConsumerSignoff(AtomicLong atomicLong, Integer num) {
            this.atomicLong = atomicLong;
            this.num = num;
        }

        @Override public void run() {

            try {
                //                Thread.sleep(1000);
                //                System.out.println("ha: " + Thread.currentThread().getName());

                int count = 30;
                while (count > 0) {
                    Thread.sleep(100);
                    if (num.compareTo(1) == 0 && count % 4 == 0) {
                        atomicLong.addAndGet(10);
                    }
                    LOGGER.info("num {} atomicLong is {}. ", num, atomicLong.get());
                    count--;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
