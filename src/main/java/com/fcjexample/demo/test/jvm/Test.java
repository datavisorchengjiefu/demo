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

package com.fcjexample.demo.test.jvm;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {
        fillHeap(1000);
        System.gc();

    }

    public static void fillHeap(int num) throws Exception {
        List<OOMObject> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            // sleep make more clear
            Thread.sleep(50);
            list.add(new OOMObject());
        }
        //        System.gc();
    }

    static class OOMObject {
        public byte[] placeholder = new byte[64 * 1024];
    }
}
