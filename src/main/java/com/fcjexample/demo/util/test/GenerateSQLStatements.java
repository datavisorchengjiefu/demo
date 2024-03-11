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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneOffset;

public class GenerateSQLStatements {
    public static void main(String[] args) {
        int year = 2023;

        // 遍历每个月
        for (int month = 1; month <= 12; month++) {
            YearMonth yearMonth = YearMonth.of(year, month);

            // 获取每个月的第一天和最后一天
            LocalDate firstDayOfMonth = yearMonth.atDay(1);
            LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();

            // 将日期转换为毫秒时间戳
            long startOfMonthTimestamp = firstDayOfMonth.atStartOfDay().toInstant(ZoneOffset.UTC)
                    .toEpochMilli();
            long endOfMonthTimestamp = LocalDateTime.of(lastDayOfMonth,
                    LocalDateTime.MAX.toLocalTime()).toInstant(ZoneOffset.UTC).toEpochMilli();

            // 打印结果
            String sql = "SELECT COUNT(1) FROM event_result WHERE timeInserted >= '"
                    + startOfMonthTimestamp + "' AND timeInserted <= '" + endOfMonthTimestamp
                    + "' AND ekata_email_first_seen_days IS NOT NULL;";

            System.out.println(sql);
        }
    }
}


