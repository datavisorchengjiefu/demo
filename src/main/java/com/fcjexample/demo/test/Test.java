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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fcjexample.demo.entity.TestEntity02;
import com.fcjexample.demo.model.TT;
import com.fcjexample.demo.model.TestEntity;
import com.fcjexample.demo.util.Tuple;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import scala.Tuple2;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);
    static boolean bo;

    private static Consumer<Map.Entry<Integer, String>> mapConsumer = new Consumer<Map.Entry<Integer, String>>() {
        @Override
        public void accept(
                //                Map.Entry<Integer, Object> value) {
                Map.Entry<Integer, String> value) {

            System.out.println(value.getKey() + ": " + value.getValue());

        }
    };

    private static String test01 = "haha";
    public static final String FOR_TEST_SIGN = "// Script for test\n";

    static {
        LOGGER.info("haha01: {}", test01);
        test01 = "haha09";
        LOGGER.info("haha01: {}", test01);
    }

    public static Integer _int(Object val) {
        if (val == null) {
            return null;
        }

        if (val instanceof Integer) {
            return (Integer) val;
        }
        if (val instanceof Number) {
            return ((Number) val).intValue();
        }
        if (val instanceof String) {
            try {
                return Integer.valueOf((String) val);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(e);
            }
        }
        throw new IllegalArgumentException();
    }

    private static double testHaohui() {
        Integer integer = new Integer(0);
        Integer integer02 = new Integer(100000);
        short short01 = integer.shortValue();
        short short02 = integer02.shortValue();

        String str = "" + integer;
        String uml_response01 = null;
        String umlResponse = (String) uml_response01;
        if (umlResponse == null) {
            return 0.0;
        }
        JSONObject jsonObj;
        try {
            jsonObj = (JSONObject) JSONValue.parse(umlResponse);
        } catch (Exception e) {
            LOGGER.error("Exception when converting json {}", umlResponse);
            return 0.0;
        }

        Object scoreObj = jsonObj.get("score");
        Double scoreInDouble = null;
        if (scoreObj != null) {
            if (scoreObj instanceof Number) {
                scoreInDouble = ((Number) scoreObj).doubleValue();
            } else if (scoreObj instanceof String) {
                try {
                    scoreInDouble = Double.parseDouble((String) scoreObj);
                } catch (NumberFormatException e) {
                    String msg = String.format("Failed to get score from uml response in JSON: %s",
                            scoreObj.toString());
                    LOGGER.error(msg);
                }
            }
        }
        if (scoreInDouble == null) {
            scoreInDouble = 0.0;
            String msg = String.format("Failed to get score from uml response in JSON: %s",
                    String.valueOf(scoreObj));
            LOGGER.error(msg);
        }
        return scoreInDouble;
    }

    private static void testSub(TestEntity testEntity) {
        testEntity.setName("01");
        testEntity.setDesc("02");
    }

    // 可变参数作为参数的方法
    public static void element(int... args) {
        for (int arg : args) {
            System.out.println(arg);
        }
    }

    public static void element(String s, int... args) {
        for (int arg : args) {
            System.out.println(arg);
        }
    }

    public static void element(Object... args) {
        System.out.println(args.length);
    }

    public static void swap(String a, String b) {
        String temp = a;
        a = b;
        b = temp;
    }

    public static void main(String[] args) throws Exception {
        testyu("666");

        //        //都可以
        //        element(1, 2, 3);
        //        int[] array = { 1, 2, 3 };
        //        element(array);
        //
        //        //Integer[]可以转型为Object[],可以作为一个对象数组
        //        element(new Integer[] { 1, 2, 3 });
        //        //多个参数的话，Integer[]会被当做一个单纯的数组对象
        //        element(1, new Integer[] { 2, 3 });
        //        element(1, 2, 3);
        //        //int[] 无法转型为Object[]，因此会被当做一个单纯的数组对象
        //        element(new int[] { 1, 2, 3 });

        Calendar calendar = Calendar.getInstance();

        String aV2 = "testa";
        String b = "testb";

        swap(aV2, b);

        String test20230413 = "s3a://datavisor-unittest/data/replayCli/ferry_test/folder01/";
        String[] test20230413s = test20230413.split("/");
        System.out.println(test20230413s[test20230413s.length - 1]);

        String[] strings2022 = new String[] { "java", "c", "python" };
        List<String> stringList = new ArrayList<>(Arrays.asList(strings2022));
        stringList.add(stringList.size() - 2, "test01");
        stringList.add(stringList.size() - 2, "test02");
        stringList.add(stringList.size() - 1, "test03");
        stringList.add("");
        stringList.add(stringList.size(), "test04");

        Date date0704 = new Date(System.currentTimeMillis());
        Date date0704V1 = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String lastEventTimeStrha = dateFormat.format(date0704V1);

        TestEntity testEntity062601 = new TestEntity("name01", "desc01");
        TestEntity testEntity062602 = new TestEntity("name02", "desc02");

        testSub(testEntity062601);
        StringBuilder stringBuilder01 = new StringBuilder();
        stringBuilder01.append("test01");
        stringBuilder01.append("test02");
        stringBuilder01.append("test03");

        stringBuilder01.deleteCharAt(stringBuilder01.length() - 1);
        stringBuilder01.deleteCharAt(stringBuilder01.length() - 2);

        Map<String, String> map20220421 = new HashMap<>();
        String valueMap20220421v1 = map20220421.putIfAbsent("1", "testv1");
        String valueMap20220421 = map20220421.computeIfAbsent("1", new Function<String, String>() {
            @Override public String apply(String s) {
                return "testv2";
            }
        });
        String valueMap20220422 = map20220421.computeIfAbsent("2", new Function<String, String>() {
            @Override public String apply(String s) {
                return "testv2";
            }
        });

        Map<String, String> conMap01 = new ConcurrentHashMap<>();
        testHaohui();
        //        testBlibli();

        String splitS = "3,,5";
        String splitS02 = "3,,5,";
        String splitS03 = "";
        String[] splitSs = splitS.split(",", -1);
        String[] splitS02s = splitS02.split(",");
        String[] splitS02s02 = splitS02.split(",", -1);
        String[] splitS02s03 = splitS03.split(",");

        String test1202 = splitS + "null";
        String test120201 = splitS + null;

        String jo = "{\"RULE\":[{\"createTime\":\"2019-11-01 21:34:36\",\"name\":\"import_rule_1636039465869\",\"updateTime\":\"2019-11-01 21:34:36\",\"type\":\"MANUAL\",\"script\":\"if (1500 > 150) { result.addAction(\\\"ACCUMULATE\\\");}\"}]}";

        String exportResult = "{\"RULE\":[{\"createTime\":\"2019-11-01 21:34:36\",\"cpName\":\"\",\"name\":\"import_rule_1636039465869\",\"updateTime\":\"2019-11-01 21:34:36\",\"type\":\"MANUAL\",\"category\":\"RULE\",\"script\":\"if (1500 > 150) { result.addAction(\\\"ACCUMULATE\\\");}\"}]}";

        JSONAssert.assertEquals(jo, exportResult,
                JSONCompareMode.LENIENT);
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> errorRes = new LinkedHashMap();

        String url = "https://www.baidu.com/hello";
        String restTemplateResult = "";
        try {
            restTemplateResult = restTemplate.getForObject(url, String.class);

        } catch (Exception e) {
            if (e.getMessage().contains("404 Not Found")) {
                // For dEdge, 404 is a "normal" error.
            }
            errorRes.put("url", url.toString());
            errorRes.put("error_message", e.getMessage());
            //            throw new RuntimeException(errorRes.toString());
        }
        System.out.println("restTemplate: " + restTemplateResult);

        String toJavaLangStrReprV1 = toJavaLangStrRepr("test\"Temp", "string");
        String toJavaLangStrReprV2 = toJavaLangStrRepr("哈哈\"哦", "string");
        String toJavaLangStrReprV102 =
                "\"" + StringEscapeUtils.unescapeJava(toJavaLangStrReprV1) + "\"";
        String toJavaLangStrReprV202 =
                "\"" + StringEscapeUtils.unescapeJava(toJavaLangStrReprV2) + "\"";

        ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(toJavaLangStrReprV2);

        String germanString = "来来来";
        byte[] germanBytes = germanString.getBytes();

        String asciiEncodedString = new String(germanBytes, StandardCharsets.UTF_8);

        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextInt(3));
        }
        System.out.println("========");
        int int2021092701 = 4;
        Integer int2021092702 = null;
        String string0929 = null;
        String s0929 = String.valueOf(string0929);
        String s20210927 = String.valueOf(int2021092701);
        String s2021092702 = String.valueOf(int2021092701);
        String ddd = String.valueOf(int2021092702);
        Object testhaha = "test";

        //        Integer aggLevel01 = 24 * 60 * 60 * 180 * 1000;
        Integer aggLevel01 = 24 * 60 * 60 * 180;
        boolean flag01 = 2 < aggLevel01;
        boolean flag02 = 2 < aggLevel01 * 1000L;
        TestEntity testEntity = new TestEntity();
        testEntity.setDesc("hhh");
        ObjectMapper objectMapper = new ObjectMapper();
        String str = objectMapper.writeValueAsString(null);
        String str01 = objectMapper.writeValueAsString(new Integer(3));
        String str02 = objectMapper.writeValueAsString("testha");
        String str021 = objectMapper.writeValueAsString("testha" + 8);
        String str022 = objectMapper.writeValueAsString(s20210927);
        String str023 = objectMapper.writeValueAsString(flag01);
        String str024 = objectMapper.writeValueAsString(testhaha);
        String str03 = objectMapper.writeValueAsString(testEntity);
        String str04 = objectMapper.writeValueAsString(4);
        String str05 = objectMapper.writeValueAsString(map20220421);
        String ttt = "if ($ConnectedTime == null || $Duration == null){\n"
                + "    return \"\";\n"
                + "}\n"
                + "\n"
                + "long second = TimeLib.toTimestamp($ConnectedTime, \"yyyy-MM-dd HH:mm:ss.SSS\");\n"
                + "long num = Long.parseLong($Duration) * 1000;\n"
                + "long res = num + second;\n"
                + "\n"
                + "String result = TimeLib.parseTimestampToStrFormat(res, \"yyyy-MM-dd HH:mm:ss.SSS\");\n"
                + "return result;";
        float f20210906 = 0.12345f;
        String s20210906 = String.format("%.3f", f20210906);
        System.out.println(s20210906);
        double d20210906 = 0.12345;

        ByteBuffer[] buffers = new ByteBuffer[2];
        buffers[0] = ByteBuffer.allocate(4).putInt(0, 4);
        //        build(false, buffers);

        Queue<Integer> queue = new LinkedList<>();
        Integer q1 = queue.peek();
        q1 = queue.poll();

        List<Long> aggLevelList = new ArrayList<>(Arrays.asList(0L));
        aggLevelList.clear();

        List<Integer> list0817 = new LinkedList<>();
        list0817.add(2);
        System.out.println("haha20211110: " + list0817.get(0));
        //        System.out.println("haha20211110: " + list0817.get(1));
        list0817.add(5);
        list0817.add(1);
        for (Integer i : list0817) {
            System.out.println("ha: " + i);
        }

        list0817.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 < o2 ? 1 : -1;
            }
        });

        //        double d01 = Math.pow(2, 31);
        double d01 = Math.pow(2, 33);
        //        double d01 = 80;

        //        int i01 = (int) d01;
        int i01 = _int(d01);

        LOGGER.info("hhh: {}", i01);
        LOGGER.info("hhhv: {}", d01 - i01);

        Object yyy = new Integer(9);
        String yyy7 = String.valueOf(yyy);

        String addScript = FOR_TEST_SIGN
                + "if (accumulator == null) return 1L; return 1L + (Long) accumulator; \n";

        System.out.println(addScript);

        List<Integer> orderList = new ArrayList<>();
        orderList.add(6);
        orderList.add(1);
        orderList.add(4);

        for (Integer o : orderList) {
            System.out.println(o);
        }

        orderList.add(2, 3);

        System.out.println("============");
        for (Integer o : orderList) {
            System.out.println(o);
        }
        String gggg = "{\"actions\":{\"REVIEW QUEUE\":\"demo\"},\"condition\":{\"and\":[{\"and\":[{\"operands\":[\"gender_load_customized_data\",\"man\"],\"operator\":\"STR_EQ\"},{\"operands\":[\"order_amount_load_customized_data\",\"1000\"],\"operator\":\"DOUBLE_GT\"}]}]}}";
        System.out.println("============");

        long now01 = System.currentTimeMillis();
        long haha01 = now01 - now01 % 60000;
        long long1 = 1627566642000L;
        long interval = 86400000L;

        Date date01 = new Date(1627516800000L);
        System.out.println(date01.getTime());

        double tmp = long1 % interval;
        double left = long1 - tmp;
        System.out.println(left);
        System.out.println(left + interval);
        System.out.println(long1 - left > (interval / 2));
        System.out.println(left > (interval / 2));
        Map<Tuple<Long, Long>, String> tupleStringMap = new LinkedHashMap<>();
        Tuple<Long, Long> key = new Tuple<>(103L, 5L);
        Tuple<Long, Long> key1 = new Tuple<>(100L, 8L);
        Tuple<Long, Long> key11 = new Tuple<>(100L, 9L);
        Tuple<Long, Long> key12 = new Tuple<>(100L, 8L);
        Tuple<Long, Long> key13 = new Tuple<>(100L, 20L);
        Tuple<Long, Long> key2 = new Tuple<>(101L, 6L);
        Tuple<Long, Long> key21 = new Tuple<>(101L, 9L);
        Tuple<Long, Long> key22 = new Tuple<>(101L, 5L);
        Tuple<Long, Long> key3 = new Tuple<>(102L, 7L);
        Tuple<Long, Long> key23 = new Tuple<>(101L, 1L);
        Long test001 = 3L;
        Long test002 = test001 + 4L;

        //        Tuple<Long, Long> key = new Tuple<>(103L, 5L);
        //        Tuple<Long, Long> key1 = new Tuple<>(100L, 8L);
        //        Tuple<Long, Long> key2 = new Tuple<>(101L, 6L);
        //        Tuple<Long, Long> key3 = new Tuple<>(102L, 7L);
        tupleStringMap.put(key, "test");
        tupleStringMap.put(key11, "test02");// these 2 reverse position. a.
        tupleStringMap.put(key1, "test02");// these 2 reverse position. a.
        tupleStringMap.put(key12, "test02");
        tupleStringMap.put(key13, "test02");
        tupleStringMap.put(key2, "test");
        tupleStringMap.put(key21, "test");
        tupleStringMap.put(key22, "test");
        tupleStringMap.put(key23, "test");// these 2 reverse position. b.
        tupleStringMap.put(key3, "test");// these 2 reverse position. b.

        List<Tuple<Long, Long>> keys = new LinkedList<>(tupleStringMap.keySet());
        for (Tuple<Long, Long> t : keys) {
            System.out.println(t._1() + ":" + t._2());
        }

        // ha04

        System.out.println("=======");
        //        Collections.reverse(keys);
        keys.sort((o1, o2) -> {
            if (o2._1().equals(o1._1())) {
                return (int) (o2._2() - o1._2());
            }
            return (int) (o2._1() - o1._1());
        });

        for (Tuple<Long, Long> t : keys) {
            System.out.println(t._1() + ":" + t._2());
        }
        System.out.println("=======");
        //        Collections.reverse(keys);

        BigDecimal num1 = new BigDecimal("10");
        BigDecimal num2 = new BigDecimal("3");

        BigDecimal resultBig = num1.divide(num2, 3, RoundingMode.HALF_UP);

        System.out.println(resultBig);
        //        Integer integer01 = new Integer(10);
        Integer integer01 = null;
        Integer result001 = Optional.ofNullable(integer01).orElse(20);
        System.out.println(result001);
        Collection<Integer> integerCollection = new HashSet<>();
        integerCollection.add(5);
        integerCollection.add(3);
        integerCollection.add(6);
        LOGGER.info("hh is {}. ", integerCollection);

        AtomicLong atomicLong = new AtomicLong(System.currentTimeMillis());
        atomicLong.getAndIncrement();

        String externalEventLine = "[{\"id\": \"eventId003\",\"name\": \"eventName003\",\"time\": 10008,\"fields\": {\"1\": \"10008\",\"2\": \"userId11\",\"3\": \"201\"}}]";
        String externalEventLine01 = "        {\n"
                + "            \"id\": \"eventId003\",\n"
                + "            \"name\": \"eventName003\",\n"
                + "            \"time\": 10007,\n"
                + "            \"fields\": {\n"
                + "                \"1\": \"10007\",\n"
                + "                \"2\": \"userId11\",\n"
                + "                \"3\": \"200\"\n"
                + "            }\n"
                + "        }";
        JSONArray externalEvent = (JSONArray) JSONValue.parseWithException(externalEventLine);
        String testString = String.class.getName();

        Set<String> setTest01 = new HashSet<>();
        setTest01.add("w1");
        setTest01.add("w2");
        setTest01.add("w3");

        String setString01 = "test|" + setTest01;
        System.out.println(setString01);
        String[] strings = setString01.split("\\|");
        String result01 = strings[1];

        List<Integer> list001 = new ArrayList<>();
        list001.add(2);
        list001.add(3);
        list001.add(4);

        List<Integer> list002 = new ArrayList<>(list001);
        list002.add(5);
        list002.add(6);
        System.out.println(list001.size());
        System.out.println(list002.size());
        list002.addAll(list001);
        List<TestEntity02> list = new ArrayList<>();
        list.add(null);
        //        list.add(new TestEntity02(10L, "tttname", "ttt"));
        //        list.add(null);
        //        list.stream().map(s -> s.getName()).collect(Collectors.toList());
        System.out.println("is empty? " + list.isEmpty());

        String wenzheng = "null";
        String wenzheng1 = null;
        String w = String.valueOf(wenzheng);
        String w1 = String.valueOf(wenzheng1);
        w.equals("null");

        System.out.println("hhh");

        String testEmpty = null;
        System.out.println(StringUtils.isEmpty(testEmpty));

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
        set.remove(entity1);
        set.remove(entity2);
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
        String jsonString = json.toJSONString();
        String string01 = json.toString();
        int jsonLength = json.toJSONString().length();
        int endIndexException = Math.min(240 - jsonLength, s.length());

        List<String> lists = new ArrayList<>();
        lists.add("zhang");
        lists.add("wang");
        lists.add("cheng");
        lists.add("zhou");
        lists.stream()
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) {
                        return s.length() > 4;
                    }
                })
                .forEach(System.out::println); // zhang cheng

        List<Map<String, String>> mapList = lists
                .stream() // .collect(Collectors.toList(Collectors.toMap(e, e)));
                .map(e -> new HashMap<String, String>() {{
                    put(e, e);
                }}).collect(Collectors.toList());

        Map<String, Object> resultMap = new HashMap<>();
        Map<String, String> resultMapInternal = new HashMap<>();
        String hh = resultMapInternal.put("ha", "uuu");
        resultMapInternal.put("ha01", "uuu02");
        resultMap.put("1", "ggg");
        resultMap.put("4", resultMapInternal);

        String 个人 = ((Map<String, String>) resultMap.get(String.valueOf(4))).get("ha01");
        个人 = "gg";

        Assert.assertTrue(((Map<String, String>) resultMap.get(String.valueOf(4)))
                .containsKey("ha"));

        Map<String, String> map01 = lists.stream()
                .collect(Collectors.toMap(Function.identity(), e -> e));

        List<String> listsFCJ = new ArrayList<>();
        Map<String, String> map02fcj = listsFCJ.stream()
                .collect(Collectors.toMap(Function.identity(), e -> e));
        System.out.println("hahalist");

        //
        Map<Integer, String> hostingMap = new HashMap<>();
        hostingMap.put(1, "linode.com");
        hostingMap.put(2, "heroku.com");
        hostingMap.put(3, "digitalocean.com");
        hostingMap.put(4, "aws.amazon.com");

        System.out.println("==========");
        hostingMap.entrySet().stream().filter(new Predicate<Map.Entry<Integer, String>>() {
            @Override
            public boolean test(Map.Entry<Integer, String> integerStringEntry) {
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

        System.out.println("resultRaw: " + resultRaw);
        System.out.println("resultSignoff: " + resultSignoff);

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
        ListIterator<Integer> listIterator = arrayList.listIterator();
        while (listIterator.hasNext()) {
            Integer integer = listIterator.next();
            if (integer % 2 == 0) {
                //                integerIterator.remove();
                listIterator.add(100 * integer);
                //                arrayList.add(100);  // integerIterator再加是不行的
            }
        }

        System.out.println(arrayList.size());

        //        printTest02();

        TestEntity02 testEntity02 = new TestEntity02();
        testEntity02.setName("before");
        modify(testEntity02);
        TestEntity02 final02 = testEntity02;
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

        //        Date date = new Date();
        //        System.out.println(date.getTime());

        List<String> stringList01 = new ArrayList<>();
        stringList01.add("1");
        stringList01.add("2");
        stringList01.add("3");
        stringList01.add("4");
        stringList01.add("5");
        stringList01.add("6");

        List<String> newList = stringList01.subList(2, 4);
        newList = stringList01.subList(0, 0);

        System.out.println(newList.size());// 01

        String s1012 = "123456789";
        System.out.println(s1012.substring(2, 5));

        String testNull = "test";// 02
        Map<String, String> mapTest = new HashMap<>();// 03
        //        mapTest.put(null, null);
        mapTest.put("sg", "tt");// 04
        mapTest.get("sg").equals("tt");// 05
        //        mapTest.get(null)
        //                .equals("eee");
        Map<String, String> map2214 = new HashMap<>();
        //        map2214.put("ggg", "uuu");
        System.out.println(map2214.keySet());

        Object id1125 = 81;
        Long test1125 = Long.valueOf(String.valueOf(id1125));
        //        Long test1125 = (Long) id1125;
        System.out.println(test1125);

        List<Integer> list1126 = new ArrayList<>();
        list1126.add(3);
        list1126.add(7);
        list1126.add(4);
        LOGGER.info("list is ha: {}", list1126);

        String rule = "{\n"
                + "  \"condition\": {\n"
                + "    \"operands\": [\n"
                + "      \"age\",\n"
                + "      \"10\"\n"
                + "    ],\n"
                + "    \"operator\": \"INT_LT\"\n"
                + "  },\n"
                + "  \"actions\": {\n"
                + "    \"$df1\": {\n"
                + "      \"value\": \"\\\"1\\\"\",\n"
                + "      \"type\": \"String\",\n"
                + "      \"priority\": \"2\"\n"
                + "    },\n"
                + "    \"$df2\": {\n"
                + "      \"value\": \"\\\"1\\\"\",\n"
                + "      \"type\": \"String\"\n"
                + "    },\n"
                + "    \"AGE\": \"young\"\n"
                + "  }\n"
                + "}";
        System.out.println(rule);
        System.out.println("yibin");
        System.out.println("**********");
        System.out.println(System.currentTimeMillis());

        System.out.println("**********");

        long ut1 = Instant.now().getEpochSecond();
        System.out.println(ut1);

        long ut2 = System.currentTimeMillis() / 1000L;
        System.out.println(ut2);

        Date now = new Date();
        long ut3 = now.getTime() / 1000L;
        System.out.println(ut3);

        System.out.println("**********8");
        long unixSeconds = System.currentTimeMillis();
        // convert seconds to milliseconds
        Date date = new java.util.Date(unixSeconds);
        System.out.println(date);
        // the format of your date
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        SimpleDateFormat sdf1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        // give a timezone reference for formatting (see comment at the bottom)
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
        sdf1.setTimeZone(java.util.TimeZone.getTimeZone("GMT+8"));
        String formattedDate = sdf.format(date);
        String formattedDate1 = sdf1.format(date);
        System.out.println(formattedDate);
        System.out.println(formattedDate1);

        Map<Integer, Map<String, String>> ruleResultMap = new HashMap<>();
        Map<String, String> ruleResultMapInternal = new HashMap<>();
        ruleResultMapInternal.put("1", "1");
        ruleResultMapInternal.put("2", "2");
        ruleResultMapInternal.put("3", "3");
        Map<String, String> ruleResultMapInternal2 = new HashMap<>();
        ruleResultMapInternal2.put("21", "21");
        ruleResultMap.put(1, ruleResultMapInternal);
        ruleResultMap.put(2, ruleResultMapInternal2);

        Map<Integer, Map<String, String>> ruleResultMapShallow = new HashMap<>();
        ruleResultMapShallow.putAll(ruleResultMap);

        System.out.println(ruleResultMap);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Executors.newScheduledThreadPool(3);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ruleResultMap.get(1).put("4", "4");

            }
        });

        System.out.println(ruleResultMap);
        System.out.println(ruleResultMapShallow);
        executorService.shutdown();

        System.out.println("==============");
        Boolean testyushu = new Boolean("TRUE");
        testyushu.booleanValue();

        //        Map<String, String> stringMap = ruleResultMap.get(1);
        //        for (Map.Entry<String, String> entry : stringMap.entrySet()) {
        //            System.out.println(entry.getKey());
        //            System.out.println(entry.getValue());
        //            Thread.sleep(3000);
        //        }

        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>(
                ruleResultMap.get(1));

        Iterator<Map.Entry<String, String>> iterator1 = concurrentHashMap.entrySet().iterator();
        while (iterator1.hasNext()) {
            Map.Entry<String, String> entry = iterator1.next();

            //            if (entry.getKey().equals("2")) {
            //                iterator1.remove();
            //            } else {
            //
            //            }

            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
            Thread.sleep(3000);
        }

    }

    static void testyu(String test) {
        System.out.println("=======hhh");
        System.out.println(test + "|" + bo);
        System.out.println("=======hhh");
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

    private List test() {

        return Arrays.asList(1, 2);
    }

    private static void modify(TestEntity02 testEntity02) {
        testEntity02.setName("after");
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

    public static ByteBuffer build(boolean isStatic, ByteBuffer... buffers) {
        int totalLength = isStatic ? 2 : 0;
        for (ByteBuffer bb : buffers)
            totalLength += 2 + bb.remaining() + 1;

        ByteBuffer out = ByteBuffer.allocate(totalLength);

        if (isStatic)
            out.putShort((short) 4);

        for (ByteBuffer bb : buffers) {
            out.put(bb.duplicate());
            out.put((byte) 0);
        }
        out.flip();
        return out;
    }

    public static String toJavaLangStrRepr(Object object, String simpleTypeName) {
        switch (simpleTypeName.toLowerCase()) {

        case "byte":
        case "char":
        case "character":
            return "'" + object.toString() + "'";
        case "string":
            String escape = org.apache.commons.text.StringEscapeUtils
                    .escapeJava(object.toString());
            return "\"" + escape + "\"";
        case "boolean":
            return object.toString();
        default:
            throw new org.apache.commons.lang3.NotImplementedException(
                    "Given constant type is not supported: " + simpleTypeName);
        }
    }

    public static void testBlibli() {
        String externalEventLine = "[{\"id\": \"eventId003\",\"name\": \"eventName003\",\"time\": 10008,\"fields\": {\"1\": \"10008\",\"2\": \"userId11\",\"3\": \"201\"}}]";
        String externalEventLine01 = "        {\n"
                + "            \"id\": \"eventId003\",\n"
                + "            \"name\": \"eventName003\",\n"
                + "            \"time\": 10007,\n"
                + "            \"fields\": {\n"
                + "                \"1\": \"10007\",\n"
                + "                \"2\": \"userId11\",\n"
                + "                \"3\": \"200\"\n"
                + "            }\n"
                + "        }";

        if (externalEventLine == null) {
        }
        Object obj = JSONValue.parse(externalEventLine);
        JSONArray jsonArray = (JSONArray) obj;
        Set<String> brandNameset = new HashSet<String>();

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jo = (JSONObject) jsonArray.get(i);
            brandNameset.add(jo.get("productTypeCode").toString());
        }
        brandNameset.stream().sorted(Comparator.reverseOrder());
        String str = String.join("@", brandNameset);
    }

    public List<Map<String, Tuple2<String, String>>> eval(Object inputObject) throws Exception {
        Map<String, String> typeMap = new HashMap<>();
        typeMap.put("userId", "String");
        typeMap.put("time", "Long");
        typeMap.put("device", "String");
        typeMap.put("city", "String");
        typeMap.put("amount", "Long");

        Map<String, Tuple2<String, String>> fieldToValueMap = new HashMap<>();
        String content = (String) inputObject;
        Map<String, Object> contentJson = (Map<String, Object>) (new JSONParser()).parse(content);
        for (String key : contentJson.keySet()) {
            if (typeMap.get(key).equalsIgnoreCase("String")) {
                fieldToValueMap
                        .put(key, new Tuple2<>("String", contentJson.get(key).toString()));
            } else if (typeMap.get(key).equalsIgnoreCase("Long")) {
                fieldToValueMap.put(key, new Tuple2<>("Long", contentJson.get(key).toString()));
            }
        }
        return Arrays.asList(fieldToValueMap);
    }

}
