package com.fcjexample.demo.test.protobuf;

import org.junit.After;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Tuple2;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*************************************************************************
 *
 * Copyright (c) 2016, DATAVISOR, INC.
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

public class ProtobufUnitTest {
    private final String filePath = "address_book";
    private static final Logger logger = LoggerFactory.getLogger(ProtobufUnitTest.class);

    @After
    public void cleanup() throws IOException {
        Files.deleteIfExists(Paths.get(filePath));
    }

    @Test
    public void givenGeneratedProtobufClass_whenCreateClass_thenShouldCreateJavaInstance() {
        //when
        String email = "j@baeldung.com";
        int id = new Random().nextInt();
        String name = "Michael Program";
        String number = "01234567890";
        AddressBookProtos.Person person =
                AddressBookProtos.Person.newBuilder()
                        .setId(id)
                        .setName(name)
                        .setEmail(email)
                        .addNumbers(number)
                        .build();
        //then
        assertEquals(person.getEmail(), email);
        assertEquals(person.getId(), id);
        assertEquals(person.getName(), name);
        assertEquals(person.getNumbers(0), number);
    }

    @Test
    public void givenAddressBookWithOnePerson_whenSaveAsAFile_shouldLoadFromFileToJavaClass()
            throws IOException {
        //given
        String email = "j@baeldung.com";
        int id = new Random().nextInt();
        String name = "Michael Program";
        String number = "01234567890";
        AddressBookProtos.Person person =
                AddressBookProtos.Person.newBuilder()
                        .setId(id)
                        .setName(name)
                        .setEmail(email)
                        .addNumbers(number)
                        .build();

        // byte
        AddressBookProtos.AddressBook addressBook01 = AddressBookProtos.AddressBook.newBuilder()
                .setHa("888")
                //                .addPeople(person).build();
                .build();
        byte[] bytes = addressBook01.toByteArray();
        AddressBookProtos.AddressBook deserialized01 = AddressBookProtos.AddressBook.newBuilder()
                .mergeFrom(bytes).build();

        assertEquals("888", deserialized01.getHa());
        List<AddressBookProtos.Person> personList = deserialized01.getPeopleList();
        for (AddressBookProtos.Person person1 : personList) {
            assertEquals(person1.getEmail(), email);
            assertEquals(person1.getId(), id);
            assertEquals(person1.getName(), name);
            assertEquals(person1.getNumbers(0), number);
        }

        // file
        //when
        AddressBookProtos.AddressBook addressBook = AddressBookProtos.AddressBook.newBuilder()
                .addPeople(person).build();
        FileOutputStream fos = new FileOutputStream(filePath);
        addressBook.writeTo(fos);
        fos.close();

        //then
        FileInputStream fis = new FileInputStream(filePath);
        AddressBookProtos.AddressBook deserialized =
                AddressBookProtos.AddressBook.newBuilder().mergeFrom(fis).build();
        fis.close();
        assertEquals(deserialized.getPeople(0).getId(), id);
        assertEquals(deserialized.getPeople(0).getName(), name);
        assertEquals(deserialized.getPeople(0).getNumbers(0), number);

        InputStreamReader isr = null;
        BufferedReader br = null; //用于包装InputStreamReader,提高处理性能。因为BufferedReader有缓冲的，而InputStreamReader没有。
        String str = "";
        String str1 = "";
        StringBuilder sb = new StringBuilder();
        //        fis = new FileInputStream(filePath);// FileInputStream
        fis = new FileInputStream(filePath);// FileInputStream
        // 从文件系统中的某个文件中获取字节
        isr = new InputStreamReader(fis);// InputStreamReader 是字节流通向字符流的桥梁,
        br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new InputStreamReader的对象
        while ((str = br.readLine()) != null) {
            sb.append(str).append("\n");
        }
        // 当读取的一行不为空时,把读到的str的值赋给str1
        System.out.println(sb);// 打印出str1

    }

    @Test
    public void testDEdge01() throws IOException {

        String eventName = "eventName01";
        String u = "hhh";
        int netType = 5;

        DEdgeClass01.DV_ZD_2 dv_zd_2 = DEdgeClass01.DV_ZD_2.newBuilder()
                .setEventName(eventName)
                .setU(u)
                .setNetType(netType)
                .build();

        // byte
        byte[] bytes = dv_zd_2.toByteArray();
        DEdgeClass01.DV_ZD_2 deserialized01 = DEdgeClass01.DV_ZD_2.newBuilder().mergeFrom(bytes)
                .build();
        if (deserialized01.hasA9()) {
            logger.info("hhh, {}", deserialized01.getA9());
        }

        if (deserialized01.hasEventName()) {
            logger.info("hhhEvent, {}", deserialized01.getEventName());
        }
        assertEquals(deserialized01.getEventName(), eventName);
        assertEquals(deserialized01.getU(), u);
        assertEquals(deserialized01.getNetType(), netType);

        // byte from file
        String filePathDEdge = "/Users/dormifu/Downloads/protobuf-output";
        //        FileInputStream fis = new FileInputStream(filePathDEdge);
        byte[] fileContent = Files.readAllBytes(Paths.get(filePathDEdge));
        DEdgeClass01.DV_ZD_2 deserialized = DEdgeClass01.DV_ZD_2.newBuilder().mergeFrom(fileContent)
                .build();
        //        fis.close();
        logger.info("hh is {}", deserialized.getEventName());
        logger.info("hh is {}", deserialized.getU());
        logger.info("hh is {}", deserialized.getNetType());

    }

    @Test
    public void testDEdge02() throws Exception {
        String t = "TxtInputTarget";
        String t1 = camelToUnder(t);

        // byte from file
        //        String filePathDEdge = "/Users/dormifu/Downloads/protobuf_output_simple0923";
        //        String filePathDEdge = "/Users/dormifu/Downloads/protobuf_output_array0923";
        String filePathDEdge = "/Users/dormifu/Downloads/protobuf_binary_data092402";
        //        FileInputStream fis = new FileInputStream(filePathDEdge);
        byte[] fileContent = Files.readAllBytes(Paths.get(filePathDEdge));
        ZDProtobufClass.DV_ZD_V3 deserializedOld = ZDProtobufClass.DV_ZD_V3.newBuilder()
                .mergeFrom(fileContent)
                .build();
        //        fis.close();
        Object temp = deserializedOld;
        ZDProtobufClass.DV_ZD_V3 deserialized = (ZDProtobufClass.DV_ZD_V3) temp;
        logger.info("hh is {}", deserialized.getEventName());
        logger.info("hh is {}", deserialized.getU());
        logger.info("hh is {}", deserialized.getD39());

        logger.info("tcinfo is {}", deserialized.hasTcInfo());

        //        List<ZDProtobufClass.TC_POINT> tcPointList = deserialized.getTcInfo().getTpList();
        //        for (ZDProtobufClass.TC_POINT tcPoint : tcPointList) {
        //            logger.info("tc is {}", tcPoint.getX());
        //            logger.info("tc is {}", tcPoint.getY());
        //            logger.info("tc is {}", tcPoint.getRelativeTime());
        //            logger.info("========");
        //        }

        //
        // 最好有nameMap和typeMap，nameMap是为了后面万一有名字的mapping

        // Just for the name mapping
        HashMap<String, String> originalToEventAttrNameMap = generateNameMap();
        //        HashMap<String, String> nameToTypeMap = new HashMap<>();
        //        nameToTypeMap.put("event_name", "String");

        Map<String, Tuple2<String, String>> fieldToValueMap = new HashMap<>();

        // try to generate has method
        StringBuilder sb = new StringBuilder();
        Class<?> currentClass = ZDProtobufClass.DV_ZD_V3.class;
        Method[] methods = currentClass.getDeclaredMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("has")) {
                String name = methodName.substring(3);
                String getterName = "get" + name;
                Method getterMethod = currentClass.getDeclaredMethod(getterName);
                Class<?> type = getterMethod.getReturnType();
                //                Object result = getterMethod.invoke(deserialized);
                String camelToUnderName = camelToUnder(name);
                //                fieldToValueMap.put(originalToEventAttrNameMap.get(camelToUnderName),
                //                        new Tuple2<>(type.getSimpleName(), result.toString()));
                if ("String".equals(type.getSimpleName())) {
                    sb.append("if (deserialized.").append(methodName)
                            .append("() && deserialized.").append(getterName)
                            .append("() != null) {\n");
                } else {
                    sb.append("if (deserialized.").append(methodName).append("()) {\n");
                }
                sb.append("fieldToValueMap.put(originalToEventAttrNameMap.getOrDefault(\"")
                        .append(camelToUnderName).append("\", \"").append(camelToUnderName)
                        .append("\"), new Tuple2<>(\"")
                        .append(type.getSimpleName()).append("\", String.valueOf(deserialized.")
                        .append(getterName).append("())));\n"
                        + "        }\n");
            }
        }

        //

        //        Class<?> currentClass = ZDProtobufClass.DV_ZD_V3.class;
        //        Method[] methods = currentClass.getDeclaredMethods();
        //        for (Method method : methods) {
        //            String methodName = method.getName();
        //            if (methodName.startsWith("has") && (boolean) method.invoke(deserialized)) {
        //                if (!methodName.contains("TcInfo")) {
        //                    String name = methodName.substring(3);
        //                    String getterName = "get" + name;
        //                    Method getterMethod = currentClass.getDeclaredMethod(getterName);
        //                    Class<?> type = getterMethod.getReturnType();
        //                    Object result = getterMethod.invoke(deserialized);
        //                    String camelToUnderName = camelToUnder(name);
        //                    fieldToValueMap.put(originalToEventAttrNameMap.get(camelToUnderName),
        //                            new Tuple2<>(type.getSimpleName(), result.toString()));
        //                } else {
        //                    // convert to json
        //                }
        //            }
        //        }

        logger.info("final size is {}", fieldToValueMap.size());
        // 注意，tcinfo里面的不要用methods方法注入，直接手动set进去得了。

    }

    private HashMap<String, String> generateNameMap() {
        HashMap<String, String> originalToEventAttrNameMap = new HashMap<>();
        originalToEventAttrNameMap.put("event_name", "event_name");
        originalToEventAttrNameMap.put("type", "type");
        originalToEventAttrNameMap.put("source", "source");
        originalToEventAttrNameMap.put("ptt", "ptt");
        originalToEventAttrNameMap.put("index", "index");
        originalToEventAttrNameMap.put("page_index", "page_index");
        originalToEventAttrNameMap.put("page_name", "page_name");
        originalToEventAttrNameMap.put("page_title", "page_title");
        originalToEventAttrNameMap.put("pre_page_name", "pre_page_name");
        originalToEventAttrNameMap.put("pre_page_title", "pre_page_title");
        originalToEventAttrNameMap.put("stt", "stt");
        originalToEventAttrNameMap.put("web_page_url", "web_page_url");
        originalToEventAttrNameMap.put("web_page_title", "web_page_title");
        originalToEventAttrNameMap.put("web_req_uri", "web_req_uri");
        originalToEventAttrNameMap.put("web_req_mothed", "web_req_mothed");
        originalToEventAttrNameMap.put("click_x", "click_x");
        originalToEventAttrNameMap.put("click_y", "click_y");
        originalToEventAttrNameMap.put("web_winsize", "web_winsize");
        originalToEventAttrNameMap.put("net_status", "net_status");
        originalToEventAttrNameMap.put("pre_net_status", "pre_net_status");
        originalToEventAttrNameMap.put("txt_input_target", "txt_input_target");
        originalToEventAttrNameMap.put("txt_len", "txt_len");
        originalToEventAttrNameMap.put("tc_info", "tc_info");
        originalToEventAttrNameMap.put("blue_status", "blue_status");
        originalToEventAttrNameMap.put("app_duration", "app_duration");
        originalToEventAttrNameMap.put("page_duration", "page_duration");
        originalToEventAttrNameMap.put("u1", "u1");
        originalToEventAttrNameMap.put("v1", "v1");
        originalToEventAttrNameMap.put("v2", "v2");
        originalToEventAttrNameMap.put("d78", "d78");
        originalToEventAttrNameMap.put("d15", "d15");
        originalToEventAttrNameMap.put("d39", "d39");
        originalToEventAttrNameMap.put("d601", "d601");
        originalToEventAttrNameMap.put("d29", "d29");
        originalToEventAttrNameMap.put("d54", "d54");
        originalToEventAttrNameMap.put("n1", "n1");
        originalToEventAttrNameMap.put("n4", "n4");
        originalToEventAttrNameMap.put("d33", "d33");
        originalToEventAttrNameMap.put("d24", "d24");
        originalToEventAttrNameMap.put("d40", "d40");
        originalToEventAttrNameMap.put("d51", "d51");
        originalToEventAttrNameMap.put("a2", "a2");
        originalToEventAttrNameMap.put("a9", "a9");
        originalToEventAttrNameMap.put("d76", "d76");
        originalToEventAttrNameMap.put("d602", "d602");
        originalToEventAttrNameMap.put("d8", "d8");
        originalToEventAttrNameMap.put("d603", "d603");
        originalToEventAttrNameMap.put("c1", "c1");
        originalToEventAttrNameMap.put("c3", "c3");
        originalToEventAttrNameMap.put("c10", "c10");
        originalToEventAttrNameMap.put("c8", "c8");
        originalToEventAttrNameMap.put("c9", "c9");
        originalToEventAttrNameMap.put("c201", "c201");
        originalToEventAttrNameMap.put("c203", "c203");
        originalToEventAttrNameMap.put("c208", "c208");
        originalToEventAttrNameMap.put("d44", "d44");
        originalToEventAttrNameMap.put("d16", "d16");
        originalToEventAttrNameMap.put("d42", "d42");
        originalToEventAttrNameMap.put("d94", "d94");
        originalToEventAttrNameMap.put("d604", "d604");
        originalToEventAttrNameMap.put("d605", "d605");
        originalToEventAttrNameMap.put("d61", "d61");
        originalToEventAttrNameMap.put("u", "u");
        originalToEventAttrNameMap.put("j1", "j1");
        originalToEventAttrNameMap.put("j2", "j2");
        originalToEventAttrNameMap.put("j5", "j5");
        originalToEventAttrNameMap.put("j6", "j6");
        originalToEventAttrNameMap.put("j7", "j7");
        originalToEventAttrNameMap.put("j10", "j10");
        originalToEventAttrNameMap.put("j11", "j11");
        originalToEventAttrNameMap.put("j12", "j12");
        originalToEventAttrNameMap.put("j15", "j15");
        originalToEventAttrNameMap.put("j16", "j16");
        originalToEventAttrNameMap.put("j17", "j17");
        originalToEventAttrNameMap.put("j18", "j18");
        originalToEventAttrNameMap.put("j36", "j36");
        originalToEventAttrNameMap.put("j37", "j37");
        originalToEventAttrNameMap.put("j38", "j38");
        originalToEventAttrNameMap.put("j39", "j39");
        originalToEventAttrNameMap.put("j40", "j40");
        originalToEventAttrNameMap.put("j41", "j41");
        originalToEventAttrNameMap.put("j42", "j42");
        originalToEventAttrNameMap.put("j43", "j43");
        originalToEventAttrNameMap.put("j44", "j44");
        originalToEventAttrNameMap.put("j45", "j45");
        originalToEventAttrNameMap.put("j46", "j46");
        originalToEventAttrNameMap.put("j51", "j51");
        originalToEventAttrNameMap.put("j52", "j52");
        originalToEventAttrNameMap.put("j53", "j53");
        originalToEventAttrNameMap.put("j54", "j54");
        originalToEventAttrNameMap.put("j55", "j55");
        originalToEventAttrNameMap.put("j56", "j56");
        originalToEventAttrNameMap.put("j57", "j57");
        originalToEventAttrNameMap.put("j58", "j58");
        originalToEventAttrNameMap.put("j59", "j59");
        originalToEventAttrNameMap.put("j60", "j60");
        originalToEventAttrNameMap.put("j61", "j61");
        originalToEventAttrNameMap.put("j62", "j62");
        originalToEventAttrNameMap.put("j63", "j63");
        originalToEventAttrNameMap.put("j64", "j64");
        originalToEventAttrNameMap.put("j70", "j70");
        originalToEventAttrNameMap.put("j71", "j71");
        originalToEventAttrNameMap.put("j73", "j73");
        return originalToEventAttrNameMap;
    }

    private String camelToUnder(String value) {
        String regex = "([a-z])([A-Z])";
        String replacement = "$1_$2";
        return value.replaceAll(regex, replacement).toLowerCase();
    }

}
