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

package com.fcjexample.demo.test;

import org.apache.kafka.common.errors.TopicExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Test01 {
    private static final Logger logger = LoggerFactory.getLogger(Test01.class);

    public static void main(String[] args) {
        String inputPath = "s3a://datavisor-qa-qa784/cronflinkferry/20200101/fpcompute.20200101_10.hadoopParts/part-00000.gz";
        String inputPath2 = "datavisor-qa-qa784/cronflinkferry/20200101/fpcompute.20200101_10.hadoopParts/part-00000.gz";
        String[] segments = inputPath.split("/");
        logger.info("size is {}. ", segments.length);
        segments = inputPath2.split("/");
        logger.info("size is {}. ", segments.length);

        long currentTimeMillis = System.currentTimeMillis();
        DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // Make sure using UTC to do parsing.
        String timeString = timeFormat.format(currentTimeMillis);

        List<String> finalDirectoryList = new ArrayList<>();
        finalDirectoryList.add("2004");
        finalDirectoryList.add("12");
        finalDirectoryList.add("15");
        finalDirectoryList.add("92");
        finalDirectoryList.add("2001");
        finalDirectoryList.add("test");
        finalDirectoryList.add("test02");
        finalDirectoryList.add("testha");
        finalDirectoryList.add("test01");
        finalDirectoryList.add("10");
        finalDirectoryList.forEach(d -> logger.info("value is {}. ", d));
        finalDirectoryList.sort(Comparator.naturalOrder());
        logger.info("===============================");
        finalDirectoryList.forEach(d -> logger.info("value is {}. ", d));
        logger.info("===============================");

        Date date01 = new Date(1627516800000L);
        System.out.println(date01);

        //        long t = System.currentTimeMillis();
        long t = 1627516800000L;
        // +8:00 time zone:
        SimpleDateFormat sdf_8 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        sdf_8.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        System.out.println("GMT+8:00 = " + sdf_8.format(t));

        // +7:00 time zone:
        SimpleDateFormat sdf_7 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        sdf_7.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
        System.out.println("GMT+7:00 = " + sdf_7.format(t));

        long t01 = 1627437719000L;
        long interval = 86400000;
        long mod = t01 % interval;
        boolean result = mod > (interval / 2);
        System.out.println(result);

        try {

            test();
        } catch (TopicExistsException topicExistsException) {
            System.out.println(topicExistsException.getMessage());
            //            return;
        } catch (Exception e) {
            throw new RuntimeException("hhh");
        }

        System.out.println("final haha. ");

    }

    private static void test() {
        throw new TopicExistsException("gg");
    }

}
