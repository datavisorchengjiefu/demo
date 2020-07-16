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

package com.fcjexample.demo.test;

import com.fcjexample.demo.model.TT;
import com.fcjexample.demo.model.TestEntity;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);
    private static Consumer<Map.Entry<Integer, String>> mapConsumer = new Consumer<Map.Entry<Integer, String>>() {
        @Override public void accept(
                //                Map.Entry<Integer, Object> value) {
                Map.Entry<Integer, String> value) {

            System.out.println(value.getKey() + ": " + value.getValue());

        }
    };

    public static void main(String[] args) {
        System.out.println("hhh");

        TestEntity entity1 = new TestEntity();
        entity1.setName("h1");
        TestEntity entity2 = new TestEntity();
        entity2.setName("h2");
        TestEntity entity3 = new TestEntity();
        entity3.setName("h3");

        Set<TestEntity> set = new HashSet<>();
        set.add(entity1);
        set.add(entity2);

        Iterator<TestEntity> iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println("hahais: " + iterator.next().getName());
        }

        System.out.println(set.size());

        System.out.println(set.remove(entity2));
        System.out.println(set.size());

        System.out.println(set.remove(entity3));
        System.out.println(set.size());

        TestEntity entity4 = new TestEntity();
        entity4.setName("h4");

        Set<TestEntity> set1 = set;
        // 注意下面这个会同时影响set1和set
        set1.add(new TestEntity());

        Set<TestEntity> set2 = new HashSet<>(set);
        //        Set<TestEntity> set2 = new HashSet<>();
        //        set2.addAll(set);
        // 但下面这个只是会影响set2，不会影响
        set2.add(new TestEntity());
        System.out.println(set2.size());

        char a = 'A';
        String s = "gge4";
        System.out.println(s.getBytes().length);

        int endIndex = Math.min(2, s.length());
        System.out.println(s.substring(0, endIndex));

        JSONObject json = new JSONObject();

        // 8
        json.put("codeId", "12");
        // 11
        json.put("codeKey", "1234");
        int jsonLength = json.toJSONString().length();
        int endIndexException = Math.min(240 - jsonLength, s.length());

        List<String> lists = new ArrayList<>();
        lists.add("zhang");
        lists.add("wang");
        lists.add("cheng");
        lists.add("zhou");
        lists.stream()
                .filter(new Predicate<String>() {
                    @Override public boolean test(String s) {
                        return s.length() > 4;
                    }
                })
                .forEach(System.out::println); // zhang cheng

        Map<Integer, String> HOSTING = new HashMap<>();
        HOSTING.put(1, "linode.com");
        HOSTING.put(2, "heroku.com");
        HOSTING.put(3, "digitalocean.com");
        HOSTING.put(4, "aws.amazon.com");

        System.out.println("==========");
        HOSTING.entrySet().stream().filter(new Predicate<Map.Entry<Integer, String>>() {
            @Override public boolean test(Map.Entry<Integer, String> integerStringEntry) {
                return integerStringEntry.getKey() > 2;
            }
        }).forEach(mapConsumer);
        System.out.println("==========");

        // Before Java 8
        String result = "";
        for (Map.Entry<Integer, String> entry : HOSTING.entrySet()) {
            if ("aws.amazon.com".equals(entry.getValue())) {
                result = entry.getValue();
            }
        }
        System.out.println("Before Java 8 : " + result);

        //Map -> Stream -> Filter -> String
        result = HOSTING.entrySet().stream()
                .filter(map -> "aws.amazon.com".equals(map.getValue()))
                .map(map -> map.getValue())
                .collect(Collectors.joining());

        System.out.println("With Java 8 : " + result);

        // filter more values
        result = HOSTING.entrySet().stream()
                .filter(x -> {
                    if (!x.getValue().contains("amazon") && !x.getValue().contains("digital")) {
                        return true;
                    }
                    return false;
                })
                .map(map -> map.getValue())
                .collect(Collectors.joining(","));

        System.out.println("With Java 8 : " + result);
        System.out.println("++++++++++++++");

        TT tt = new TT();
        //        tt.setName(null);
        tt.setAge(30);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<TT>> constraintViolationSet = validator.validate(tt);

        System.out.println("constraintViolationSet size: " + constraintViolationSet.size());

        for (ConstraintViolation<TT> violation : constraintViolationSet) {
            LOGGER.error(violation.getMessage());
        }

        System.out.println(tt.getName());
        System.out.println("++++++++++++++");

        List<String> fcjList = Arrays.asList("a", "b");

        List<Object> fcjObjectList = Arrays.asList(fcjList);

        System.out.println(fcjList.contains("a"));
        System.out.println(fcjObjectList.contains("a"));

        int testInt01 = 2;
        switch (testInt01) {
        case 0:
            System.out.println("ha1");
        case 1:
            System.out.println("ha2");
            break;
        case 2:
            System.out.println("ha3");
            //            break;
        case 3:
            System.out.println("ha4");
            break;
        default:
            System.out.println("default");
        }

        String test01 = "123";
        String test02 = "fcj ha ge";
        Integer test1 = Integer.parseInt(test01);
        System.out.println(test1);
        try {
            Integer test2 = Integer.parseInt(test02);
            System.out.println(test2);
        } catch (Exception e) {
            String mess = e.getMessage();
            System.out.println("666: " + mess);
            System.out.println("777: " + JSONValue.escape(mess));
            LOGGER.error("haha ex: ", e);

        }

        StringBuilder sb = null;
        if (8 > 6) {
            sb = new StringBuilder("5eee");
        }
        //        assert sb != null : "bad!!!";
        assert sb != null;
        System.out.println(sb.toString());

        //
        Map<Integer, Object> rawFeatureValueMap = new HashMap<>();
        rawFeatureValueMap.put(1, "r1");
        rawFeatureValueMap.put(2, "r2");
        rawFeatureValueMap.put(3, "r3");
        Map<Integer, Object> signoffFeatureValueMap = new HashMap<>();
        signoffFeatureValueMap.put(1, "r1");
        signoffFeatureValueMap.put(2, "rha2");
        signoffFeatureValueMap.put(3, "ruuu3");

        Map<Integer, Object> resultRaw = rawFeatureValueMap.entrySet().stream()
                .filter(e -> !e.getValue().equals(signoffFeatureValueMap.get(e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Map<Integer, Object> resultSignoff = rawFeatureValueMap.entrySet().stream()
                .filter(e -> !e.getValue().equals(signoffFeatureValueMap.get(e.getKey())))
                .collect(Collectors
                        .toMap(Map.Entry::getKey, e -> signoffFeatureValueMap.get(e.getKey())));

        System.out.println(resultRaw);

        System.out.println(tt.getHeight()); // Long 默认为null
        System.out.println(tt.getWidth()); // long 默认为0
    }

}
