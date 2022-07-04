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

package com.fcjexample.demo.test.testpackage;

import com.fcjexample.demo.entity.TestEntity02;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TestMain03 {

    private static final Logger logger = LoggerFactory.getLogger(TestMain03.class);

    public static void main(String[] args) throws ClassNotFoundException {
        Map<Integer, Boolean> accumulatorDimMisMatch = new HashMap<>();
        accumulatorDimMisMatch.put(1, true);
        logger.info("1: {}. ", accumulatorDimMisMatch.get(1));
        logger.info("1: {}. ", accumulatorDimMisMatch.get(2));
        //        logger.info("1: {}. ", (false == accumulatorDimMisMatch.get(2)));// NPE
        //        logger.info("1: {}. ", (accumulatorDimMisMatch.get(2) == false));// NPE

        TestEntity02 testEntity0201 = new TestEntity02();
        testEntity0201.setName("01");
        testEntity0201.setAddress("qian01");
        TestEntity02 testEntity0202 = new TestEntity02();
        testEntity0202.setName("02");
        TestEntity02 testEntity0203 = new TestEntity02();
        testEntity0203.setName("03");
        Set<TestEntity02> testEntity02Set = new HashSet<>();
        testEntity02Set.add(testEntity0201);
        testEntity02Set.add(testEntity0202);
        testEntity02Set.add(testEntity0203);

        Map<String, TestEntity02> stringTestMain02Map = new ConcurrentHashMap<>();
        stringTestMain02Map.putIfAbsent("1", testEntity0201);
        stringTestMain02Map.putIfAbsent("1", testEntity0202);
        //        testEntity0201.setName("02_02");// useful
        //        testEntity0201 = null; // no use
        //        stringTestMain02Map.put("1", null);// fail
        stringTestMain02Map.remove("1");
        testEntity0201 = null;
        stringTestMain02Map.putIfAbsent("1", testEntity0203);

        List<TestEntity02> list01 = new ArrayList<>(testEntity02Set);
        System.out.println(list01.size());

        for (TestEntity02 testEntity02 : testEntity02Set) {
            testEntity02.setAddress("hou");
        }

        System.out.println(list01.size());

        System.out.println("=========================");

        String fcj01 = "fff_ee_egg";
        //        String fcj01 = null;
        String[] fcj01Result = fcj01
                .split("_");

        System.out.println(fcj01Result.length);

        Map<String, String> map = new HashMap<>();
        String temp = map.put("1", "11");
        System.out.println(temp);
        temp = map.put("1", "12");// get last value
        System.out.println(temp);
        System.out.println(map.get("1"));
        System.out.println("+++++++++++++++++");
        String temp01 = map.putIfAbsent("2", "22");
        System.out.println(temp01);
        temp01 = map.putIfAbsent("2", "23");
        System.out.println(temp01);
        System.out.println(map.get("2"));
        System.out.println("+++++++++++++++++");

        List<String> list = new ArrayList<>();
        list.add("ttt");
        list.add(null);
        list.add("hhh");
        list.add(null);
        System.out.println(list.size());

        Set<TestEntity02> set = new HashSet<>();
        TestEntity02 testEntity02 = new TestEntity02(12L, "user01", "h1");
        TestEntity02 testEntity03 = new TestEntity02(12L, "user01", "h1");
        testEntity02.setTimeout(89L);
        //        testEntity02.setTimeout(null);
        testEntity02.setHeight(37);
        //        Long time = null;
        //        testEntity02.setHeight(time);
        Long tempha = testEntity02.getTimeout();
        tempha = 100L;
        System.out.println(testEntity02.getTimeout());

        testEntity02.setTimeout(101L);
        System.out.println(testEntity02.getTimeout());

        set.add(testEntity02);
        set.add(testEntity03);

        System.out.println(set.size());
        System.out.println(set.toArray());
        System.out.println("haha01----");
        System.out.println(set.toString());
        //
        Map<String, Object> responseBody = new HashMap<>();
        Map<String, Object> map01 = new HashMap<>();
        map01.put("01h1", "h1value");
        map01.put("01h2", "h2value");
        Map<String, Object> map02 = new HashMap<>();
        map02.put("02u1", "u1value");
        map02.put("02u2", "u2value");

        responseBody.put("wai01", map01);
        responseBody.put("wai02", map02);
        responseBody.put("wai03", "test ha");

        for (Map.Entry<String, Object> responseBodyEntry : responseBody.entrySet()) {

            Map<String, Object> responseBodyMap;
            try {
                responseBodyMap = (Map<String, Object>) responseBodyEntry.getValue();
                logger.info("success for [{}]", String.valueOf(responseBodyEntry.getValue()));
            } catch (Exception e) {
                logger.warn("Cast to Map<String, Object> failed for [{}]",
                        String.valueOf(responseBodyEntry.getValue()));
            }
        }
        Map<Integer, Map<String, String>> mapOuter = new HashMap<>();
        Map<String, String> maxMap = new HashMap<>();
        maxMap.put("1", "1ha");
        maxMap.put("2", "2ha");
        maxMap.put("3", "3ha");

        mapOuter.put(100, maxMap);
        Map<String, String> mapCopy22 = new HashMap<>(mapOuter.get(100));
        Map<String, String> mapCopy23 = mapOuter.get(100);
        maxMap.put("3", "tmphaa");

        Map<Integer, TestEntity02> map1 = new HashMap<>();
        map1.put(1, new TestEntity02(1L, "name1", "FF1"));
        map1.put(2, new TestEntity02(2L, "name2", "FF2"));
        map1.put(3, new TestEntity02(3L, "name3", "FF3"));
        Map<Integer, TestEntity02> map1Copy = new HashMap<>(map1);
        TestEntity02 tmp = map1.get(3);
        tmp.setName("hahfcj");
        map1.put(3, tmp);
        map1.remove(2);
        map1Copy.put(4, new TestEntity02());

        Map<Integer, TestEntity02> map2Copy = new HashMap<>();
        map1.forEach((k, v) -> map2Copy.put(k, v));
        map2Copy.put(5, new TestEntity02());

        map1.get(2).setName("ha");

        System.out.println(map1.get(2).getName());
        System.out.println(map1Copy.get(2).getName());
        System.out.println(map2Copy.get(2).getName());

        Object obj01 = "ggg";
        Set<String> set1 = new HashSet<>();
        set1.add("s1");
        set1.add("s2");
        System.out.println(obj01.getClass());
        obj01 = set1;
        System.out.println(obj01.getClass());

        //        obj01 = (Set) obj01;

        Class<?> theClass = Class.forName(set1.getClass().getName());
        theClass.cast(obj01);
        System.out.println(obj01.getClass());

        System.out.println();
        System.out.println(((HashSet) obj01));

    }
}
