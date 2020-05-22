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
import com.google.common.collect.Queues;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A feature metrics aspect to record feature value to meter registry
 */
@Aspect
@Component
public class SignoffAspect {
    private static final Logger logger = LoggerFactory.getLogger(SignoffAspect.class);
    private static final int capacity = 10000;
    private BlockingQueue<TestEntity> sampleQueue = new ArrayBlockingQueue<TestEntity>(capacity);

    private ScheduledExecutorService executorService;

    //    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) throws Exception {
        executorService = Executors.newScheduledThreadPool(4,
                new ThreadFactory() {
                    AtomicInteger integer = new AtomicInteger();

                    @Override public Thread newThread(Runnable r) {
                        // 注意下面这个Runnable的r不要忘记加了！！！
                        return new Thread(r, "signoffSample-consumerfcj-" + integer);
                    }
                });

        logger.info("SampleConsumer starting...");

        executorService
                //                .scheduleWithFixedDelay(new SampleConsumer(sampleQueue), 5, 30, TimeUnit.MINUTES);
                .scheduleWithFixedDelay(new SampleConsumer(sampleQueue), 1, 3, TimeUnit.SECONDS);
        //                .scheduleWithFixedDelay(new SampleConsumerOutter(sampleQueue), 1, 3,
        //        TimeUnit.SECONDS);

        logger.info("SampleConsumer started");

    }

    @PreDestroy
    public void after() {
        if (executorService != null) {
            executorService.shutdown();
            logger.info("shutdown executorService. ");
        }
    }

    /**
     * Advice feature metrics when a method is entered and exited.
     *
     * @param joinPoint join point for advice
     * @return result
     * @throws Throwable throws IllegalArgumentException
     */
    @Before("applicationPackagePointcut() && featureControllerPointcut() && "
            + " computeFeaturesPointcut()")
    @SuppressWarnings("unchecked")
    public void metricsAround(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        //        for (Object o : args) {
        //            if (o instanceof TestEntity) {
        //                TestEntity testEntity = (TestEntity) o;
        //                sampleQueue.offer(testEntity);
        //            }
        //        }
        //
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        String[] parameterNames = methodSignature.getParameterNames();
        int testEntityIndex = ArrayUtils.indexOf(parameterNames, "testEntity");
        sampleQueue.offer((TestEntity) (args[testEntityIndex]));
        int tenantIndex = ArrayUtils.indexOf(parameterNames, "tenant");
        //        if (tenantIndex == -1) {
        //            throw new RuntimeException("tenant not found! ");
        //        }
        //        String tenant = (String) args[tenantIndex];

        //        try {
        //            Object result = joinPoint.proceed();
        //            logger.info("start to record feature values for api {}", joinPoint.getSignature());
        //            return result;
        //        } catch (Exception e) {
        //            //            logger.error("metricsAround failed for tenant: {}.", tenant, e);
        //            throw e;
        //        } finally {
        //        }
    }

    /**
     *
     */
    @Pointcut("within(com.fcjexample.demo.controller.SignoffController)")
    public void featureControllerPointcut() {
    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut("within(com.fcjexample..*)")
    public void applicationPackagePointcut() {
    }

    /**
     * Pointcut that matched all feature compute methods with {@literal List<Map>}
     * as its return type
     */
    @Pointcut("execution(String signoff*(..))")
    public void computeFeaturesPointcutList() {
    }

    /**
     * as its return type
     */
    @Pointcut("execution(* compute*(..))")
    public void computeFeaturesPointcut() {
    }

    class SampleConsumer implements Runnable {
        private final int consumeCapacity = 2000;
        private final long consumeMinute = 1;
        private BlockingQueue<TestEntity> queue;

        public SampleConsumer(BlockingQueue<TestEntity> queue) {
            this.queue = queue;
        }

        @Override public void run() {
            List<TestEntity> data = new ArrayList<>();
            try {
                //每次取出的数据存放到data里
                //每次到1000条数据才进行入库，等待1分钟，没达到2000条也继续入库
                long start = System.currentTimeMillis();
                logger.info("start drain. ");
                Queues.drain(queue, data, consumeCapacity, consumeMinute, TimeUnit.MINUTES);
                logger.info("end drain. ");
                long end = System.currentTimeMillis();
                logger.info("drain cost time: " + (end - start) / 1000);

                writeSignoffReplaySampleToS3(data);
            } catch (Exception e) {
                logger.error("SampleConsumer failed. ", e);
            }
        }

        private void writeSignoffReplaySampleToS3(List<TestEntity> data)
                throws IOException {

            System.out.println(data.get(0));
            System.out.println(data.get(1));
            System.out.println("list size is: " + data.size());

        }

    }
}
