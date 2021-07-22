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

package com.fcjexample.demo.test.fp;

public class CodeDevDemo {
    public static void main(String[] args) throws Exception {
//        double value = 34;
//        int value = 34;
//        Integer value = 34;
        String value = "34";

//        Set<Integer> value = new HashSet<>();
//        value.add(4);
//        value.add(6);
        System.out.println(metricsNumberEval(value));
    }


    public static double metricsNumberEval(Object value) throws Exception {
//        return (double) value; //not good
        return Double.parseDouble(String.valueOf(value));

//        return ((Set) value).size();
    }
}
