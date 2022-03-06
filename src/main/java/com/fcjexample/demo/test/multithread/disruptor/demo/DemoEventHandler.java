/*************************************************************************
 *
 * Copyright (c) 2022, DATAVISOR, INC.
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

package com.fcjexample.demo.test.multithread.disruptor.demo;

import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;

public class DemoEventHandler implements EventHandler<DemoElement> {
    private static final Logger logger = LoggerFactory.getLogger(DemoEventHandler.class);
    LinkedBlockingQueue<String> blockingQueue;

    public DemoEventHandler(LinkedBlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public DemoEventHandler() {
    }

    @Override public void onEvent(DemoElement event, long sequence, boolean endOfBatch)
            throws Exception {
        try {
            Thread.sleep(2000);
            System.out.println(
                    "DemoElement: " + Thread.currentThread().getName() + ": " + event
                            .getValue()
                            + ": "
                            + sequence);
            if ("2".equals(event.getValue())) {
                throw new RuntimeException("there is exception. ");
            }

            blockingQueue.offer(event.getValue());
            logger.info("blockingQueue size is {}", blockingQueue.size());
            Thread.sleep(1000);

        } catch (Exception e) {
            logger.error("onEvent error. ", e);
        }

    }
}
