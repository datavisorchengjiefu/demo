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

package com.fcjexample.demo.test.generic;

import com.fcjexample.demo.service.HelloService;
import com.fcjexample.demo.service.Impl.DataViewServiceImpl;
import com.fcjexample.demo.service.Impl.HelloServiceImpl;
import javafx.util.Pair;
import org.hamcrest.Matchers;
import org.junit.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

//import static org.hamcrest.Matchers.hasItems;

public class GenericTest {
    public static void main(String[] args) {

        GenericClassFcj<Integer> genericClassFcj = new GenericClassFcj<>();
        GenericClassFcj02 genericClassFcj02 = new GenericClassFcj02();

        Integer[] integers = new Integer[10];
        String[] strings = new String[5];
        fromArrayToListNumber(integers);
        //        fromArrayToListNumber(strings); // wrong

        HelloServiceImpl[] helloService = new HelloServiceImpl[5];
        DataViewServiceImpl[] dataViewService = new DataViewServiceImpl[4];
        fromArrayToListInterface(helloService);
        //        fromArrayToListInterface(dataViewService);// wrong

    }

    static class Box<T> {

        private T t;

        public void set(T t) {
            this.t = t;
        }

        public T get() {
            return t;
        }

        public <U extends Number> void inspect(U u) {
            System.out.println("T: " + t.getClass().getName());
            System.out.println("U: " + u.getClass().getName());
        }

        public static void main(String[] args) {
            Box<Integer> integerBox = new Box<Integer>();
            integerBox.set(new Integer(10));
            //            integerBox.inspect("some text"); // error: this is still String!
            integerBox.inspect(new Long(25));
        }
    }

    public <T> List<T> fromArrayToList(T[] a) {
        return Arrays.stream(a).collect(Collectors.toList());
    }

    public static <T, G> List<G> fromArrayToList(T[] a, Function<T, G> mapperFunction) {
        return Arrays.stream(a)
                .map(mapperFunction)
                .collect(Collectors.toList());
    }

    public static <T extends Number> T fromArrayToListNumber(T[] a) {
        return a[2];
    }

    public static <T extends HelloService> T fromArrayToListInterface(T[] a) {
        return a[2];
    }

    public static List<? extends Number> paintAllBuildings(List<? extends Number> buildings) {
        return buildings;
    }

    public static Number paintOneBuildingNumber(List<? extends Number> buildings) {
        return buildings.get(1);
    }

    public static <T> T paintOneBuilding(List<? extends T> buildings) {
        return buildings.get(1);
    }

    public <T extends Number> void genericMethod(T t) {

    }

    @org.junit.Test
    public void givenArrayOfIntegers_thanListOfStringReturnedOK() {
        Integer[] intArray = { 1, 2, 3, 4, 5 };
        //        List<String> stringList
        //                = GenericTest.fromArrayToList(intArray, String::valueOf);
        List<String> stringList
                = GenericTest.fromArrayToList(intArray, Object::toString);

        Assert.assertThat(stringList, Matchers.hasItems("1", "2", "3", "4", "5"));
    }

    static class GenericClassFcj<S> {

        private S test;

        public S getTest() {
            return test;
        }

        public void setTest(S test) {
            this.test = test;
        }

        public <T> T getEntity(T t) {
            return t;
        }

        public static <K, V> boolean compare(Pair<K, V> p1, Pair<K, V> p2) {
            return p1.getKey().equals(p2.getKey()) &&
                    p1.getValue().equals(p2.getValue());
        }
    }

    static class GenericClassFcj02 {

        public static <K, V> boolean compare(Pair<K, V> p1, Pair<K, V> p2) {
            return p1.getKey().equals(p2.getKey()) &&
                    p1.getValue().equals(p2.getValue());
        }
    }
}
