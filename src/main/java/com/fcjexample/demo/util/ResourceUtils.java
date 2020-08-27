/*************************************************************************
 *
 * Copyright (c) 2019, DATAVISOR, INC.
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

package com.fcjexample.demo.util;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class ResourceUtils {

    public static String loadResource(String resourcePath) throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream is = loader.getResourceAsStream(resourcePath);
        if (is == null) {
            throw new NullPointerException("Failed to find resource: " + resourcePath);
        }
        return loadResource(is);
    }

    public static byte[] loadResourceToBytes(String resourcePath) throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream is = loader.getResourceAsStream(resourcePath);
        if (is == null) {
            throw new NullPointerException("Failed to find resource: " + resourcePath);
        }
        int size = is.available();
        byte[] bytes = new byte[size];
        if (size != is.read(bytes, 0, size)) {
            throw new IOException();
        }
        is.close();
        return bytes;
    }

    public static String loadResource(InputStream is) throws IOException {
        int size = is.available();
        byte[] bytes = new byte[size];
        if (size != is.read(bytes, 0, size)) {
            throw new IOException();
        }
        is.close();
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static String getFilePath(String fileName) throws Exception {
        return getFileUrl(fileName).getPath();
    }

    public static URL getFileUrl(String fileName) throws Exception {
        URL url = null;
        try {
            url = Thread.currentThread().getContextClassLoader().getResource(fileName);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception when try to find " + fileName
                    + " in class path, try absolute file path instead.");
        }
        if (url == null) {
            System.out.println("Failed to find " + fileName
                    + " in class path, try absolute file path instead.");
            try {
                url = new File(fileName).toURI().toURL();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Failed to find " + fileName + " as absolute path.");
            }
        }
        return url;
    }

    public static void writeTo(OutputStream outputStream, String content) throws Exception {
        try (Writer writer = new BufferedWriter(
                new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
            writer.write(content);
        }
    }

    public static void writeTo(OutputStream outputStream, Collection<String> content,
            String delimiter) throws Exception {
        try (Writer writer = new BufferedWriter(
                new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
            for (String s : content) {
                writer.write(s);
                writer.write(delimiter);
            }
        }
    }

}
