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

package com.fcjexample.demo.test.lambda.optional;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;

import java.util.*;

public class User01 {

    private String name;

    private Address01 address01;

    public User01(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address01 getAddress01() {
        return address01;
    }

    public void setAddress01(Address01 address01) {
        this.address01 = address01;
    }

    public static void main(String[] args) throws ParseException {

        String data01 = "{\"income\":\"38661\",\"user_id\":\"1rvIDKvp2j7FW727Cp13\",\"ip\":\"37.243.233.230\",\"testVelocityFeatureWithRule_count_income_user_id\":\"0\",\"time\":\"1579740512942\",\"event_time\":\"1579740512942\"}";
        String data02 = "{\"income\":\"38662\",\"user_id\":\"user01\",\"ip\":\"37.243.233.230\",\"testVelocityFeatureWithRule_count_income_user_id\":\"5\",\"time\":\"1579740512948\",\"event_time\":\"1579740512948\"}";
        String data03 = "{\"income\":\"38662\",\"user_id\":\"user02\",\"ip\":\"37.243.233.230\",\"testVelocityFeatureWithRule_count_income_user_id\":\"2\",\"time\":\"1579740512948\",\"event_time\":\"1579740512948\"}";
        String data04 = "{\"income\":\"38662\",\"user_id\":\"user01\",\"ip\":\"37.243.233.230\",\"testVelocityFeatureWithRule_count_income_user_id\":\"1\",\"time\":\"1579740512948\",\"event_time\":\"1579740512948\"}";
        String data05 = "{\"income\":\"38662\",\"user_id\":\"user01\",\"ip\":\"37.243.233.230\",\"testVelocityFeatureWithRule_count_income_user_id\":\"3\",\"time\":\"1579740512948\",\"event_time\":\"1579740512948\"}";

        List<String> list = new ArrayList<>();
        list.add(data01);
        list.add(data02);
        list.add(data03);
        list.add(data04);
        list.add(data05);

        String featureName = "testVelocityFeatureWithRule_count_income_user_id";
        Map<String, List<String>> userNameToFeatureValueMap = new HashMap<>();
        for (String str : list) {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(str);
            putUserIdAndFeatureValue(json, "user01", featureName, userNameToFeatureValueMap);
            putUserIdAndFeatureValue(json, null, featureName, userNameToFeatureValueMap);

            putUserIdAndFeatureValue(json, "user02", featureName, userNameToFeatureValueMap);
            putUserIdAndFeatureValue(json, "user03", featureName, userNameToFeatureValueMap);
        }

        Collections.sort(userNameToFeatureValueMap.get("user01"));
        Collections.sort(userNameToFeatureValueMap.get("user02"));

        Assert.assertEquals("1,3,5", String.join(",", userNameToFeatureValueMap.get("user01")));

        for (String str : list) {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(str);
            System.out.println(json.get("user_id"));
        }

        System.out.println("=============");
        Map<String, TreeSet<String>> stringTreeSetMap = new HashMap<>();
        Set<String> stringSet = new TreeSet<>();
        stringSet.add("2");
        stringSet.add("1");
        stringSet.add("4");
        stringSet.add("1");
        for (String s : stringSet) {
            System.out.println(s);
        }

        System.out.println("=========");

        User01 user01 = new User01("bob");
        //        User01 user01 = null;

        String way2 = Optional.ofNullable(user01)
                //        String way2 = Optional.of(user01) // NPE
                .map(u -> u.getAddress01())
                .map(a -> a.getCountry01())
                .map(c -> c.getWay())
                .orElse("default");

        System.out.println(way2);

    }

    private static void putUserIdAndFeatureValue(JSONObject json, String userId, String featureName,
            Map<String, List<String>> userNameToFeatureValueMap) {
//        if (userId.equals(json.get("user_id"))) {
            userNameToFeatureValueMap.computeIfAbsent(userId, k -> new ArrayList<>());
            userNameToFeatureValueMap.get(userId).add((String) json.get(featureName));
//        }
    }
}
