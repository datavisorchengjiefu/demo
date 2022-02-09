package com.fcjexample.demo.util.datafactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Arrays;

public class BufferReaderWriterForReplay {
    private static final Logger logger = LoggerFactory.getLogger(BufferReaderWriterForReplay.class);

    public static void main(String[] args) throws IOException {
        writeUsingBufferWriterFlink();
        //        writeUsingBufferWriter2();
        //        readUsingBufferReader();
        //        write100K_200K();
        //        writeLargeDimension();
        //                writeLargeTarget();

    }

    private static void writeUsingBufferWriter2() throws IOException {
        System.out.println("1. Start writing contents to file - BufferedWriter");
        try {
            FileOutputStream outputStream = new FileOutputStream(
                    "/Users/dormifu/test/replaycli/dataFactory/for-distinct-count02.json");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            //            {"userId":"user001","amount":"10","time":"10"}
            //            {"userId":"user001","amount":"20","time":"20"}
            for (int i = 51; i <= 70; i++) {
                StringBuilder sb = new StringBuilder();

                sb.append("{\"user_id\":\"user001\",\"income\":\"");
                sb.append(i);
                sb.append("\",\"time\":\"");
                sb.append(i);
                sb.append("\"}");

                bufferedWriter.write(String.valueOf(sb));
                bufferedWriter.newLine();
            }

            for (int i = 71; i <= 80; i++) {
                StringBuilder sb = new StringBuilder();

                sb.append("{\"user_id\":\"user002\",\"income\":\"");
                sb.append(i);
                sb.append("\",\"time\":\"");
                sb.append(i);
                sb.append("\"}");

                bufferedWriter.write(String.valueOf(sb));
                bufferedWriter.newLine();
            }

            for (int i = 81; i <= 90; i++) {
                StringBuilder sb = new StringBuilder();

                sb.append("{\"user_id\":\"user003\",\"income\":\"");
                sb.append(i);
                sb.append("\",\"time\":\"");
                sb.append(i);
                sb.append("\"}");

                bufferedWriter.write(String.valueOf(sb));
                bufferedWriter.newLine();
            }

            //            for (int i = 3001; i <= 6000; i++) {
            //                StringBuilder sb = new StringBuilder();
            //
            //                sb.append("{\"userId\":\"user003\",\"amount\":\"");
            //                sb.append(i);
            //                sb.append("\",\"time\":\"");
            //                sb.append(i);
            //                sb.append("\"}");
            //
            //                bufferedWriter.write(String.valueOf(sb));
            //                bufferedWriter.newLine();
            //            }

            bufferedWriter.close();
        } catch (Exception e) {
            logger.error("failed. ", e);
        }
        System.out.println("2. Successfully written contents to file - BufferedWriter");
    }

    private static void writeUsingBufferWriterFlink() throws IOException {
        System.out.println("1. Start writing contents to file - BufferedWriter");
        try {
            FileOutputStream outputStream = new FileOutputStream(
                    "/Users/dormifu/test/replaycli/dataFactory/flink/distinct_count_12000.csv");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            bufferedWriter.write("userId,time,device,card,amount\n");
            int num = 12000;
            for (int i = 1; i < num; i++) {
                StringBuilder sb = new StringBuilder();
                sb.append("u1,").append(i * 1000).append(",d2,c1,").append(i);
                bufferedWriter.write(String.valueOf(sb));
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
        } catch (Exception e) {
            logger.error("failed. ", e);
        }
        System.out.println("2. Successfully written contents to file - BufferedWriter");
    }

    private static void writeUsingBufferWriter() throws IOException {
        System.out.println("1. Start writing contents to file - BufferedWriter");
        try {
            FileOutputStream outputStream = new FileOutputStream(
                    "/Users/dormifu/test/replaycli/dataFactory/replaycli02-03.json");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            //            {"userId":"user001","amount":"10","time":"10"}
            //            {"userId":"user001","amount":"20","time":"20"}
            for (int i = 1; i <= 2000; i++) {
                StringBuilder sb = new StringBuilder();

                sb.append("{\"userId\":\"user001\",\"amount\":\"");
                sb.append(i);
                sb.append("\",\"time\":\"");
                sb.append(i);
                sb.append("\"}");

                bufferedWriter.write(String.valueOf(sb));
                bufferedWriter.newLine();
            }

            for (int i = 2001; i <= 5000; i++) {
                StringBuilder sb = new StringBuilder();

                sb.append("{\"userId\":\"user002\",\"amount\":\"");
                sb.append(i);
                sb.append("\",\"time\":\"");
                sb.append(i);
                sb.append("\"}");

                bufferedWriter.write(String.valueOf(sb));
                bufferedWriter.newLine();
            }

            //            for (int i = 3001; i <= 6000; i++) {
            //                StringBuilder sb = new StringBuilder();
            //
            //                sb.append("{\"userId\":\"user003\",\"amount\":\"");
            //                sb.append(i);
            //                sb.append("\",\"time\":\"");
            //                sb.append(i);
            //                sb.append("\"}");
            //
            //                bufferedWriter.write(String.valueOf(sb));
            //                bufferedWriter.newLine();
            //            }

            bufferedWriter.close();
        } catch (Exception e) {
            logger.error("failed. ", e);
        }
        System.out.println("2. Successfully written contents to file - BufferedWriter");
    }

    private static void write100K_200K() throws IOException {
        System.out.println("1. Start writing contents to file - BufferedWriter");
        try {
            FileOutputStream outputStream = new FileOutputStream(
                    "/Users/dormifu/test/replaycli/dataFactory/replaycli02-10K-20K.json");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            //            {"userId":"user001","amount":"10","time":"10"}
            //            {"userId":"user001","amount":"20","time":"20"}
            for (int i = 1; i <= 100000; i++) {
                StringBuilder sb = new StringBuilder();

                sb.append("{\"userId\":\"user001\",\"amount\":\"");
                sb.append(i);
                sb.append("\",\"time\":\"");
                sb.append(i);
                sb.append("\"}");

                bufferedWriter.write(String.valueOf(sb));
                bufferedWriter.newLine();
            }

            for (int i = 100001; i <= 300000; i++) {
                StringBuilder sb = new StringBuilder();

                sb.append("{\"userId\":\"user002\",\"amount\":\"");
                sb.append(i);
                sb.append("\",\"time\":\"");
                sb.append(i);
                sb.append("\"}");

                bufferedWriter.write(String.valueOf(sb));
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
        } catch (Exception e) {
            logger.error("failed. ", e);
        }
        System.out.println("2. Successfully written contents to file - BufferedWriter");
    }

    private static void writeLargeDimension() throws IOException {
        System.out.println("1. Start writing contents to file - BufferedWriter");
        try {
            FileOutputStream outputStream = new FileOutputStream(
                    "/Users/dormifu/test/replaycli/dataFactory/largeDimension-10K-10K.json");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            //            {"userId":"user001","amount":"10","time":"10"}
            //            {"userId":"user001","amount":"20","time":"20"}
            for (int i = 1; i <= 10; i++) {
                char[] chars = new char[10000];
                Arrays.fill(chars, 'a');
                String str = new String(chars);
                StringBuilder sb = new StringBuilder();

                sb.append("{\"amount\":\"");
                sb.append(i);
                sb.append("\",\"time\":\"");
                sb.append(i);
                sb.append("\",\"userId\":\"");
                sb.append(str);
                sb.append("\"}");

                bufferedWriter.write(String.valueOf(sb));
                bufferedWriter.newLine();
            }

            for (int i = 1; i <= 10; i++) {

                char[] chars = new char[10000];
                Arrays.fill(chars, 'b');
                String str = new String(chars);
                StringBuilder sb = new StringBuilder();

                sb.append("{\"amount\":\"");
                sb.append(i);
                sb.append("\",\"time\":\"");
                sb.append(i);
                sb.append("\",\"userId\":\"");
                sb.append(str);
                sb.append("\"}");

                bufferedWriter.write(String.valueOf(sb));
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
        } catch (Exception e) {
            logger.error("failed. ", e);
        }
        System.out.println("2. Successfully written contents to file - BufferedWriter");
    }

    private static void writeLargeTarget() throws IOException {
        System.out.println("1. Start writing contents to file - BufferedWriter");
        try {
            FileOutputStream outputStream = new FileOutputStream(
                    "/Users/dormifu/test/replaycli/dataFactory/largeTarget-10K-10K.json");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            //            {"userId":"user001","amount":"10","time":"10"}
            //            {"userId":"user001","amount":"20","time":"20"}
            for (int i = 1; i <= 10; i++) {
                char[] chars = new char[10000];
                Arrays.fill(chars, 'a');
                String str = new String(chars);
                StringBuilder sb = new StringBuilder();

                sb.append("{\"userId\":\"user001\",\"time\":\"");
                sb.append(i);
                sb.append("\",\"device\":\"");
                sb.append(str);
                sb.append("\"}");

                bufferedWriter.write(String.valueOf(sb));
                bufferedWriter.newLine();
            }

            for (int i = 1; i <= 15; i++) {
                char[] chars = new char[10000];
                Arrays.fill(chars, 'b');
                String str = new String(chars);
                StringBuilder sb = new StringBuilder();

                sb.append("{\"userId\":\"user002\",\"time\":\"");
                sb.append(i);
                sb.append("\",\"device\":\"");
                sb.append(str);
                sb.append("\"}");

                bufferedWriter.write(String.valueOf(sb));
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
        } catch (Exception e) {
            logger.error("failed. ", e);
        }
        System.out.println("2. Successfully written contents to file - BufferedWriter");
    }

    private static void readUsingBufferReader() throws IOException {
        try (FileInputStream fileStream = new FileInputStream(
                new File("/Users/dormifu/test/replaycli/dataFactory/sampleFile.txt"));
                InputStreamReader reader = new InputStreamReader(fileStream);
                BufferedReader bufferedReader = new BufferedReader(reader)) {
            System.out.println("\n3. Start Reading file using BufferedReader");
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("4. End Reading file using BufferedReader");
        }
    }

}