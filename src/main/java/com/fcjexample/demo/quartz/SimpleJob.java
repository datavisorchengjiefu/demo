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

package com.fcjexample.demo.quartz;

import com.fcjexample.demo.service.HelloService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.TimeUnit.SECONDS;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SimpleJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(SimpleJob.class);
    protected final ScheduledExecutorService cronJobScheduler = Executors.newScheduledThreadPool(1,
            new ThreadFactory() {
                @Override public Thread newThread(Runnable r) {
                    logger.info("create new Thread. [{}]", integer.getAndIncrement());
                    return new Thread(r, "batch-cronjob-check-replay-" + integer.get());
                }

                AtomicInteger integer = new AtomicInteger();

            });

    // work
    public SimpleJob() {
        logger.info("create a new simpleJob");
        //        hello = BeanUtil.getBean(HelloServiceImpl.class);
        //        this.hello = BeanUtil.getBean(HelloServiceImpl.class);
    }

    // not work
    //    public SimpleJob(HelloService hello) {
    //        //        this.hello = hello;
    //        logger.info("create a new simpleJob");
    //        this.hello = BeanUtil.getBean(HelloServiceImpl.class);
    //    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("========================");
        logger.info("This is a quartz job! {}. {} ", context.getJobDetail().getKey(), new Date());
        //        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        //        logger.info("execute tenant is  {}. ", TenantContext.getTenant());
        //        TenantContext.setTenant(dataMap.getString("tenantName"));
        //        logger.info("later execute tenant is  {}. ", TenantContext.getTenant());
        //
        //        String jobSays = dataMap.getString("jobSays");
        //        float myFloatValue = dataMap.getFloat("myFloatValue");
        //        logger.info("Job says: {}, and val is: {}", jobSays, myFloatValue);
        //        logger.info("JobDetail key is {}. ", context.getJobDetail().getKey());
        //        logger.info("execute simpleJob");
        //        logger.info("context ha 01 is {}", context.getJobDetail().getJobDataMap());
        //
        //        logger.info("context ha 02 is {}", dataMap.get("entityKey02"));
        //        TestEntity02 testEntity02 = (TestEntity02) dataMap.get("entityKey02");
        //
        //        logger.info("context ha 03 is {}", testEntity02.getTimeout());
        //        hello.sayHelloV2();

        long start = System.currentTimeMillis();
        cronJobScheduler.scheduleWithFixedDelay(new Runnable() {
            @Override public void run() {
                logger.info("cronJobScheduler run. ");
                logger.info("start task: {}. ", getPeriod(start, System.currentTimeMillis()));
                //                        sleep(SECONDS, 10);
                logger.info("end task: {}", getPeriod(start, System.currentTimeMillis()));
                if (getPeriod(start, System.currentTimeMillis()) > 9) {
                    logger.info("shutting down");
                    //                scheduledFuture.cancel(false);
                    cronJobScheduler.shutdown();
                }
                logger.info("hhh finish. ");
            }
        }, 1, 3, SECONDS);
        try {
            //            Thread.sleep(5000);
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            if (!cronJobScheduler.awaitTermination(Long.MAX_VALUE, SECONDS)) {
                cronJobScheduler.shutdown();
                logger.info("await timeout executor.shutdown(). ");
            }
        } catch (InterruptedException e) {
            logger.error("ERROR ha. ", e);
            cronJobScheduler.shutdownNow();
        }

        logger.info("quartz job finish. {}. ", context.getJobDetail().getKey());
    }

    private static int getPeriod(long start, long end) {
        return (int) (end - start) / 1000;
    }

    private HelloService hello;
}