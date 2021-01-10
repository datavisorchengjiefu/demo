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

public class TestMain03 {

    private static final Logger logger = LoggerFactory.getLogger(TestMain03.class);

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        list.add("ttt");
        list.add(null);
        list.add("hhh");
        list.add(null);
        System.out.println(list.size());

        Set<TestEntity02> set = new HashSet<>();
        TestEntity02 testEntity02 = new TestEntity02(12L, "user01", "h1");
        TestEntity02 testEntity03 = new TestEntity02(12L, "user01", "h1");

        set.add(testEntity02);
        set.add(testEntity03);

        System.out.println(set.size());

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
    }
}
