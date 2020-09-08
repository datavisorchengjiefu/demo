package com.fcjexample.demo.util.datafactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class BufferReaderWriterForReplay {
    private static final Logger logger = LoggerFactory.getLogger(
            com.datavisor.replaycli.util.BufferReaderWriterForReplay.class);

    public static void main(String[] args) throws IOException {
        writeUsingBufferWriter();
        //        readUsingBufferReader();
    }

    private static void writeUsingBufferWriter() throws IOException {
        System.out.println("1. Start writing contents to file - BufferedWriter");
        try {
            FileOutputStream outputStream = new FileOutputStream(
                    "/Users/dormifu/test/replaycli/dataFactory/replaycli02-02.json");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            //            {"userId":"user001","amount":"10","time":"10"}
            //            {"userId":"user001","amount":"20","time":"20"}
            for (int i = 1; i <= 1000; i++) {
                StringBuilder sb = new StringBuilder();

                sb.append("{\"userId\":\"user001\",\"amount\":\"");
                sb.append(i);
                sb.append("\",\"time\":\"");
                sb.append(i);
                sb.append("\"}");

                bufferedWriter.write(String.valueOf(sb));
                bufferedWriter.newLine();
            }

            for (int i = 1001; i <= 3000; i++) {
                StringBuilder sb = new StringBuilder();

                sb.append("{\"userId\":\"user002\",\"amount\":\"");
                sb.append(i);
                sb.append("\",\"time\":\"");
                sb.append(i);
                sb.append("\"}");

                bufferedWriter.write(String.valueOf(sb));
                bufferedWriter.newLine();
            }

            for (int i = 3001; i <= 6000; i++) {
                StringBuilder sb = new StringBuilder();

                sb.append("{\"userId\":\"user003\",\"amount\":\"");
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