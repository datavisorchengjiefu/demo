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

    public static void main(String[] args) {
        ExecutorService threadPool2 = new ThreadPoolExecutor(3, 4, 0, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(100), new ThreadFactory() {
            AtomicInteger integer = new AtomicInteger();

            @Override public Thread newThread(Runnable r) {
                return new Thread(r, "test-ha" + integer.getAndIncrement());
            }
        });

        List<Future<String>> futureList = new ArrayList<>();
        //        for (int i = 0; i < 10; i++) {
        //            futureList.add(threadPool2.submit(new CallTestClass(i)));
        //        }

        List<Integer> timeoutList = new ArrayList<>();
        timeoutList.add(1000);
        timeoutList.add(2000);
        timeoutList.add(5000);

        List<CallTestClass> callTestClassList = new ArrayList<>();
        for (Integer integer : timeoutList) {
            callTestClassList.add(new CallTestClass(integer));
            //            futureList.add(threadPool2.submit(new CallTestClass(integer)));
        }

        try {
            List<Future<String>> futureListInvoke = threadPool2.invokeAll(callTestClassList);

            for (Future<String> future : futureListInvoke) {
                long start = System.currentTimeMillis();
                System.out.println("start..");
                try {
                    //                System.out.println(futureList.get(i).isDone());
                    System.out.println(future.get());
                    //                System.out.println(futureList.get(i).get(1200, TimeUnit.MILLISECONDS));
                } catch (Exception e) {
                    logger.error("error", e);
                    threadPool2.shutdown();
                }
                System.out.println(System.currentTimeMillis() - start);
            }
        } catch (InterruptedException e) {
            logger.error("Failed. ", e);
        }

        //        for (int i = 0; i < timeoutList.size(); i++) {
        //            long start = System.currentTimeMillis();
        //            System.out.println("start..");
        //            try {
        //                //                System.out.println(futureList.get(i).isDone());
        //                System.out.println(futureList.get(i).get());
        //                //                System.out.println(futureList.get(i).get(1200, TimeUnit.MILLISECONDS));
        //            } catch (Exception e) {
        //                logger.error("error", e);
        //                threadPool2.shutdown();
        //            }
        //            System.out.println(System.currentTimeMillis() - start);
        //        }

        threadPool2.shutdown();

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
