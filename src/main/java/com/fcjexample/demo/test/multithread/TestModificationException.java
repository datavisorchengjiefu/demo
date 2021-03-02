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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TestModificationException {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestModificationException.class);

    public static void main(String[] args) throws InterruptedException {
        Map<Integer, Map<String, String>> mapOuter = new HashMap<>();
        Map<String, String> maxMap = new HashMap<>();
        maxMap.put("1", "1ha");
        maxMap.put("2", "2ha");
        maxMap.put("3", "3ha");

        mapOuter.put(100, maxMap);

        ExecutorService threadPoolExecutor = new ThreadPoolExecutor(4, 4, 0, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(100), new ThreadFactory() {
            AtomicInteger integer = new AtomicInteger();

            @Override public Thread newThread(Runnable r) {
                return new Thread(r, "test-modification-exception" + integer.getAndIncrement());
            }
        });
        threadPoolExecutor.submit(new Runnable() {
            @Override public void run() {
                try {

                    LOGGER.info("start to wait in runnable.");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                maxMap.put("4", "tmphaa4");
                LOGGER.info("finish putting in runnable.");
            }
        });

        for (Map.Entry<Integer, Map<String, String>> entry : mapOuter.entrySet()) {
            Map<String, String> map = new HashMap<>(entry.getValue());
            LOGGER.info("start to wait in main");
            Thread.sleep(3000);
            LOGGER.info("finish waiting in main");
            for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
                System.out.println("print is " + stringStringEntry.getKey());

            }
        }
    }
}
