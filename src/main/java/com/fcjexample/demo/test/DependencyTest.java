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

package com.fcjexample.demo.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DependencyTest {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer integer = iterator.next();
            if (integer == 2) {
                //                list.add(5);
                iterator.remove();
            }
        }

        List<Integer> toRemove = new ArrayList<>();
        List<Integer> toAdd = new ArrayList<>();
        for (Integer integer : list) {
            if (integer == 2) {
                toAdd.add(5);
                toRemove.add(integer);
            }
        }

        list.addAll(toAdd);
        list.removeAll(toRemove);

        System.out.println(list.size());

        Long l = 54L;
        int num = 4;
        Long u = (long) num;
        System.out.println(l < num);

        System.out.println(num + l);
    }
}
