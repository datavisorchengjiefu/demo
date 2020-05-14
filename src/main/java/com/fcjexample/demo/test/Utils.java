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

import java.io.*;

public class Utils {

    public static void main(String[] args) {
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null; //用于包装InputStreamReader,提高处理性能。因为BufferedReader有缓冲的，而InputStreamReader没有。
        try {
            String str = "";
            String str1 = "";
            StringBuilder sb = new StringBuilder();
            fis = new FileInputStream(
                    "src/main/resources/files/errorHandlingZh.txt");// FileInputStream
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

            File file = new File("src/main/resources/files/errorChOut.txt");
            if (!file.exists()) {
                try {
                    file.createNewFile(); //如果文件不存在则创建文件
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            writeInFile(file, sb.toString());

        } catch (FileNotFoundException e) {
            System.out.println("找不到指定文件");
        } catch (IOException e) {
            System.out.println("读取文件失败");
        } finally {
            try {
                br.close();
                isr.close();
                fis.close();
                // 关闭的时候最好按照先后顺序关闭最后开的先关闭所以先关s,再关n,最后关m
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void writeInFile(File file, String content) {
        Writer writer = null;
        StringBuilder outputString = new StringBuilder();
        try {
            outputString.append(content + "\r\n");
            writer = new FileWriter(file, true); // true表示追加
            writer.write(outputString.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }
}


