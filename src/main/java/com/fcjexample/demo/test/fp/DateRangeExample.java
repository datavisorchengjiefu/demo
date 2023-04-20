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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateRangeExample {

    public static void main(String[] args) {
        //
        //        test01();
        //        test02();
        test03();

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
}
