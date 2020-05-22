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

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestExceptionDemo {

    @Test
    public void testTryCatch() {
        //        getRead();
        try {
            getRead();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("顶层：");
        }
    }

    public static void getRead() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse("2018-1a 3-01");
            System.out.println("date:" + date);
        } catch (ParseException e) {
            //            e.printStackTrace();
            System.out.println("【解析异常】");
            throw new RuntimeException("hhh01");
        } catch (Exception e) {
            System.out.println("【异常】");
            //throw new Exception();
        }
    }

    @Test
    public void testTryCatch2() {
        try {
            getRead2();
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("【顶层解析异常】");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("【顶层异常】");
        }
        System.out.println("catch2 888");
    }

    //    public static void getRead2() throws ParseException, Exception {
    public static void getRead2() throws ParseException {
        //    public static void getRead2() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse("2018-1a 3-01");
            System.out.println("date:" + date);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("【解析异常】");
            throw new ParseException("解析异常", e.getErrorOffset());
            //            throw new RuntimeException("hhhh");// 如果是runtime的，getRead2方法其实都不用现实地展示出来
        } catch (Exception e) {
            System.out.println("【异常】");
            //            throw new Exception();
        } finally {
            System.out.println("finally 666");
        }
        System.out.println("hahaha");
    }
}
