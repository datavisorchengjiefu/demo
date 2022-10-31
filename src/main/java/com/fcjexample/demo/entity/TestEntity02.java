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

package com.fcjexample.demo.entity;

public class TestEntity02 {
    private Long timeout;
    private String name;
    private String address;
    private long height;

    public TestEntity02() {
    }

    public TestEntity02(Long timeout, String name, String address) {
        this.timeout = timeout;
        this.name = name;
        this.address = address;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    @Override public String toString() {
        return "TestEntity02{" +
                "timeout=" + timeout +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", height=" + height +
                '}';
    }
}
