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

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ConsumerTest implements Runnable {
    private int number;
    //    private BlockingQueue<String> queue;
    private Queue<String> queue;
    private int count = 0;
    private AtomicInteger integer = new AtomicInteger(0);

    public ConsumerTest(int number, BlockingQueue<String> queue) {
        this.number = number;
        this.queue = queue;
    }

    //    public ConsumerTest(int number, Queue<String> queue) {
    //        this.number = number;
    //        this.queue = queue;
    //    }

    @Override public void run() {
        //        while (count < 500) {
        //            try {
        //                Thread.sleep(10);
        //            } catch (InterruptedException e) {
        //                e.printStackTrace();
        //            }
        //            count++;
        //            queue.poll();
        //        }

        while (queue.poll() != null) {
            integer.addAndGet(1);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("integer" + integer);
        System.out.println(queue.size());
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("aa");
        list.add(0, "bb");

        System.out.println(list.size());
        System.out.println(list.get(0));
        System.out.println(list.get(1));
    }
}
