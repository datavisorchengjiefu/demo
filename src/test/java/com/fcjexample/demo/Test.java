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

package com.fcjexample.demo;

import java.util.ArrayList;
import java.util.List;

public class Test {

    private Integer aa;

    @org.junit.Test
    public void test01() {
        System.out.println(aa);

        aa = 78;
        System.out.println(aa);
    }

    @org.junit.Test
    public void test02() {
        System.out.println(aa);

        aa = 9;
        System.out.println(aa);
    }

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        list.add("c2");
        list.add("c3");
        list.add("c1");

        String s = String.join(" ", list);
        System.out.println("hhh " + s);

        String str = "haha";
        new Thread() {
            @Override
            public void run() {
                System.out.println(str);
            }
        }.start();

    }
}
