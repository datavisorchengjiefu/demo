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

package com.fcjexample.demo.controller;

import com.fcjexample.demo.model.TestEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignoffController {

    @PostMapping("/signoff/01")
    public String signoff01(@RequestBody TestEntity testEntity) {
        return "01" + testEntity.getName();
    }

    @PostMapping("/signoff/02")
    public String signoff02(@RequestBody TestEntity testEntity) {
        System.out.println("test");
        System.out.println("test02");
        return "02" + testEntity.getName();
    }

    public void test() {

    }

    public void test01() {

    }
}
