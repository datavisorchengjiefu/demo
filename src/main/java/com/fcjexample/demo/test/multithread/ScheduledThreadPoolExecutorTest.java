/*************************************************************************
 *
 * Copyright (c) 2021, DATAVISOR, INC.
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

import org.junit.Test;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ScheduledThreadPoolExecutorTest {
    private static ScheduledThreadPoolExecutor executor;
    private static Runnable task;
    private static volatile boolean flag = true;
    static ScheduledFuture scheduledFuture;

    public static void main(String[] args) throws Exception {
        //        while (flag) { // wrong
        executor = initExecutor();
        task = initTask();
        test();
        //        }

        //        while (flag) {
        //            executor.shutdown();
        //        }
    }

    private static void test() throws Exception {
        scheduledFuture = executor.scheduleWithFixedDelay(task, 1, 3, SECONDS);

        print("before get");
        //        if (scheduledFuture.isDone()) {
        //            print("is done");
        //        }
        //        if (scheduledFuture.isCancelled()) {
        //            print("is isCancelled");
        //        }

        //        scheduledFuture.get();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        print("after get");
    }

    //    @Before
    //    public void before() {
    //        executor = initExecutor();
    //        task = initTask();
    //    }

    private static ScheduledThreadPoolExecutor initExecutor() {
        return new ScheduledThreadPoolExecutor(1);
    }

    private static Runnable initTask() {
        long start = System.currentTimeMillis();
        return () -> {
            print("start task: " + getPeriod(start, System.currentTimeMillis()));
            //                        sleep(SECONDS, 10);
            print("end task: " + getPeriod(start, System.currentTimeMillis()));
            if (getPeriod(start, System.currentTimeMillis()) > 9) {
                print("shutting down");
                //                scheduledFuture.cancel(false);
                executor.shutdown();
            }
        };
    }

    @Test
    public void testFixedTask() {
        print("start main thread");
        executor.scheduleAtFixedRate(task, 1, 3, SECONDS);
        sleep(SECONDS, 12);
        print("end main thread");
    }

    @Test
    public void testDelayedTask() {
        //        print("start main thread");
        executor.scheduleWithFixedDelay(task, 2, 3, SECONDS);
        //        sleep(SECONDS, 15);
        //        print("end main thread");
    }

    private void sleep(TimeUnit unit, long time) {
        try {
            unit.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static int getPeriod(long start, long end) {
        return (int) (end - start) / 1000;
    }

    private static void print(String msg) {
        System.out.println(msg);
    }
}
