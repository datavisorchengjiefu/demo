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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TtlUtils {
    public static final Integer DEFAULT_TTL = 2592000;
    public static final Integer DEFAULT_NUMBER_TTL = 5;
    public static final Integer NUMBER_FOR_HOUR = 5;
    private static final Map<Long, Integer> windowTTLs = initWindowTTLs();
    public static final Integer NUMBER_FOR_DAY = 5;


    private static Map<Long, Integer> initWindowTTLs() {
        Map<Long, Integer> intervalTTLs = new HashMap<>();
        intervalTTLs.put(100L,
                20 * NUMBER_FOR_HOUR);

        return Collections.unmodifiableMap(intervalTTLs);
    }

    public static void main(String[] args) {
        System.out.println("555");
    }


}
