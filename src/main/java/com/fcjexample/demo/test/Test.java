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

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Test {
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

        TT tt = new TT();
        tt.setName(null);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<TT>> constraintViolationSet = validator.validate(tt);
        System.out.println(constraintViolationSet.size());

        System.out.println(tt.getName());

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

    }

}
