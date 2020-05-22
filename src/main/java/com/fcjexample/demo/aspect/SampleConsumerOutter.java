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

package com.fcjexample.demo.aspect;

import com.fcjexample.demo.model.TestEntity;
import com.google.common.collect.Queues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class SampleConsumerOutter implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(SampleConsumerOutter.class);

    private final int consumeCapacity = 2000;
    private final long consumeMinute = 10;
    private BlockingQueue<TestEntity> queue;

    public SampleConsumerOutter(BlockingQueue<TestEntity> queue) {
        this.queue = queue;
    }

    @Override public void run() {
        List<TestEntity> data = new ArrayList<>();
        try {
            //每次取出的数据存放到data里
            //每次到1000条数据才进行入库，等待1分钟，没达到2000条也继续入库
            long start = System.currentTimeMillis();
            logger.info("start drain. ");
            Queues.drain(queue, data, consumeCapacity, consumeMinute, TimeUnit.SECONDS);
            logger.info("end drain. ");
            long end = System.currentTimeMillis();
            logger.info("drain cost time: " + (end - start) / 1000);

            writeSignoffReplaySampleToS3(data);
        } catch (Exception e) {
            logger.error("SampleConsumer failed. ", e);
        }
    }

    private void writeSignoffReplaySampleToS3(List<TestEntity> data)
            throws IOException {

        System.out.println(data.get(0));
        System.out.println(data.get(1));
        System.out.println("list size is: " + data.size());

    }
}
