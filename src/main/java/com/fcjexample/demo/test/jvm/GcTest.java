/*************************************************************************
 *
 * Copyright (c) 2021, DATAVISOR, INC.
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

package com.fcjexample.demo.test.jvm;

public class GcTest {
    private static final int SIZE = 6 * 1024 * 1024;

    public static void localVarGc1() {
        byte[] b = new byte[SIZE];
        System.gc();
    }

    public static void localVarGc2() {
        byte[] b = new byte[SIZE];
        b = null;
        System.gc();
    }

    public static void localVarGc3() {
        {
            byte[] b = new byte[SIZE];
        }
        System.gc();
    }

    public static void localVarGc4() {
        {
            byte[] b = new byte[SIZE];
        }
        int c = 0;
        System.gc();
    }

    public static void localVarGc5() {
        localVarGc1();
        System.gc();
    }

    public static void main(String[] args) {
        //        localVarGc1();   // 没有GC
        localVarGc2();   // GC
        //        localVarGc3();   // 没有GC
        //        localVarGc4();   // GC
        //        localVarGc5();   // GC
    }

}
