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
import com.google.common.collect.EvictingQueue;
import com.google.common.collect.Queues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

public class SampleConsumerOutter implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(SampleConsumerOutter.class);

    //    private final int consumeCapacity = 1000;
    private final int consumeCapacity = 3;
    private final long consumeMinute = 20;
    //    private final long consumeMinute = 3;
    private BlockingQueue<TestEntity> queue;

    public SampleConsumerOutter(BlockingQueue<TestEntity> queue) {
        this.queue = queue;
    }

    @Override public void run() {
        List<TestEntity> data = new ArrayList<>();
        data.add(new TestEntity());
        data.add(null);
        data.add(null);

        System.out.println("haha size is " + data.size());
        try {
            //每次取出的数据存放到data里
            //每次到1000条数据才进行入库，等待1分钟，没达到2000条也继续入库
            long start = System.currentTimeMillis();
            logger.info("start drain. ");
            logger.info("queue size: {}", queue.size());
            Queues.drain(queue, data, consumeCapacity, consumeMinute, TimeUnit.SECONDS);
            logger.info("end drain. ");
            long end = System.currentTimeMillis();
            logger.info("drain cost time: " + (end - start) / 1000);
            writeSignoffReplaySampleToS3(data);

            queue.add(new TestEntity("name5", "desc5"));
            logger.info("queue size: {}", queue.size());
            queue.stream().forEach(t -> System.out.println(t.getName()));
            logger.info("=========");
            //            logger.info("q: {}", queue.getFirst());
            //            logger.info("q: {}", queue.getLast());

        } catch (Exception e) {
            logger.error("SampleConsumer failed. ", e);
        }
    }

    public static void main(String[] args) {
        //        BlockingQueue<TestEntity> blockingQueue = new ArrayBlockingQueue<>(20);
        //        LinkedBlockingQueue<TestEntity> blockingQueue = new LinkedBlockingQueue<>(20);
        // todo 超过size会报错重要!!!
        LinkedBlockingQueue<TestEntity> blockingQueue = new LinkedBlockingQueue<>(2);
        //        blockingQueue.add(new TestEntity("name1", "desc1"));
        //        blockingQueue.add(new TestEntity("name2", "desc2"));
        //        blockingQueue.add(new TestEntity("name3", "desc3"));// add will throw exception
        //        blockingQueue.add(new TestEntity("name4", "desc4"));

        blockingQueue.offer(new TestEntity("name1", "desc1"));
        blockingQueue.offer(new TestEntity("name2", "desc2"));
        blockingQueue.offer(new TestEntity("name3", "desc3"));// offer will not throw exception
        blockingQueue.offer(new TestEntity("name4", "desc4"));
        logger.info("size is {}", blockingQueue.size());

        List<TestEntity> result = new ArrayList<>();
        blockingQueue.drainTo(result, 2);

        //        Queue<Integer> fifo = EvictingQueue.create(100);
        Queue<Integer> fifo = EvictingQueue.create(2);
        fifo.offer(1);
        fifo.offer(2);
        fifo.offer(3);
        final int[] count = { 0 };
        Thread thread = new Thread(new Runnable() {
            @Override public void run() {
                while (count[0] < 10) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    fifo.add(1);
                    fifo.stream().forEach(f -> logger.info("f is {} ", f));
                    logger.info("=====");

                    count[0]++;
                }

            }
        });
        thread.start();

        logger.info("size is {}", fifo.size());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Integer> list = new ArrayList<>(fifo);
        //        List<Integer> list = new ArrayList<>(1);
        for (Integer i : list) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            logger.info("i is ha {}", i);
        }

        SampleConsumerOutter sampleConsumerOutter = new SampleConsumerOutter(blockingQueue);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(sampleConsumerOutter);
        executorService.shutdown();

    }

    private void writeSignoffReplaySampleToS3(List<TestEntity> data)
            throws IOException {

        System.out.println(data.get(0));
        System.out.println(data.get(1));
        System.out.println("list size is: " + data.size());

    }
}
