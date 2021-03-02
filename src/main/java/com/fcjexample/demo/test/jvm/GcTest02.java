/*************************************************************************
 *
 * Copyright (c) 2021, DATAVISOR, INC.
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

package com.fcjexample.demo.test.jvm;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GcTest02 {
    public static void main(String[] args) {
        // 每100毫秒创建100线程，每个线程创建一个1M的对象，即每100ms申请100M堆空间
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            for (int i = 0; i < 100; i++) {
                new Thread(() -> {
                    try {
                        //  申请1M
                        byte[] temp = new byte[1024 * 1024];
                        Thread.sleep(new Random().nextInt(1000)); // 随机睡眠1秒以内
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }, 1000, 100, TimeUnit.MILLISECONDS);
    }

}
