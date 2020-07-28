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
import com.fcjexample.demo.service.SignoffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@RestController
//@Validated
public class SignoffController {

    private static final Logger logger = LoggerFactory.getLogger(SignoffController.class);

    @Autowired
    Environment environment;

    @Autowired
    SignoffService signoffService;

    @PostMapping("/signoff/01")
    public String signoff01(@RequestBody TestEntity testEntity) {
        environment.getProperty("JAVA_HOME");
        System.getenv();
        System.getProperties();
        try {
            logger.info("start signoff 01...");
            signoffService.test();
        } catch (Exception e) {
            logger.error("signoff01 failed. ", e);
        }
        return "01" + testEntity.getName();
    }

    @PostMapping("/signoff/02/{tenant}/{topic}")
    public String computeSignoff02(@RequestBody @Valid TestEntity testEntity,
            @PathVariable("tenant") String tenant,
            @PathVariable("topic") String topic) {
        return "02" + testEntity.getName();
    }

    @PostMapping("/signoff/03/{tenant}")
    public String computeSignoff03(@RequestBody TestEntity testEntity,
            @PathVariable("tenant") String tenant,
            @RequestParam("num") @Min(10) int num) {
        return "02" + num + "+" + testEntity.getName();
    }

    @PostMapping("/signoff/04/{tenant}/{topic}")
    public String computeSignoff04(@RequestBody String testEntity,
            @PathVariable("tenant") String tenant,
            @PathVariable("topic") String topic) {
        return "02" + testEntity;
    }

    public void test() {

    }

    public static void main(String[] args) throws InterruptedException {
        // define capacity of LinkedBlockingQueue
        int capacity = 2;

        // create object of LinkedBlockingQueue
        // using LinkedBlockingQueue(int initialCapacity) constructor
        BlockingQueue<Integer> lbq
                = new ArrayBlockingQueue<>(capacity);

        // add  numbers
        //        lbq.add(1);
        //        lbq.add(2);
        //        lbq.add(3);
        lbq.offer(1);
        lbq.offer(2);
        lbq.offer(3);
        lbq.offer(4);
        lbq.poll();
        lbq.remove();
        lbq.poll(100, TimeUnit.SECONDS);

        // print queue
        System.out.println("ArrayBlockingQueue:"
                + lbq);

    }
}
