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

package com.fcjexample.demo.util.test;

public class Test {
    public static void main(String[] args) {

        testRegex();

    }

    public static void testRegex() {
        String testReturnMessage =
                "Compile error. Diagnostics info:[GenClass_10106_1599631787508.java:83: error: illegal character: '#'\n"
                        + "\t\treturn (double) ((double) num#0 - (double) num#1);\n"
                        + "\t\t                             ^, GenClass_10106_1599631787508.java:83: error: illegal character: '#'\n"
                        + "\t\treturn (double) ((double) num#0 - (double) num#1);\n"
                        + "\t\t                                              ^, GenClass_10106_1599631787508.java:83: error: not a statement\n"
                        + "\t\treturn (double) ((double) num#0 - (double) num#1);\n"
                        + "\t\t                                ^]";

        String result = testReturnMessage.replaceAll("GenClass.*?:\\d+", "GenClassName.java");

        System.out.println(result);
    }
}
