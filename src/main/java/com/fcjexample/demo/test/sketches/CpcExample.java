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

package com.fcjexample.demo.test.sketches;


import com.fcjexample.demo.util.InstrumentationAgent;
import org.apache.datasketches.cpc.CpcSketch;
import org.apache.datasketches.cpc.CpcUnion;
import org.apache.datasketches.memory.Memory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

//simplified file operations and no error handling for clarity
public class CpcExample {

    public static void main(String[] args) throws Exception {
//        test01();
//        test02();
//        test03();
        test04();
    }

    private static void test01() throws IOException {
        final int lgK = 11;
        // this section generates two sketches with some overlap and serializes them into files
        {
            // 100000 distinct keys
            int num1 = 65536;
//            int num1 = 64;
            int num2 = 50;
            int num3 = 150;
            CpcSketch sketch1 = new CpcSketch(lgK);
            for (int key = 0; key < num1; key++) sketch1.update(key);
//            for (int key = 0; key < num1; key++) sketch1.update("198.127." + key);
            System.out.println("sketch1 compact size is: " + InstrumentationAgent.getObjectSize(sketch1.toByteArray()));
            FileOutputStream out1 = new FileOutputStream("CpcSketch1.bin");
            byte[] b1 = sketch1.toByteArray();
            out1.write(b1);
            out1.close();

            // 100000 distinct keys
            CpcSketch sketch2 = new CpcSketch(lgK);
            for (int key = num2; key < num3; key++) sketch2.update(key);
//            for (int key = num2; key < num3; key++) sketch2.update("198.127." + key);
            System.out.println("sketch2 compact size is: " + InstrumentationAgent.getObjectSize(sketch2.toByteArray()));
            FileOutputStream out2 = new FileOutputStream("CpcSketch2.bin");
            out2.write(sketch2.toByteArray());
            out2.close();
        }

        // this section deserializes the sketches, produces a union and prints the result
        {
            FileInputStream in1 = new FileInputStream("CpcSketch1.bin");
            byte[] bytes1 = new byte[in1.available()];
            in1.read(bytes1);
            in1.close();
            CpcSketch sketch1 = CpcSketch.heapify(Memory.wrap(bytes1));
            System.out.println("sketch1 compact size is: " + InstrumentationAgent.getObjectSize(sketch1.toByteArray()));

            FileInputStream in2 = new FileInputStream("CpcSketch2.bin");
            byte[] bytes2 = new byte[in2.available()];
            in2.read(bytes2);
            in2.close();
            CpcSketch sketch2 = CpcSketch.heapify(Memory.wrap(bytes2));
            System.out.println("sketch2 compact size is: " + InstrumentationAgent.getObjectSize(sketch2.toByteArray()));

            CpcUnion union = new CpcUnion(lgK);
            union.update(sketch1);
            union.update(sketch2);
            CpcSketch result = union.getResult();

            // debug summary of the union result sketch
            System.out.println(result.toString());

            System.out.println("Distinct count estimate: " + result.getEstimate());
            System.out.println("Distinct count lower bound 95% confidence: " + result.getLowerBound(2));
            System.out.println("Distinct count upper bound 95% confidence: " + result.getUpperBound(2));
        }
    }

    private static void test02() throws IOException {
        final int lgK = 11;
        // this section generates two sketches with some overlap and serializes them into files
        {
            // 100000 distinct keys
            CpcSketch sketch1 = new CpcSketch(lgK);
            for (int key = 0; key < 8000000; key++) sketch1.update(key);
//            for (int key = 0; key < 8000000; key++) sketch1.update("198.127." + key);
            System.out.println("sketch1 compact size is: " + InstrumentationAgent.getObjectSize(sketch1.toByteArray()));
            FileOutputStream out1 = new FileOutputStream("CpcSketch1.bin");
            out1.write(sketch1.toByteArray());
            out1.close();

            // 100000 distinct keys
            CpcSketch sketch2 = new CpcSketch(lgK);
            for (int key = 5000000; key < 864000000; key++) sketch2.update(key);
            System.out.println("sketch2 compact size is: " + InstrumentationAgent.getObjectSize(sketch2.toByteArray()));
            FileOutputStream out2 = new FileOutputStream("CpcSketch2.bin");
            out2.write(sketch2.toByteArray());
            out2.close();
        }

        // this section deserializes the sketches, produces a union and prints the result
        {
            FileInputStream in1 = new FileInputStream("CpcSketch1.bin");
            byte[] bytes1 = new byte[in1.available()];
            in1.read(bytes1);
            in1.close();
            CpcSketch sketch1 = CpcSketch.heapify(Memory.wrap(bytes1));

            FileInputStream in2 = new FileInputStream("CpcSketch2.bin");
            byte[] bytes2 = new byte[in2.available()];
            in2.read(bytes2);
            in2.close();
            CpcSketch sketch2 = CpcSketch.heapify(Memory.wrap(bytes2));

            CpcUnion union = new CpcUnion(lgK);
            union.update(sketch1);
            union.update(sketch2);
            CpcSketch result = union.getResult();

            // debug summary of the union result sketch
            System.out.println(result.toString());

            System.out.println("Distinct count estimate: " + result.getEstimate());
            System.out.println("Distinct count lower bound 95% confidence: " + result.getLowerBound(2));
            System.out.println("Distinct count upper bound 95% confidence: " + result.getUpperBound(2));
        }
    }

    private static void test03() throws IOException {
        final int lgK = 11;
        // this section generates two sketches with some overlap and serializes them into files

        // 100000 distinct keys
        CpcSketch sketch1 = new CpcSketch(lgK);
        for (int key = 0; key < 10000; key++) sketch1.update(key);
        System.out.println("sketch1 compact size is: " + InstrumentationAgent.getObjectSize(sketch1.toByteArray()));

        for (int key = 5000; key < 80000; key++) sketch1.update(key);
        System.out.println("sketch1 compact size is: " + InstrumentationAgent.getObjectSize(sketch1.toByteArray()));

//        sketch1.toByteArray().toString();
        byte[] old = sketch1.toByteArray();
        String temp = new String(old);
        String temp02 = temp;
//        String temp = sketch1.toByteArray();
        byte[] newBytes = temp02.getBytes();

        // wrong, can't use Charset, should use base64
        Charset charset = StandardCharsets.US_ASCII;
        String string = charset.decode(ByteBuffer.wrap(old))
                .toString();

        FileOutputStream out1 = new FileOutputStream("CpcSketch1.bin");
        out1.write(string.getBytes());
        out1.close();

        FileInputStream in1 = new FileInputStream("CpcSketch1.bin");
        byte[] bytes1 = new byte[in1.available()];
        in1.read(bytes1);
        in1.close();

        String string01 = charset.decode(ByteBuffer.wrap(bytes1))
                .toString();
//        CpcSketch sketch1 = CpcSketch.heapify(Memory.wrap(string01.getBytes()));

//        byte[] byteArrrayNew02 = charset.encode(string).array();
        byte[] byteArrrayNew02 = charset.encode(string01).array();

        CpcSketch sketch = CpcSketch.heapify(byteArrrayNew02);

//        CpcSketch sketch = CpcSketch.heapify(Memory.wrap(sketch1.toByteArray()));
//        CpcSketch sketch = CpcSketch.heapify(sketch1.toByteArray());

        CpcUnion union = new CpcUnion(lgK);
        union.update(sketch);
        CpcSketch result = union.getResult();

        // debug summary of the union result sketch
        System.out.println(result.toString());
        System.out.println("hhhh02");

        System.out.println("Distinct count estimate: " + result.getEstimate());
        System.out.println("Distinct count lower bound 95% confidence: " + result.getLowerBound(2));
        System.out.println("Distinct count upper bound 95% confidence: " + result.getUpperBound(2));


    }

    private static void test04() throws IOException {

        final int lgK = 11;
        // this section generates two sketches with some overlap and serializes them into files

        // 100000 distinct keys
        CpcSketch sketch1 = new CpcSketch(lgK);
        for (int key = 0; key < 10000; key++) {
            sketch1.update(key);
        }

        for (int key = 5000; key < 80000; key++) {
            sketch1.update(key);
        }


//        String temp = sketch1.toString();

        /*byte[] old = sketch1.toByteArray();
        Charset charset = StandardCharsets.US_ASCII;
        String stringOld = charset.decode(ByteBuffer.wrap(old))
                .toString();

        byte[] byteArrrayNew02 = charset.encode(stringOld).array();

        byte[] bytes1 = new byte[byteArrrayNew02.length];
        for (int i = 0; i < byteArrrayNew02.length; i++) {
            bytes1[i] = byteArrrayNew02[i];
        }*/


//        String originalInput = "test input";
//        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());

        // need Base64!!!
        byte[] old = sketch1.toByteArray();
        String encodedString = Base64.getEncoder().encodeToString(old);
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);

        CpcSketch sketch = CpcSketch.heapify(Memory.wrap(decodedBytes));
//        CpcSketch sketch = CpcSketch.heapify(Memory.wrap(bytes1));

        CpcUnion union = new CpcUnion(lgK);
        union.update(sketch);
        CpcSketch result = union.getResult();

        // debug summary of the union result sketch
        System.out.println(result.toString());
        System.out.println("hhhh02");

        System.out.println("Distinct count estimate: " + result.getEstimate());
        System.out.println("Distinct count lower bound 95% confidence: " + result.getLowerBound(2));
        System.out.println("Distinct count upper bound 95% confidence: " + result.getUpperBound(2));


    }

}
