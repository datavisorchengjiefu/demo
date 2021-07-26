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
import org.apache.datasketches.hll.HllSketch;
import org.apache.datasketches.hll.TgtHllType;
import org.apache.datasketches.hll.Union;
import org.apache.datasketches.memory.Memory;

import java.io.FileInputStream;
import java.io.FileOutputStream;

// simplified file operations and no error handling for clarity
public class HllExample {

    public static void main(String[] args) throws Exception {
//        doSketchesTempFile();
        doSketchesTempFile02();
//        doSketches02();
    }


    private static void doSketchesTempFile() throws Exception {
        int lgK = 12;

        // this section generates two sketches with some overlap and serializes them into files
        {
            // 100000 unique keys
            HllSketch sketch1 = new HllSketch(lgK);
            for (int key = 0; key < 100000; key++) sketch1.update(key);
            System.out.println("sketch1 compact size is: " + sketch1.getCompactSerializationBytes());
            System.out.println("sketch1 compact size is: " + InstrumentationAgent.getObjectSize(sketch1.toCompactByteArray()));

            FileOutputStream out1 = new FileOutputStream("HllSketch1.bin");
            out1.write(sketch1.toCompactByteArray());
            out1.close();

            // 100000 unique keys
            HllSketch sketch2 = new HllSketch(lgK);
            for (int key = 50000; key < 150000; key++) sketch2.update(key);
            System.out.println("sketch2 compact size is: " + sketch2.getCompactSerializationBytes());
            System.out.println("sketch2 compact size is: " + InstrumentationAgent.getObjectSize(sketch2.toCompactByteArray()));


            FileOutputStream out2 = new FileOutputStream("HllSketch2.bin");
            out2.write(sketch2.toCompactByteArray());
            out2.close();
        }

        // this section deserializes the sketches, produces union and prints the results
        {
            FileInputStream in1 = new FileInputStream("HllSketch1.bin");
            byte[] bytes1 = new byte[in1.available()];
            in1.read(bytes1);
            in1.close();
            HllSketch sketch1 = HllSketch.heapify(Memory.wrap(bytes1));

            FileInputStream in2 = new FileInputStream("HllSketch2.bin");
            byte[] bytes2 = new byte[in2.available()];
            in2.read(bytes2);
            in2.close();
            HllSketch sketch2 = HllSketch.heapify(Memory.wrap(bytes2));

            Union union = new Union(lgK);
            union.update(sketch1);
            union.update(sketch2);
            HllSketch unionResult = union.getResult(TgtHllType.HLL_4);

            // debug summary of the union result sketch
            System.out.println(unionResult.toString());

            System.out.println("Union unique count estimate: " + unionResult.getEstimate());
            System.out.println("Union unique count lower bound 95% confidence: " + unionResult.getLowerBound(2));
            System.out.println("Union unique count upper bound 95% confidence: " + unionResult.getUpperBound(2));
        }
    }

    private static void doSketchesTempFile02() throws Exception {
        int lgK = 12;

        // this section generates two sketches with some overlap and serializes them into files
        {
            // 100000 unique keys
            HllSketch sketch1 = new HllSketch(lgK);
            for (int key = 0; key < 8000000; key++) sketch1.update(key);
            System.out.println("sketch1 compact size is: " + sketch1.getCompactSerializationBytes());
            System.out.println("sketch1 compact size is: " + InstrumentationAgent.getObjectSize(sketch1.toCompactByteArray()));

            FileOutputStream out1 = new FileOutputStream("HllSketch1.bin");
            out1.write(sketch1.toCompactByteArray());
            out1.close();

            // 100000 unique keys
            HllSketch sketch2 = new HllSketch(lgK);
            for (int key = 5000000; key < 864000000; key++) sketch2.update(key);
            System.out.println("sketch2 compact size is: " + sketch2.getCompactSerializationBytes());
            System.out.println("sketch2 compact size is: " + InstrumentationAgent.getObjectSize(sketch2.toCompactByteArray()));


            FileOutputStream out2 = new FileOutputStream("HllSketch2.bin");
            out2.write(sketch2.toCompactByteArray());
            out2.close();
        }

        // this section deserializes the sketches, produces union and prints the results
        {
            FileInputStream in1 = new FileInputStream("HllSketch1.bin");
            byte[] bytes1 = new byte[in1.available()];
            in1.read(bytes1);
            in1.close();
            HllSketch sketch1 = HllSketch.heapify(Memory.wrap(bytes1));

            FileInputStream in2 = new FileInputStream("HllSketch2.bin");
            byte[] bytes2 = new byte[in2.available()];
            in2.read(bytes2);
            in2.close();
            HllSketch sketch2 = HllSketch.heapify(Memory.wrap(bytes2));

            Union union = new Union(lgK);
            union.update(sketch1);
            union.update(sketch2);
            HllSketch unionResult = union.getResult(TgtHllType.HLL_4);

            // debug summary of the union result sketch
            System.out.println(unionResult.toString());

            System.out.println("Union unique count estimate: " + unionResult.getEstimate());
            System.out.println("Union unique count lower bound 95% confidence: " + unionResult.getLowerBound(2));
            System.out.println("Union unique count upper bound 95% confidence: " + unionResult.getUpperBound(2));
        }
    }

    private static void doSketches02() throws Exception {
        final int lgK = 10;

        // this section deserializes the sketches, produces union and prints the results
        {
//            FileInputStream in1 = new FileInputStream("HllSketch1.bin");
//            byte[] bytes1 = new byte[in1.available()];
//            in1.read(bytes1);
//            in1.close();
//            HllSketch sketch1 = HllSketch.heapify(Memory.wrap(bytes1));

            long num1 = 4600000;
//            long num1 = 4600;
            HllSketch sketch1 = new HllSketch(lgK);
            for (long key = 0; key < num1; key++) {
                sketch1.update(key);// todo 同一个sketch? 还有不同的string的情况下。
            }
            System.out.println("sketch1 compact size is: " + sketch1.getCompactSerializationBytes());


//            FileInputStream in2 = new FileInputStream("HllSketch2.bin");
//            byte[] bytes2 = new byte[in2.available()];
//            in2.read(bytes2);
//            in2.close();
//            HllSketch sketch2 = HllSketch.heapify(Memory.wrap(bytes2));

            long num2 = 860000000;
            long num3 = 7000000;
            long num4 = 8000000;
            HllSketch sketch2 = new HllSketch(lgK);
            for (long key = 165000; key < num4; key++) {
                sketch2.update(key);
            }
            for (long key = num3; key < num2; key++) {
                sketch2.update(key);
            }
            System.out.println("sketch2 compact size is: " + sketch2.getCompactSerializationBytes());


//            HllSketch sketch3 = new HllSketch(lgK);
//            for (int key = 120000; key < 200000; key++) {
//                sketch3.update(key);
//            }

            Union union = new Union(lgK);
            union.update(sketch1);
            union.update(sketch2);
//            union.update(sketch3);
            HllSketch unionResult = union.getResult(TgtHllType.HLL_4);

            // debug summary of the union result sketch
            System.out.println("Starting...");
            System.out.println(unionResult.toString());

            System.out.println("Union unique count estimate: " + unionResult.getEstimate());
            System.out.println("Union unique count lower bound 95% confidence: " + unionResult.getLowerBound(2));
            System.out.println("Union unique count upper bound 95% confidence: " + unionResult.getUpperBound(2));
        }
    }
}
