/*************************************************************************
 *
 * Copyright (c) 2021, DATAVISOR, INC.
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

package com.fcjexample.demo.test.basic;

import java.util.HashSet;
import java.util.Set;

public class TestEquals {
    public static void main(String[] args) {
        EqualsEntity equalsEntity01 = new EqualsEntity();
        equalsEntity01.setId(1);
        equalsEntity01.setName("user01");
        EqualsEntity equalsEntity02 = new EqualsEntity();
        equalsEntity02.setId(2);
        equalsEntity02.setName("user01");

        Set<EqualsEntity> set = new HashSet<>();
        set.add(equalsEntity01);
        set.add(equalsEntity01);
        set.add(equalsEntity02);

        System.out.println(set.size());

    }

}
