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

package com.fcjexample.demo.test.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LambdaTest {
    public static void main(String[] args) {
        Stream<String> nameStream = Stream.of("Alice", "Bob", "Chuck");
        nameStream.peek(System.out::println);

        System.out.println("++++++++++++++++");
        Stream<String> nameStream1 = Stream.of("Alice", "Bob", "Chuck");
        nameStream1.forEach(System.out::println);

        System.out.println("++++++++++++++++++");
        List<String> list = Stream.of("one", "two", "three", "four")
                .filter(e -> e.length() > 3)
                .peek(e -> System.out.println("Filtered value: " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e))
                .collect(Collectors.toList());

        System.out.println(list);

        //        Stream<String> userStream = Stream.of("Alice", "Bob", "Chuck");
        //        userStream.peek(u -> u.toLowerCase())
        //                .forEach(System.out::println);

        System.out.println("++++++++++++++");
        List<User> userList = Arrays.asList(new User("Alice"), new User("Bob"), new User("Chuck"));
        userList.stream()
                .peek(u -> u.setName(u.getName().toLowerCase()))
                .peek(u -> u.setName(u.getName().toUpperCase()))
                .forEach(System.out::println);
        userList.forEach(new Consumer<User>() {
            @Override public void accept(User user) {
                System.out.println("name is: " + user.getName());
            }
        });

        System.out.println("++++++++++++++");
        Stream<User> userStream = Stream.of(new User("Alice"), new User("Bob"), new User("Chuck"));
        userStream.peek(u -> u.setName(u.getName().toLowerCase()))
                .forEach(System.out::println);
        userStream.forEach(new Consumer<User>() {
            @Override public void accept(User user) {
                System.out.println("name is: " + user.getName());
            }
        });
    }

    static class User {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public User(String name) {
            this.name = name;
        }

        @Override public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
