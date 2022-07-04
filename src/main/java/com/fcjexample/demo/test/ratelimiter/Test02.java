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
    public static void main(String[] args) throws Exception {

        //每1s产生0.5个令牌，也就是说该接口2s只允许调用1次
        RateLimiter rateLimiter = RateLimiter.create(0.5, 1, TimeUnit.SECONDS);
        ExecutorService executor = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 10; i++) {
            // a.
            //            executor.execute(new Runnable() {
            //                @Override
            //                public void run() {
            //                    // 1.
            //                    //获取令牌桶中一个令牌，最多等待10秒
            //                    if (rateLimiter.tryAcquire(1, 10, TimeUnit.SECONDS)) {
            //                        System.out.println(
            //                                Thread.currentThread().getName() + " " + new SimpleDateFormat(
            //                                        "yyyy-MM-dd HH:mm:ss").format(new Date()));
            //                    } else {
            //                        System.out.println("请求频繁");
            //                    }
            //
            //                    // 2.
            //                    rateLimiter.acquire();
            //                    System.out.println(
            //                            Thread.currentThread().getName() + " " + new SimpleDateFormat(
            //                                    "yyyy-MM-dd HH:mm:ss").format(new Date()));
            //                }
            //            });

            // b.
            // 1.
            //获取令牌桶中一个令牌，最多等待10秒
            //            if (rateLimiter.tryAcquire(1, 10, TimeUnit.SECONDS)) {
            if (rateLimiter.tryAcquire(1, 10, TimeUnit.MILLISECONDS)) {
                //                Thread.sleep(3000);
                System.out.println(
                        Thread.currentThread().getName() + " " + new SimpleDateFormat(
                                "yyyy-MM-dd HH:mm:ss").format(new Date()));
            } else {
                System.out.println("请求频繁");
            }

            // 2.
            //            rateLimiter.acquire();
            //            System.out.println(
            //                    Thread.currentThread().getName() + " " + new SimpleDateFormat(
            //                            "yyyy-MM-dd HH:mm:ss").format(new Date()));

            // c.
            //获取令牌桶中一个令牌，最多等待10秒
            //            if (rateLimiter.tryAcquire(1, 3, TimeUnit.SECONDS)) {
            //                Thread.sleep(4000);
            //                System.out.println(
            //                        Thread.currentThread().getName() + " " + new SimpleDateFormat(
            //                                "yyyy-MM-dd HH:mm:ss").format(new Date()));
            //            } else {
            //                System.out.println("请求频繁");
            //            }

            // 2.
            //            rateLimiter.acquire();
            //            Thread.sleep(4000);
            //            System.out.println(
            //                    Thread.currentThread().getName() + " " + new SimpleDateFormat(
            //                            "yyyy-MM-dd HH:mm:ss").format(new Date()));

        }

        executor.shutdown();

        /**
         // d.
         RateLimiter rateLimiter = RateLimiter.create(1);

         // when
         double d1 = rateLimiter.acquire();
         System.out.println(d1);
         System.out.println(
         Thread.currentThread().getName() + " " + new SimpleDateFormat(
         "yyyy-MM-dd HH:mm:ss").format(new Date()));

         double d2 = rateLimiter.acquire();
         System.out.println(d2);
         System.out.println(
         Thread.currentThread().getName() + " " + new SimpleDateFormat(
         "yyyy-MM-dd HH:mm:ss").format(new Date()));

         boolean result = rateLimiter.tryAcquire(2, 10, TimeUnit.MILLISECONDS);
         boolean result2 = rateLimiter.tryAcquire(3, 1, TimeUnit.SECONDS);

         System.out.println("test");
         System.out.println(result);
         System.out.println(result2);

         */

    }
}
