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

public class TestModificationException {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestModificationException.class);

    public static void main(String[] args) throws InterruptedException {
        // A.
        //        ConcurrentHashMap<Integer, ConcurrentHashMap<String, String>> mapOuter = new ConcurrentHashMap<>();
        //        ConcurrentHashMap<String, String> maxMap = new ConcurrentHashMap<>();
        //        maxMap.put("1max", "1ha");
        //        maxMap.put("2max", "2ha");
        //        maxMap.put("3max", "3ha");
        //
        //        ConcurrentHashMap<String, String> map02 = new ConcurrentHashMap<>();
        //        map02.put("1", "1ha");
        //        map02.put("2", "2ha");
        //        map02.put("3", "3ha");
        //
        //        mapOuter.put(100, maxMap);
        //        mapOuter.put(101, new ConcurrentHashMap<>());
        //
        //        ExecutorService threadPoolExecutor = new ThreadPoolExecutor(4, 4, 0, TimeUnit.SECONDS,
        //                new LinkedBlockingQueue<Runnable>(100), new ThreadFactory() {
        //            AtomicInteger integer = new AtomicInteger();
        //
        //            @Override public Thread newThread(Runnable r) {
        //                return new Thread(r, "test-modification-exception" + integer.getAndIncrement());
        //            }
        //        });
        //        threadPoolExecutor.submit(new Runnable() {
        //            @Override public void run() {
        //                try {
        //
        //                    maxMap.put("4max", "tmphaa4");
        //                    LOGGER.info("start to wait in runnable.");
        //                    Thread.sleep(2000);
        //                } catch (InterruptedException e) {
        //                    e.printStackTrace();
        //                }
        //                maxMap.put("5max", "tmphaa4");
        //                mapOuter.put(108, map02);
        //                LOGGER.info("finish putting in runnable.");
        //            }
        //        });
        //
        //        //        // case 1. mapOuter如果只有1+1不能复现，如果2+1就能复现
        //        //        Map<Integer, Map<String, String>> mapOuterClone = new HashMap<>(mapOuter);
        //        //        for (Map.Entry<Integer, Map<String, String>> entry : mapOuterClone.entrySet()) {
        //        //            LOGGER.info("start to wait in main");
        //        //            LOGGER.info("entry is {}", entry.getKey());
        //        //            Thread.sleep(3000);
        //        //            LOGGER.info("finish waiting in main");
        //        //        }
        //
        //        // case 2.
        //        //        Map<Integer, Map<String, String>> mapOuterClone = new HashMap<>(mapOuter);
        //        //        Map<Integer, Map<String, String>> mapOuterClone = new ConcurrentHashMap<>(mapOuter);
        //        //        Map<Integer, Map<String, String>> mapOuterClone = new ConcurrentHashMap<>(
        //        //                mapOuter);// 单独外面是这个也没有用
        //        // 重要！如果outer和里面的map都有变化，那么只有mapOuterClone是不够的。在循环里面的innerMap在里面也重新赋值。
        //        // 或者直接在最外面同时把out和inner都copy一下，然后给一个finalMapClone.
        //        // finalMapClone
        //        //        Map<Integer, Map<String, String>> finalMapClone = generateFinalMapClone(mapOuter);
        //
        //        for (Map.Entry<Integer, ConcurrentHashMap<String, String>> entry : mapOuter.entrySet()) {
        //            // a. right
        //            //            Map<String, String> innerMap = new HashMap<>(
        //            //                    entry.getValue());// outer也变化的话，mapOuter.entrySet就报错了，不会到innerMap.entrySet报错了
        //            // b. right
        //            //            Map<String, String> innerMap = (Map<String, String>) ((HashMap<String, String>) entry
        //            //                    .getValue()).clone();
        //            // c. if not finalMapClone, will be wrong
        //            Map<String, String> innerMap = entry.getValue();
        //            for (Map.Entry<String, String> stringStringEntry : innerMap.entrySet()) {
        //                Thread.sleep(1000);
        //                System.out.println("print is " + stringStringEntry.getKey());
        //            }
        //        }

        // B.
        //        Map<Integer, Map<String, String>> mapOuter = new HashMap<>();
        //        Map<String, String> maxMap = new HashMap<>();
        //        maxMap.put("1max", "1ha");
        //        maxMap.put("2max", "2ha");
        //        maxMap.put("3max", "3ha");
        //
        //        Map<String, String> map02 = new HashMap<>();
        //        map02.put("1", "1ha");
        //        map02.put("2", "2ha");
        //        map02.put("3", "3ha");
        //
        //        mapOuter.put(100, maxMap);
        //        mapOuter.put(101, new HashMap<>());
        //
        //        ExecutorService threadPoolExecutor = new ThreadPoolExecutor(4, 4, 0, TimeUnit.SECONDS,
        //                new LinkedBlockingQueue<Runnable>(100), new ThreadFactory() {
        //            AtomicInteger integer = new AtomicInteger();
        //
        //            @Override public Thread newThread(Runnable r) {
        //                return new Thread(r, "test-modification-exception" + integer.getAndIncrement());
        //            }
        //        });
        //        threadPoolExecutor.submit(new Runnable() {
        //            @Override public void run() {
        //                try {
        //
        //                    maxMap.put("4max", "tmphaa4");
        //                    LOGGER.info("start to wait in runnable.");
        //                    Thread.sleep(2000);
        //                } catch (InterruptedException e) {
        //                    e.printStackTrace();
        //                }
        //                maxMap.put("5max", "tmphaa4");
        //                mapOuter.put(108, map02);
        //                LOGGER.info("finish putting in runnable.");
        //            }
        //        });
        //
        //        //        // case 1. mapOuter如果只有1+1不能复现，如果2+1就能复现
        //        //        Map<Integer, Map<String, String>> mapOuterClone = new HashMap<>(mapOuter);
        //        //        for (Map.Entry<Integer, Map<String, String>> entry : mapOuterClone.entrySet()) {
        //        //            LOGGER.info("start to wait in main");
        //        //            LOGGER.info("entry is {}", entry.getKey());
        //        //            Thread.sleep(3000);
        //        //            LOGGER.info("finish waiting in main");
        //        //        }
        //
        //        // case 2.
        //        //        Map<Integer, Map<String, String>> mapOuterClone = new HashMap<>(mapOuter);
        //        //        Map<Integer, Map<String, String>> mapOuterClone = new ConcurrentHashMap<>(mapOuter);
        //        //        Map<Integer, Map<String, String>> mapOuterClone = new ConcurrentHashMap<>(
        //        //                mapOuter);// 单独外面是这个也没有用
        //        // 重要！如果outer和里面的map都有变化，那么只有mapOuterClone是不够的。在循环里面的innerMap在里面也重新赋值。
        //        // 或者直接在最外面同时把out和inner都copy一下，然后给一个finalMapClone.
        //        // finalMapClone
        //        Map<Integer, Map<String, String>> finalMapClone = generateFinalMapClone(mapOuter);
        //
        //        for (Map.Entry<Integer, Map<String, String>> entry : finalMapClone.entrySet()) {
        //            // a. right
        //            //            Map<String, String> innerMap = new HashMap<>(
        //            //                    entry.getValue());// outer也变化的话，mapOuter.entrySet就报错了，不会到innerMap.entrySet报错了
        //            // b. right
        //            //            Map<String, String> innerMap = (Map<String, String>) ((HashMap<String, String>) entry
        //            //                    .getValue()).clone();
        //            // c. if not finalMapClone, will be wrong
        //            Map<String, String> innerMap = entry.getValue();
        //            for (Map.Entry<String, String> stringStringEntry : (new HashMap<>(innerMap))
        //                    .entrySet()) {
        //                Thread.sleep(1000);
        //                System.out.println("print is " + stringStringEntry.getKey());
        //            }
        //        }

        Map<String, String> map = new HashMap<>();
        map.put("1", "test");
        map.put("2", "test");
        map.put("3", "test");

        Thread thread = new Thread(new Runnable() {
            @Override public void run() {
                try {
                    map.put("4", "test");
                    Thread.sleep(2000);
                    map.put("5", "test");
                } catch (InterruptedException e) {
                }
            }
        });
        thread.start();

        //        Map<String, String> copyMap = new HashMap<>(map);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            Thread.sleep(1000);
            LOGGER.info("result is {}", entry.getKey());
        }

    }

    private static Map<Integer, Map<String, String>> generateFinalMapClone(
            Map<Integer, Map<String, String>> mapOuter) {
        Map<Integer, Map<String, String>> tempFinalMapClone = new HashMap<>(mapOuter);
        Map<Integer, Map<String, String>> finalMapClone = new HashMap<>();
        tempFinalMapClone.forEach((key, value) -> {
            finalMapClone.put(key, new HashMap<>(value));
            //                finalMapClone.put(key, value);
        });
        return finalMapClone;
    }
}
