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

package com.fcjexample.demo.test.basic;

// An object reference of this class is
// contained by Test2
class Test {
    int x, y;
}

// Contains a reference of Test and implements
// clone with shallow copy.
class Test2 implements Cloneable {
    int a;
    int b;
    Test c = new Test();

    public Object clone() throws
            CloneNotSupportedException {
        return super.clone();
    }
}

// Driver class
public class Main {
    public static void main(String args[]) throws
            CloneNotSupportedException {
        Test2 t1 = new Test2();
        t1.a = 10;
        t1.b = 20;
        t1.c.x = 30;
        t1.c.y = 40;

        Test2 t2 = (Test2) t1.clone();

        t2.a = 100;

        t2.c.x = 300;

        // Change in object type field will be
        // reflected in both t2 and t1(shallow copy)
        System.out.println(t1.a + " " + t1.b + " " +
                t1.c.x + " " + t1.c.y);
        System.out.println(t2.a + " " + t2.b + " " +
                t2.c.x + " " + t2.c.y);
    }
}
