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
import java.util.concurrent.*;

public class Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(4);
        long start = System.currentTimeMillis();
        List<Future> list = new ArrayList<>();
        //        ConsumerTest consumerTest = new ConsumerTest();
        //        Queue<String> queue = new LinkedList<>();
        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10000);
        for (int i = 0; i < 500; i++) {
            queue.add("test" + i);
        }
        ConsumerTest consumerTest1 = new ConsumerTest(0, queue);
        ConsumerTest consumerTest2 = new ConsumerTest(0, queue);
        list.add(pool.submit(consumerTest1));
        list.add(pool.submit(consumerTest2));
        //        list.add(pool.submit(new ConsumerTest(0)));
        //        list.add(pool.submit(new ConsumerTest(0)));
        for (Future future : list) {
            future.get();
            System.out.println("future isdone");
        }

        System.out.println("queue size: " + queue.size());
        long end = System.currentTimeMillis();
        System.out.println(end);

        System.out.println("time: " + (end - start));
    }
}
