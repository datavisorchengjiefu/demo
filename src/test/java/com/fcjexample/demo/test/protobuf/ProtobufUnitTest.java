package com.fcjexample.demo.test.protobuf;

import org.junit.After;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    public void givenAddressBookWithOnePerson_whenSaveAsAFile_shouldLoadFromFileToJavaClass() throws IOException {
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

        //when
        AddressBookProtos.AddressBook addressBook = AddressBookProtos.AddressBook.newBuilder().addPeople(person).build();
        FileOutputStream fos = new FileOutputStream(filePath);
        addressBook.writeTo(fos);
        fos.close();

        //then
        FileInputStream fis = new FileInputStream(filePath);
        AddressBookProtos.AddressBook deserialized =
                AddressBookProtos.AddressBook.newBuilder().mergeFrom(fis).build();
        fis.close();
        assertEquals(deserialized.getPeople(0).getEmail(), email);
        assertEquals(deserialized.getPeople(0).getId(), id);
        assertEquals(deserialized.getPeople(0).getName(), name);
        assertEquals(deserialized.getPeople(0).getNumbers(0), number);

        InputStreamReader isr = null;
        BufferedReader br = null; //用于包装InputStreamReader,提高处理性能。因为BufferedReader有缓冲的，而InputStreamReader没有。
        String str = "";
        String str1 = "";
        StringBuilder sb = new StringBuilder();
        fis = new FileInputStream(filePath);// FileInputStream
        // 从文件系统中的某个文件中获取字节
        isr = new InputStreamReader(fis);// InputStreamReader 是字节流通向字符流的桥梁,
        br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new InputStreamReader的对象
        while ((str = br.readLine()) != null) {

            String[] ss = str.split(":");
            if (ss.length > 1) {

                sb.append(ss[1]).append("\n");
            }
        }
        // 当读取的一行不为空时,把读到的str的值赋给str1
        System.out.println(sb);// 打印出str1


    }
}
