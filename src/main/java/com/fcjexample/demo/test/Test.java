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

import com.fcjexample.demo.entity.TestEntity02;
import com.fcjexample.demo.model.TT;
import com.fcjexample.demo.model.TestEntity;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
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

    public static void main(String[] args) throws Exception {

        System.out.println("hhh");

        TestEntity entity1 = new TestEntity();
        entity1.setName("h1");
        TestEntity entity2 = new TestEntity();
        entity2.setName("h2");
        TestEntity entity3 = new TestEntity();
        entity3.setName("h3");

        System.out.println(entity1.getCreateTime());
        System.out.println(entity1.getTestTime());

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

        List<Map<String, String>> mapList = lists
                .stream() // .collect(Collectors.toList(Collectors.toMap(e, e)));
                .map(e -> new HashMap<String, String>() {{
                    put(e, e);
                }}).collect(Collectors.toList());

        Map<String, String> map01 = lists.stream()
                .collect(Collectors.toMap(Function.identity(), e -> e));

        //
        Map<Integer, String> hostingMap = new HashMap<>();
        hostingMap.put(1, "linode.com");
        hostingMap.put(2, "heroku.com");
        hostingMap.put(3, "digitalocean.com");
        hostingMap.put(4, "aws.amazon.com");

        System.out.println("==========");
        hostingMap.entrySet().stream().filter(new Predicate<Map.Entry<Integer, String>>() {
            @Override public boolean test(Map.Entry<Integer, String> integerStringEntry) {
                return integerStringEntry.getKey() > 2;
            }
        }).forEach(mapConsumer);
        System.out.println("==========");

        // Before Java 8
        String result = "";
        for (Map.Entry<Integer, String> entry : hostingMap.entrySet()) {
            if ("aws.amazon.com".equals(entry.getValue())) {
                result = entry.getValue();
            }
        }
        System.out.println("Before Java 8 : " + result);

        //Map -> Stream -> Filter -> String
        result = hostingMap.entrySet().stream()
                .filter(map -> "aws.amazon.com".equals(map.getValue()))
                .map(map -> map.getValue())
                .collect(Collectors.joining());

        System.out.println("With Java 8 : " + result);

        // filter more values
        result = hostingMap.entrySet().stream()
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

        InnerTest innerTest = new InnerTest();
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "test01");
        map.put(2, "test02");
        String test = map.get(null);
        System.out.println(test);

        innerTest.setMap(map);
        Map<Integer, String> originMap = innerTest.getMap();

        Map<Integer, String> copyMap = new HashMap<>(originMap);

        copyMap.put(2, "haha02");

        System.out.println("result: " + originMap.get(1) + "," + originMap.get(2));
        System.out.println("result: " + copyMap.get(1) + "," + copyMap.get(2));

        List<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);
        arrayList.add(5);
        arrayList.add(6);
        System.out.println(arrayList.size());

        Iterator<Integer> integerIterator = arrayList.iterator();
        while (integerIterator.hasNext()) {
            Integer integer = integerIterator.next();
            if (integer % 2 == 0) {
                integerIterator.remove();
                //                arrayList.add(100);  再加是不行的
            }
        }

        System.out.println(arrayList.size());

        //        printTest02();

        TestEntity02 testEntity02 = new TestEntity02();
        //        testEntity02.setTimeout(30L);
        long timeout = testEntity02.getTimeout() == null ? 60 : testEntity02.getTimeout();

        System.out.println(timeout);

        System.out.println("---------===========");
        //        Set<String> stringSet = ConcurrentHashMap.newKeySet();
        Set<String> stringSet = new HashSet<>();
        stringSet.add("yushu");
        stringSet.add(null);

        for (String yushu : stringSet) {
            System.out.println(yushu);
        }

        RuleTestClass ruleTestClass = new RuleTestClass(null, "gg");
        System.out.println("finished");

        Integer fcjInteger = 5;
        Object v = new Integer(4);
        //        double ttv = Double.parseDouble((String) v);// fail
        double c = Double.valueOf(v.toString());
        double d = Double.parseDouble(v.toString());
        System.out.println("c is: " + c);

        Double fcjDouble = fcjInteger.doubleValue();
        System.out.println(fcjDouble);

        Set<String> testSet = new HashSet<>();
        testSet.add("1");
        testSet.add("2");
        testSet.add("3");
        Object sss = testSet;
        double setSize = ((Set) sss).size();
        System.out.println(setSize);

        //        byte[] code = new byte[] {};
        //        File file = new File("/Users/dormifu/test/javaClass/aaa.class");
        //        FileOutputStream fos = new FileOutputStream(file);
        //        fos.write(code);

        Date date = new Date();
        System.out.println(date.getTime());

        List<String> stringList01 = new ArrayList<>();
        stringList01.add("1");
        stringList01.add("2");
        stringList01.add("3");
        stringList01.add("4");
        stringList01.add("5");
        stringList01.add("6");

        List<String> newList = stringList01.subList(2, 4);

        System.out.println(newList.size());

        String s1012 = "123456789";
        System.out.println(s1012.substring(2, 5));

        String testNull = "test";
        Map<String, String> mapTest = new HashMap<>();
        //        mapTest.put(null, null);
        mapTest.put("sg", "tt");
        mapTest.get("sg").equals("tt");
        //        mapTest.get(null)
        //                .equals("eee");

    }

    static class RuleTestClass {
        Object id;
        String name;

        public RuleTestClass(Object id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    private static void printTest() throws IOException {

        String resourcePath = "testfiles/test03.txt";
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream is = loader.getResourceAsStream(resourcePath);

        //        FileInputStream fileInputStream = null;
        Reader reader = null;
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            //            fileInputStream = new FileInputStream(fileName);
            //            reader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));
            reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            reader.close();
            writer.close();
            is.close();
        }
        String str = writer.toString();
        long end = System.currentTimeMillis();
    }

    private static void printTest02() throws IOException, ParseException {
        List<String> list = new ArrayList<>();
        String resourcePath = "testfiles/test03.txt";
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream is = loader.getResourceAsStream(resourcePath);

        //        FileInputStream fileInputStream = null;
        BufferedReader reader = null;
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            //            fileInputStream = new FileInputStream(fileName);
            //            reader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));
            reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String line = reader.readLine();
            while (line != null) {
                list.add(line);
                // read next line
                line = reader.readLine();
            }
        } finally {
            reader.close();
            writer.close();
            is.close();
        }

        TreeMap<String, String> treeMap = new TreeMap<>();

        for (String str : list) {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(str);
            treeMap.put((String) json.get("event_time"),
                    (String) json.get("testVelocityFeatureWithNegativeWindowOffset_feature"));
        }

        String wenzheng = "all";
        System.out.println("hhh is: " + wenzheng + "end!!!");

        System.out.println(list.size());

        String test01 = "ddd";
        String[] arrays = test01.split("_");
        System.out.println(arrays.length);
        System.out.println(String.join(",", arrays));
        System.out.println("end");
    }

    static class InnerTest {
        Map<Integer, String> map;

        public Map<Integer, String> getMap() {
            return map;
        }

        public void setMap(Map<Integer, String> map) {
            this.map = map;
        }
    }

}
