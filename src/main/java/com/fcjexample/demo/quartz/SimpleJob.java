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

import com.fcjexample.demo.service.BeanUtil;
import com.fcjexample.demo.service.HelloService;
import com.fcjexample.demo.service.Impl.HelloServiceImpl;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class SimpleJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(SimpleJob.class);

    // work
    public SimpleJob() {
        logger.info("create a new simpleJob");
        hello = BeanUtil.getBean(HelloServiceImpl.class);
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
        logger.info("This is a quartz job! {}. ", new Date());
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();

        String jobSays = dataMap.getString("jobSays");
        float myFloatValue = dataMap.getFloat("myFloatValue");
        logger.info("Job says: {}, and val is: {}", jobSays, myFloatValue);
        logger.info("JobDetail key is {}, {}. ", context.getJobDetail().getKey().getGroup(),
                context.getJobDetail().getKey().getName());
        logger.info("execute simpleJob");
        hello.sayHelloV2();
    }

    private HelloService hello;
}