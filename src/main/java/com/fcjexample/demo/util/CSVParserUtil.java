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

package com.fcjexample.demo.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CSVParserUtil {

    public static List<String> getCSVRecordByLineAndDelimiter(String line, char delimiter)
            throws IOException {
        CSVParser csvParser = CSVParser.parse(line, formatForDelimiter(delimiter));
        CSVRecord record = csvParser.getRecords().get(0);
        List<String> ret = new ArrayList<>();
        record.iterator().forEachRemaining(ret::add);
        return ret;
    }

    // If the delimiter is not comma, we'll create a new format without any escaping, to support
    // out /u0001 case.
    private static CSVFormat formatForDelimiter(char delimiter) {
        CSVFormat format = CSVFormat.DEFAULT;
        if (delimiter != format.getDelimiter()) {
            return CSVFormat.newFormat(delimiter);
        }
        return format;
    }

    public static List<String> getCSVRecordByLineAndDelimiter(String line, String delimiter)
            throws IOException {
        if (delimiter.length() == 1) {
            return getCSVRecordByLineAndDelimiter(line, delimiter.charAt(0));
        }
        return Arrays.asList(line.split(Pattern.quote(delimiter), -1));
    }

    // We generally assume each line in the CSV file is one record, so we need to explicitly escape
    // the newLine character, such that in the output file, one event is always in one line.
    public static String escapeCsv(String line) {
        if (line == null) {
            return StringUtils.EMPTY;
        }
        return StringEscapeUtils.escapeCsv(line.replace("\n", "\\n"));
    }
}
