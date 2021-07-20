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

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ScheduledThreadPoolExecutorTest {
    private static ScheduledThreadPoolExecutor executor;
    private static Runnable task;
    private static volatile boolean flag = true;

    public static void main(String[] args) {
        while (flag) {
            executor = initExecutor();
            task = initTask();
            executor.scheduleWithFixedDelay(task, 1, 3, SECONDS);
        }

        executor.shutdown();
    }

    //    @Before
    //    public void before() {
    //        executor = initExecutor();
    //        task = initTask();
    //    }

    private static ScheduledThreadPoolExecutor initExecutor() {
        return new ScheduledThreadPoolExecutor(2);
    }

    private static Runnable initTask() {
        long start = System.currentTimeMillis();
        return () -> {
            print("start task: " + getPeriod(start, System.currentTimeMillis()));
            //            sleep(SECONDS, 10);
            print("end task: " + getPeriod(start, System.currentTimeMillis()));
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
