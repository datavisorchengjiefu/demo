/*************************************************************************
 *
 * Copyright (c) 2023, DATAVISOR, INC.
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateRangeExample {

    private static final Logger logger = LoggerFactory.getLogger(DateRangeExample.class);

    public static void main(String[] args) {
        //
        //        test01();
        //        test02();
        //        test03();
        test04();

    }

    public static void test01() {
        // Define the date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Get the last week's range
        LocalDate lastWeekStartDate = currentDate.minusDays(7).plusDays(1);
        LocalDate lastWeekEndDate = currentDate.minusDays(1);
        String lastWeekRange =
                lastWeekStartDate.format(formatter) + " - " + lastWeekEndDate.format(formatter);
        System.out.println("Last week's range: " + lastWeekRange);

        // Get the previous week's range
        LocalDate previousWeekStartDate = lastWeekStartDate.minusDays(7);
        LocalDate previousWeekEndDate = lastWeekEndDate.minusDays(7);
        String previousWeekRange =
                previousWeekStartDate.format(formatter) + " - " + previousWeekEndDate
                        .format(formatter);
        System.out.println("Previous week's range: " + previousWeekRange);
    }

    public static void test02() {

        // Define the date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Get the last week's range
        LocalDate lastSunday = currentDate.with(DayOfWeek.SUNDAY).minusDays(7);
        LocalDate lastMonday = currentDate.with(DayOfWeek.MONDAY).minusDays(7);
        String lastWeekRange = lastSunday.format(formatter) + " - " + lastMonday.format(formatter);
        System.out.println("Last week's range: " + lastWeekRange);

        // Get the previous week's range
        LocalDate previousSunday = lastSunday.minusDays(7);
        LocalDate previousMonday = lastMonday.minusDays(7);
        String previousWeekRange =
                previousSunday.format(formatter) + " - " + previousMonday.format(formatter);
        System.out.println("Previous week's range: " + previousWeekRange);
    }

    public static void test03() {
        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 获取当前日期
        LocalDate currentDate = LocalDate.now();

        // 获取上个月的第一天和最后一天
        LocalDate firstDayOfLastMonth = currentDate.minusMonths(1).withDayOfMonth(1);
        LocalDate lastDayOfLastMonth = currentDate.minusMonths(1).withDayOfMonth(
                currentDate.minusMonths(1).lengthOfMonth());

        // 格式化日期范围字符串
        String lastMonthRange = firstDayOfLastMonth.format(formatter) + " - " + lastDayOfLastMonth
                .format(formatter);
        System.out.println("上个月的时间范围为: " + lastMonthRange);
    }

    public static void test04() {
        // 定义日期格式
        //        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 获取当前日期
        LocalDateTime localDateTime = LocalDateTime.now();

        // todo: LocalDateTime 无法使用lengthOfMonth
        LocalDateTime lastDayOfLastMonth = localDateTime.minusMonths(1).withDayOfMonth(
                localDateTime.minusMonths(1).getDayOfMonth());

        // LocalDate.now() also use ZoneId.systemDefault()
        LocalDate currentDate = LocalDate.now();
        ZonedDateTime dateTime = currentDate.atStartOfDay(ZoneId.systemDefault());
        long milliseconds = dateTime.toInstant().toEpochMilli();
        System.out.println("Milliseconds since epoch: " + milliseconds);

        int amount = 1;
        LocalDate firstDayOfSomeWeek = currentDate.minusWeeks(amount)
                .with(DayOfWeek.MONDAY);
        LocalDate lastDayOfSomeWeek = currentDate.minusWeeks(amount)
                .with(DayOfWeek.SUNDAY);
        logger.info("firstDayOfSomeWeek is {}", firstDayOfSomeWeek.format(formatter));
        logger.info("firstDayOfSomeWeek is {}", getStartMilli(firstDayOfSomeWeek));
        logger.info("lastDayOfSomeWeek is {}", lastDayOfSomeWeek.format(formatter));
        logger.info("lastDayOfSomeWeek is {}", getEndMilli(lastDayOfSomeWeek));

        //
        LocalDate firstDay = currentDate.minusDays(7);
        logger.info("firstDay is {}", firstDay.format(formatter));
        LocalDate lastDay = currentDate.minusDays(1);
        logger.info("lastDay is {}", lastDay.format(formatter));

        logger.info("=========");

        currentDate = LocalDate.of(2023, 4, 1);
        //        currentDate.isAfter()
        firstDay = currentDate.withDayOfMonth(1);
        logger.info("firstDay is {}", firstDay.format(formatter));
        lastDay = currentDate.minusDays(1);
        logger.info("lastDay is {}", lastDay.format(formatter));

    }

    private static long getStartMilli(LocalDate localDate) {
        ZonedDateTime dateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        return dateTime.toInstant().toEpochMilli();
    }

    private static long getEndMilli(LocalDate localDate) {
        ZonedDateTime dateTime = localDate.atStartOfDay().with(LocalTime.MAX)
                .atZone(ZoneId.systemDefault());
        return dateTime.toInstant().toEpochMilli();
    }
}
