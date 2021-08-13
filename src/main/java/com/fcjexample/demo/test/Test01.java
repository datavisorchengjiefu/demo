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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Test01 {
    public static void main(String[] args) {
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
        } catch (Exception e) {
            throw new RuntimeException("hhh");
        }

    }

    private static void test() {
        throw new TopicExistsException("gg");
    }

}
