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

package com.fcjexample.demo.test.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Test02 {
    public static void main(String[] args) {
        //每1s产生0.5个令牌，也就是说该接口2s只允许调用1次
        RateLimiter rateLimiter = RateLimiter.create(0.5, 1, TimeUnit.SECONDS);
        ExecutorService executor = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 10; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    //获取令牌桶中一个令牌，最多等待10秒
                    if (rateLimiter.tryAcquire(1, 10, TimeUnit.SECONDS)) {
                        System.out.println(
                                Thread.currentThread().getName() + " " + new SimpleDateFormat(
                                        "yyyy-MM-dd HH:mm:ss").format(new Date()));
                    } else {
                        System.out.println("请求频繁");
                    }
                }
            });
        }

        executor.shutdown();

    }
}
