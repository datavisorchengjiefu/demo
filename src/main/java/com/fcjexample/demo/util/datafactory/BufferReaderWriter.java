package com.fcjexample.demo.util.datafactory;

import java.io.*;

public class BufferReaderWriter {
    public static void main(String[] args) throws IOException {
        //        writeUsingBufferWriter();
        //        readUsingBufferReader();
        writeEventForHotspot();
    }

    public static void writeEventForHotspot() {
        String fileName = "/Users/chengjiefu/research/work/develop/QA-2352/eventsForHotspot02.json";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < 10000; i++) {
                String jsonData = generateJsonData(i);
                writer.write(jsonData);
                writer.newLine();
            }
            System.out.println("Data generated successfully and written to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String generateJsonData(int index) {
        String userId = "u" + (index % 10);
        long eventTime = 1703146419001L + index;
        String eventType = "dummyFCJ1";
        String dimensionFcj = "f" + (index % 3);
        int amount = 2;

        return String.format(
                "{\"user_id\":\"%s\",\"event_time\":%d,\"eventType\":\"%s\",\"dimension_fcj\":\"%s\",\"amount\":%d}",
                userId, eventTime, eventType, dimensionFcj, amount);
    }

    private static void writeUsingBufferWriter() throws IOException {
        System.out.println("1. Start writing contents to file - BufferedWriter");
        try (FileOutputStream fileStream = new FileOutputStream(
                new File("/Users/dormifu/test/replaycli/dataFactory/sampleFile.txt"));
                OutputStreamWriter writer = new OutputStreamWriter(fileStream);
                BufferedWriter bufferWriter = new BufferedWriter(writer)) {
            bufferWriter.write("Soccer");
            bufferWriter.newLine();
            bufferWriter.write("Tennis");
            bufferWriter.newLine();
            bufferWriter.write("Badminton");
            bufferWriter.newLine();
            bufferWriter.write("Hockey");
            bufferWriter.newLine();
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