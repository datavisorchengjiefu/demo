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

package com.fcjexample.demo.test;

import com.fcjexample.demo.util.CSVParserUtil;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Test20211122 {
    public static void main(String[] args) throws Exception {
        List<List<String>> result = new ArrayList<>();
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null; //用于包装InputStreamReader,提高处理性能。因为BufferedReader有缓冲的，而InputStreamReader没有。

        String str = "";
        String str1 = "";
        StringBuilder sb = new StringBuilder();
        fis = new FileInputStream(
                "src/main/resources/files/test.txt");// FileInputStream
        // 从文件系统中的某个文件中获取字节
        isr = new InputStreamReader(fis);// InputStreamReader 是字节流通向字符流的桥梁,
        br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new InputStreamReader的对象
        while ((str = br.readLine()) != null) {
            result.add(CSVParserUtil.getCSVRecordByLineAndDelimiter(str, ','));
        }

        System.out.println(result.size());
        System.out.println("+++++++++++");
        result.forEach(new Consumer<List<String>>() {
            @Override public void accept(List<String> strings) {
                System.out.println(strings.size());
                System.out.println("==========");
            }
        });
    }
}
