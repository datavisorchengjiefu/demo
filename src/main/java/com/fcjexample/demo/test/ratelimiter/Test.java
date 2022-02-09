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

package com.fcjexample.demo.test.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * 这个是控制了整体的时间
 */
public class Test {
    private static final Logger logger = LoggerFactory.getLogger(Test.class);
    static AtomicInteger atomicInteger = new AtomicInteger(1);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(50);

        // given
        RateLimiter rateLimiter = RateLimiter.create(10);

        List<Future> list = new ArrayList<>();

        // when
        long startTime = System.currentTimeMillis();
        IntStream.range(1, 51).forEach(i -> {
            rateLimiter.acquire();
            doSomeLimitedOperation();
            //            list.add(pool.submit(new SemaphoreTest.Task()));
        });

        for (Future future : list) {
            future.get();
        }
        long elapsedTimeSeconds = System.currentTimeMillis() - startTime;

        System.out.println("elapsedTime: " + elapsedTimeSeconds);

        pool.shutdown();

    }

    private static void doSomeLimitedOperation() {
        try {
            logger.info("num is {}. ", atomicInteger.getAndIncrement());
            //            Thread.sleep(500);
            Thread.sleep(
                    500);// The 10 of RateLimiter.create(10) won't work here, will be 2 per second.
            //            Thread.sleep(1000 * atomicInteger.getAndIncrement());
            //            Thread.sleep(1000);
            //            Thread.sleep(100 * atomicInteger.getAndIncrement());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
