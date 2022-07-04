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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

/**
 * 这个是控制了整体的个数qps
 */
public class SemaphoreTest {
    private static final Logger logger = LoggerFactory.getLogger(SemaphoreTest.class);
    static AtomicInteger atomicInteger = new AtomicInteger(1);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(50);
        Semaphore semaphore = new Semaphore(3);
        int num = 6;
        List<Future> list = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < num; i++) {
            //            list.add(pool.submit(new Task()));
            semaphore.acquireUninterruptibly();
            //            Future future = pool.submit(new Task());
            CompletableFuture f = CompletableFuture.runAsync(new Task(), pool);
            f.whenComplete(new BiConsumer() {
                @Override public void accept(Object o, Object o2) {
                    semaphore.release();
                }
            });
            list.add(f);

        }

        for (Future future : list) {
            future.get();
        }

        long elapsedTimeSeconds = System.currentTimeMillis() - startTime;
        System.out.println("elapsedTime: " + elapsedTimeSeconds);

        pool.shutdown();

    }

    public static class Task implements Runnable {

        @Override public void run() {
            try {
                logger.info("num is {}. ", atomicInteger.get());
                Thread.sleep(1000 * atomicInteger.getAndIncrement());
                //                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class TaskV2 implements Runnable {

        @Override public void run() {
            try {
                logger.info("num is {}. ", atomicInteger.getAndIncrement());
                //                Thread.sleep(1000 * atomicInteger.getAndIncrement());
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
