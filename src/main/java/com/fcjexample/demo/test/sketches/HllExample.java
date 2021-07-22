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
import java.util.Set;

// simplified file operations and no error handling for clarity
public class HllExample {

    public static void main(String[] args) throws Exception {
        doSketchesTempFile();
//        doSketches02();
//        double value = 34;
//        int value = 34;
//        Integer value = 34;
//        String value = "34";
//        Set<Integer> value = new HashSet<>();
//        value.add(4);
//        value.add(6);
//        System.out.println(metricsNumberEval(value));
    }


    public static double metricsNumberEval(Object value) throws Exception {
        return ((Set) value).size();
//        return Double.parseDouble(String.valueOf(value));
    }

    private static void doSketchesTempFile() throws Exception {
        final int lgK = 10;

        // this section generates two sketches with some overlap and serializes them into files
        {
            byte[] test = new byte[1024];
            System.out.println("haha is " + InstrumentationAgent.getObjectSize(test));
            // 100000 unique keys
            HllSketch sketch1 = new HllSketch(lgK, TgtHllType.HLL_4);
            for (int key = 0; key < 1000000; key++) {
                sketch1.update(key);
            }
            System.out.println(sketch1.getEstimate() + " kk " + sketch1.getCompositeEstimate());
            FileOutputStream out1 = new FileOutputStream("HllSketch1.bin");
            System.out.println("sketch1 compact size is: " + sketch1.getCompactSerializationBytes());
            out1.write(sketch1.toCompactByteArray());
            System.out.println("sketch1 compact size is: " + sketch1.getCompactSerializationBytes());
            out1.close();

            // 100000 unique keys
            HllSketch sketch2 = new HllSketch(lgK);
            for (int key = 50000; key < 1100000; key++) {
                sketch2.update(key);
            }
            System.out.println("sketch2 size is: " + InstrumentationAgent.getObjectSize(sketch2));
            FileOutputStream out2 = new FileOutputStream("HllSketch2.bin");
            out2.write(sketch2.toCompactByteArray());
            System.out.println("sketch2 compact size is: " + InstrumentationAgent.getObjectSize(sketch2.toCompactByteArray()));
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

            HllSketch sketch1 = new HllSketch(lgK);
            for (int key = 0; key < 100000; key++) {
                sketch1.update(key);
            }

//            FileInputStream in2 = new FileInputStream("HllSketch2.bin");
//            byte[] bytes2 = new byte[in2.available()];
//            in2.read(bytes2);
//            in2.close();
//            HllSketch sketch2 = HllSketch.heapify(Memory.wrap(bytes2));
            HllSketch sketch2 = new HllSketch(lgK);
            for (int key = 50000; key < 150000; key++) {
                sketch2.update(key);
            }

            HllSketch sketch3 = new HllSketch(lgK);
            for (int key = 120000; key < 200000; key++) {
                sketch3.update(key);
            }

            Union union = new Union(lgK);
            union.update(sketch1);
            union.update(sketch2);
            union.update(sketch3);
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
