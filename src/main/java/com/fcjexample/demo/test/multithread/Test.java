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

public class Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int replayThreadCount = 4;
        ExecutorService signoffThreadPool = Executors.newFixedThreadPool(replayThreadCount);

        ExecutorService signoffThreadPool2 = new ThreadPoolExecutor(4, 6, 0, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(100), new ThreadFactory() {
            AtomicInteger integer = new AtomicInteger();

            @Override public Thread newThread(Runnable r) {
                return new Thread(r, "test-ha" + integer.getAndIncrement());
            }
        });

        int count = 1;
        List<Future> futureList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            //            futureList.add(signoffThreadPool.submit(new ConsumerSignoff()));
            //            futureList.add(signoffThreadPool2.submit(new ConsumerSignoff()));
            //            futureList.add(signoffThreadPool.submit(new CallFcjClass()));
            try {

                futureList.add(signoffThreadPool2.submit(new CallFcjClass()));
            } catch (Exception e) {
                LOGGER.error("submit failed. ", e);
            }

            LOGGER.info("fcjha01 finished.");

        }

        for (Future future : futureList) {
            try {
                // CallFcjClass线程池里的抛错，如果没有future.get()，这外面是不会看到报错的。所以最好在线程池里自己try catch住打log。
                //                System.out.println("result: " + future.get());

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

        @Override public void run() {

            try {
                Thread.sleep(1000);
                System.out.println("ha: " + Thread.currentThread().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
