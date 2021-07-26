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

// simplified file operations and no error handling for clarity

import org.apache.datasketches.ResizeFactor;
import org.apache.datasketches.memory.Memory;
import org.apache.datasketches.theta.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ThetaExample {
    public static void main(String[] args) throws Exception {
        test();
//        test02();
//        testFileCustom();
    }

    private static void test() throws Exception {
// this section generates two sketches with some overlap
// and serializes them into files in compact (not updatable) form
        {
            // 100000 unique keys
//            int num1 = 100000;
            int num1 = 2;
//            int num2 = 50000;
            int num2 = 1;
//            int num3 = 150000;
            int num3 = 12;
//            UpdateSketch sketch1 = UpdateSketch.builder().build();
            UpdateSketch sketch1 = UpdateSketch.builder().setResizeFactor(ResizeFactor.X2).build();
            System.out.println("resize is: " + sketch1.getResizeFactor());
            for (int key = 0; key < Math.pow(2, num1); key++) sketch1.update(key);
            FileOutputStream out1 = new FileOutputStream("ThetaSketch1.bin");
//            System.out.println("sketch1 compact size is: " + InstrumentationAgent.getObjectSize(sketch1.toByteArray())); // big
            System.out.println("sketch1 compact size is: " + sketch1.getCurrentBytes(true));
//            System.out.println("sketch1 compact size is: " + sketch1.getCurrentBytes(false)); // big
//            System.out.println("sketch1 compact size is: " + InstrumentationAgent.getObjectSize(sketch1.compact().toByteArray()));

            out1.write(sketch1.compact().toByteArray());
            out1.close();

            // 100000 unique keys
            // the first 50000 unique keys overlap with sketch1
//            UpdateSketch sketch2 = UpdateSketch.builder().build();
            UpdateSketch sketch2 = UpdateSketch.builder().setResizeFactor(ResizeFactor.X2).build();
            for (double key = 0; key < Math.pow(2, num3); key++) sketch2.update(key);
//            System.out.println("sketch2 compact size is: " + InstrumentationAgent.getObjectSize(sketch2.toByteArray()));
            System.out.println("sketch2 compact size is: " + sketch2.getCurrentBytes(true));
//            System.out.println("sketch2 compact size is: " + sketch2.getCurrentBytes(false));
//            System.out.println("sketch2 compact size is: " + InstrumentationAgent.getObjectSize(sketch2.compact().toByteArray()));
            FileOutputStream out2 = new FileOutputStream("ThetaSketch2.bin");
            out2.write(sketch2.compact().toByteArray());
            out2.close();
        }

// this section deserializes the sketches, produces union and intersection and prints the results
        {
            FileInputStream in1 = new FileInputStream("ThetaSketch1.bin");
            byte[] bytes1 = new byte[in1.available()];
            in1.read(bytes1);
            in1.close();
            Sketch sketch1 = Sketches.wrapSketch(Memory.wrap(bytes1));

            FileInputStream in2 = new FileInputStream("ThetaSketch2.bin");
            byte[] bytes2 = new byte[in2.available()];
            in2.read(bytes2);
            in2.close();
            Sketch sketch2 = Sketches.wrapSketch(Memory.wrap(bytes2));

            Union union = SetOperation.builder().buildUnion();
            union.update(sketch1);
            union.update(sketch2);
            Sketch unionResult = union.getResult();

            // debug summary of the union result sketch
            System.out.println(unionResult.toString());

            System.out.println("Union unique count estimate: " + unionResult.getEstimate());
            System.out.println("Union unique count lower bound 95% confidence: " + unionResult.getLowerBound(2));
            System.out.println("Union unique count upper bound 95% confidence: " + unionResult.getUpperBound(2));

        }

    }

    private static void test02() throws Exception {
// this section generates two sketches with some overlap
// and serializes them into files in compact (not updatable) form
        {
            // 100000 unique keys
            UpdateSketch sketch1 = UpdateSketch.builder().build();
            for (int key = 0; key < 8000000; key++) sketch1.update(key);
            FileOutputStream out1 = new FileOutputStream("ThetaSketch1.bin");
//            System.out.println("sketch1 compact size is: " + InstrumentationAgent.getObjectSize(sketch1.toByteArray())); // big
            System.out.println("sketch1 compact size is: " + sketch1.getCurrentBytes(true));
//            System.out.println("sketch1 compact size is: " + sketch1.getCurrentBytes(false)); // big
//            System.out.println("sketch1 compact size is: " + InstrumentationAgent.getObjectSize(sketch1.compact().toByteArray()));

            out1.write(sketch1.compact().toByteArray());
            out1.close();

            // 100000 unique keys
            // the first 50000 unique keys overlap with sketch1
            UpdateSketch sketch2 = UpdateSketch.builder().build();
            for (int key = 5000000; key < 864000000; key++) sketch2.update(key);
//            System.out.println("sketch2 compact size is: " + InstrumentationAgent.getObjectSize(sketch2.toByteArray()));
            System.out.println("sketch2 compact size is: " + sketch2.getCurrentBytes(true));
//            System.out.println("sketch2 compact size is: " + sketch2.getCurrentBytes(false));
//            System.out.println("sketch2 compact size is: " + InstrumentationAgent.getObjectSize(sketch2.compact().toByteArray()));
            FileOutputStream out2 = new FileOutputStream("ThetaSketch2.bin");
            out2.write(sketch2.compact().toByteArray());
            out2.close();
        }

// this section deserializes the sketches, produces union and intersection and prints the results
        {
            FileInputStream in1 = new FileInputStream("ThetaSketch1.bin");
            byte[] bytes1 = new byte[in1.available()];
            in1.read(bytes1);
            in1.close();
            Sketch sketch1 = Sketches.wrapSketch(Memory.wrap(bytes1));

            FileInputStream in2 = new FileInputStream("ThetaSketch2.bin");
            byte[] bytes2 = new byte[in2.available()];
            in2.read(bytes2);
            in2.close();
            Sketch sketch2 = Sketches.wrapSketch(Memory.wrap(bytes2));

            Union union = SetOperation.builder().buildUnion();
            union.update(sketch1);
            union.update(sketch2);
            Sketch unionResult = union.getResult();

            // debug summary of the union result sketch
            System.out.println(unionResult.toString());

            System.out.println("Union unique count estimate: " + unionResult.getEstimate());
            System.out.println("Union unique count lower bound 95% confidence: " + unionResult.getLowerBound(2));
            System.out.println("Union unique count upper bound 95% confidence: " + unionResult.getUpperBound(2));

        }

    }

    private static void testFileCustom() throws Exception {
        // this section generates two sketches with some overlap
        // and serializes them into files in compact (not updatable) form
        {
            // 100000 unique keys
            UpdateSketch sketch1 = UpdateSketch.builder().build();
            for (int key = 0; key < 100000; key++) sketch1.update(key);
            System.out.println("currentBytes is: " + sketch1.getCurrentBytes(true));
            System.out.println("currentBytes is: " + sketch1.getCurrentBytes(false));
            System.out.println("currentBytes is: " + sketch1.getCurrentBytes(true));
            System.out.println("currentBytes is: " + sketch1.getCurrentBytes(false));
            System.out.println("ha bytes is " + sketch1.compact().toByteArray());

            FileOutputStream out1 = new FileOutputStream("ThetaSketch1.bin");
            out1.write(sketch1.compact().toByteArray());
            out1.close();

            // 100000 unique keys
            // the first 50000 unique keys overlap with sketch1
            UpdateSketch sketch2 = UpdateSketch.builder().build();
            for (int key = 80000; key < 150000; key++) sketch2.update(key);
            FileOutputStream out2 = new FileOutputStream("ThetaSketch2.bin");
            out2.write(sketch2.compact().toByteArray());
            out2.close();
        }

// this section deserializes the sketches, produces union and intersection and prints the results
        {
            FileInputStream in1 = new FileInputStream("ThetaSketch1.bin");
            byte[] bytes1 = new byte[in1.available()];
            in1.read(bytes1);
            in1.close();
            Sketch sketch1 = Sketches.wrapSketch(Memory.wrap(bytes1));

            FileInputStream in2 = new FileInputStream("ThetaSketch2.bin");
            byte[] bytes2 = new byte[in2.available()];
            in2.read(bytes2);
            in2.close();
            Sketch sketch2 = Sketches.wrapSketch(Memory.wrap(bytes2));

            Union union = SetOperation.builder().buildUnion();
            union.update(sketch1);
            union.update(sketch2);
            Sketch unionResult = union.getResult();

            // debug summary of the union result sketch
            System.out.println(unionResult.toString());

            System.out.println("Union unique count estimate: " + unionResult.getEstimate());
            System.out.println("Union unique count lower bound 95% confidence: " + unionResult.getLowerBound(2));
            System.out.println("Union unique count upper bound 95% confidence: " + unionResult.getUpperBound(2));

            Intersection intersection = SetOperation.builder().buildIntersection();
            intersection.update(sketch1);
            intersection.update(sketch2);
            Sketch intersectionResult = intersection.getResult();

            // debug summary of the intersection result sketch
            System.out.println(intersectionResult.toString());

            System.out.println("Intersection unique count estimate: " + intersectionResult.getEstimate());
            System.out.println("Intersection unique count lower bound 95% confidence: " + intersectionResult.getLowerBound(2));
            System.out.println("Intersection unique count upper bound 95% confidence: " + intersectionResult.getUpperBound(2));

            AnotB anotB = SetOperation.builder().buildANotB();
            anotB.update(sketch1, sketch2);
            Sketch result = anotB.getResult();
            System.out.println(result.toString());

            AnotB anotB02 = SetOperation.builder().buildANotB();
            anotB02.update(sketch2, sketch1);
            Sketch result02 = anotB02.getResult();
            System.out.println(result02.toString());
        }
    }
}
