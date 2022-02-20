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

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;

public class Demo01 {
    private static final Logger logger = LoggerFactory.getLogger(Demo01.class);

    public static void main(String[] args) {

        ThreadFactory threadFactory = new ThreadFactory() {
            int i = 0;

            @Override public Thread newThread(Runnable r) {
                return new Thread(r, "simpleThread" + String.valueOf(i++));
            }
        };

        EventFactory<DemoElement> factory = new EventFactory<DemoElement>() {
            @Override public DemoElement newInstance() {
                return new DemoElement();
            }
        };

        BlockingWaitStrategy strategy = new BlockingWaitStrategy();

        // 会有影响，对于生产者
        int bufferSize = 4;
        //        int bufferSize = 8;
        //        int bufferSize = 16;

        Disruptor<DemoElement> disruptor = new Disruptor<>(factory, bufferSize, threadFactory,
                ProducerType.SINGLE, strategy);

        //
        LinkedBlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>(1000);

        // 设置EventHandler
        disruptor.handleEventsWith(new DemoEventHandler(blockingQueue));

        disruptor.start();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            disruptor.publishEvent(new EventTranslatorOneArg<DemoElement, Integer>() {
                @Override public void translateTo(DemoElement event, long sequence, Integer arg0) {
                    System.out.println(
                            "之前的数据" + event.getValue() + ", 当前的sequence" + sequence + ", 当前的arg是"
                                    + arg0);
                    //                    event.setValue("我是第" + sequence + "个");
                    event.setValue("我是第" + sequence + "个. " + "我要设置的值为" + arg0);
                }
            }, i);
            logger.info("after publish time is {}", System.currentTimeMillis() - start);
        }
        logger.info("Main thread blockingQueue size is {}", blockingQueue.size());

    }
}
