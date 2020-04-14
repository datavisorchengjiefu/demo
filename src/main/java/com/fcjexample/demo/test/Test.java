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

import com.fcjexample.demo.model.TestEntity;

import java.util.HashSet;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        System.out.println("hhh");

        TestEntity entity1 = new TestEntity();
        entity1.setName("h1");
        TestEntity entity2 = new TestEntity();
        entity2.setName("h2");
        TestEntity entity3 = new TestEntity();
        entity3.setName("h3");

        Set<TestEntity> set = new HashSet<>();
        set.add(entity1);
        set.add(entity2);

        System.out.println(set.size());

        System.out.println(set.remove(entity2));
        System.out.println(set.size());

        System.out.println(set.remove(entity3));
        System.out.println(set.size());

        TestEntity entity4 = new TestEntity();
        entity4.setName("h4");

        Set<TestEntity> set1 = set;
        // 注意下面这个会同时影响set1和set
        set1.add(new TestEntity());

        Set<TestEntity> set2 = new HashSet<>(set);
        //        Set<TestEntity> set2 = new HashSet<>();
        //        set2.addAll(set);
        // 但下面这个只是会影响set2，不会影响
        set2.add(new TestEntity());
        System.out.println(set2.size());

        char a = 'A';
        String s = "gge4";
        System.out.println(s.getBytes().length);

    }
}
