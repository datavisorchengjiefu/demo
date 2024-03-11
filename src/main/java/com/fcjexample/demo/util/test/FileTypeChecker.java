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

package com.fcjexample.demo.util.test;

public class FileTypeChecker {
    public static String getFileType(String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex != -1) {
            String extension = filename.substring(lastDotIndex + 1).toLowerCase();
            if (extension.equals("csv")) {
                return "csv";
            } else if (extension.equals("json")) {
                return "json";
            }
        }
        return "json"; // Default to JSON if the extension is not recognized
    }

    public static void main(String[] args) {
        String filename1 = "example.csv";
        String filename2 = "example.json";
        String filename3 = "example.txt";
        String filename4 = "example.data.csv";
        String filename5 = "example.config.json";

        System.out.println("File type for " + filename1 + ": " + getFileType(filename1));
        System.out.println("File type for " + filename2 + ": " + getFileType(filename2));
        System.out.println("File type for " + filename3 + ": " + getFileType(filename3));
        System.out.println("File type for " + filename4 + ": " + getFileType(filename4));
        System.out.println("File type for " + filename5 + ": " + getFileType(filename5));
    }
}

