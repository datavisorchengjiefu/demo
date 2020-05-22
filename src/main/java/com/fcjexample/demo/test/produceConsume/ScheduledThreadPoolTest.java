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

package com.fcjexample.demo.test.produceConsume;

import com.google.common.collect.Queues;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class ScheduledThreadPoolTest {

    private static BlockingQueue<String> sampleQueue = new ArrayBlockingQueue<>(100);

    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(4);

        for (int i = 1; i < 80; i++) {
            sampleQueue.offer("value" + i);
        }

        System.out.println("Current Time = " + new Date());
        for (int i = 0; i < 1; i++) {
            Thread.sleep(1000);
            WorkerThread worker = new WorkerThread("do heavy processing", sampleQueue);
            //scheduledThreadPool.schedule(worker, 3, TimeUnit.SECONDS);
            //scheduledThreadPool.scheduleAtFixedRate(worker, 5, 5, TimeUnit.SECONDS);
            scheduledThreadPool.scheduleWithFixedDelay(worker, 1, 3, TimeUnit.SECONDS);
        }
        //        Thread.sleep(300000);
        //
        //        scheduledThreadPool.shutdown();
        //        while (!scheduledThreadPool.isTerminated()) {
        //        }
        //        System.out.println("Finished all threads");
    }

    static class WorkerThread implements Runnable {

        private String command;
        private BlockingQueue<String> queue;

        public WorkerThread(String command, BlockingQueue<String> queue) {
            this.command = command;
            this.queue = queue;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " Start. Time = " + new Date());
            //            processCommand();
            List<String> list = new ArrayList<>();
            try {
                Queues.drain(queue, list, 20, 10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + " End. Time = " + new Date());
            System.out.println("list size" + list.size());
            System.out.println("queue size: " + queue.size());

        }

        private void processCommand() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return this.command;
        }
    }
}
