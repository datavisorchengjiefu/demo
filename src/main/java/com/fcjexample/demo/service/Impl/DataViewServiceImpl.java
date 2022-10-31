/*************************************************************************
 *
 * Copyright (c) 2016, DATAVISOR, INC.
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

package com.fcjexample.demo.service.Impl;

import com.fcjexample.demo.entity.TestEntity02;
import com.fcjexample.demo.model.TestEntity;
import com.fcjexample.demo.quartz.SimpleJob;
import com.fcjexample.demo.service.DataViewService;
import com.fcjexample.demo.util.exception.DataViewException;
import com.fcjexample.demo.util.exception.ViewTypeException;
import com.fcjexample.demo.util.storage.TenantContext;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.quartz.JobBuilder.newJob;

@Service
public class DataViewServiceImpl implements DataViewService {
    private static final Logger logger = LoggerFactory.getLogger(DataViewServiceImpl.class);

    @EventListener
    public void onApplicationEvente(ContextRefreshedEvent event) throws Exception {
        logger.info("listener ha! ");
        test();
        System.out.println("listenerhaha.");

    }

    private void test() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        try {

            // define the job and tie it to our HelloJob class
            //            JobDetail job01 = newJob(SimpleJob.class)
            //                    .withIdentity("job1", "group1")
            //                    .usingJobData("jobSays", "Hello World!")
            //                    .usingJobData("myFloatValue", 3.141f)
            //                    .build();

            String tenantName = "tenant01";
            TenantContext.setTenant(tenantName);

            TestEntity02 testEntity02 = new TestEntity02(10L, "name01", "address11");
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("entityKey02", testEntity02);

            JobDetail job02 = newJob(SimpleJob.class)
                    .withIdentity("job2", "group2")
                    .usingJobData("jobSays", "Hello World2!")
                    .usingJobData("myFloatValue", 2f)
                    .usingJobData(jobDataMap)
                    .usingJobData("tenantName", tenantName)
                    .build();

            //
            //            CronTrigger trigger01 = TriggerBuilder.newTrigger()
            //                    .withIdentity("trigger1", "groupT1")
            //                    .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?"))
            //                    //                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?"))
            //                    //                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 10-19 * * ?"))
            //                    //                    .forJob("myJob", "myGroup")// the key is different
            //                    //                    .forJob("job1", "group1")
            //                    .build();

            CronTrigger trigger02 = TriggerBuilder.newTrigger()
                    .withIdentity("trigger2", "groupT2")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                    //                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?"))
                    //                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 10-19 * * ?"))
                    //                    .forJob("myJob", "myGroup")// the key is different
                    //                    .forJob("job1", "group1")
                    .build();
            //            trigger02.

            // Tell quartz to schedule the job using our trigger
            //            scheduler.scheduleJob(job01, trigger01);

            scheduler.scheduleJob(job02, trigger02);

            // and start it off
            scheduler.start();
            logger.info("Current time is {}", new Date());
        } catch (Exception e) {
            logger.error("There is error. ", e);
        }
    }

    @Override public String publishDataView(String tenant) {
        if ("hao".equalsIgnoreCase(tenant)) {
            throw new DataViewException("attention! hao is denied!!!");
        }

        return tenant + "666";
    }

    @Override public TestEntity publishEntity(String tenant) {
        if ("hao".equalsIgnoreCase(tenant)) {
            throw new DataViewException("attention! entity, hao is denied!!!");
        }

        TestEntity subEntity = new TestEntity();
        subEntity.setName("sub2");

        TestEntity entity = new TestEntity();
        entity.setId(4);
        entity.setName(tenant);
        entity.setCreateTime(System.currentTimeMillis());
        Map<Integer, TestEntity> map = new HashMap<>();
        map.put(1, subEntity);
        entity.setSubTestEntitys(map);
        return entity;

    }

    @Override
    public Integer getViewType(String tenant) {
        if ("hao".equalsIgnoreCase(tenant)) {
            throw new ViewTypeException("view type hao is wrong!");
        }
        return 33;
    }

    //    public String testStringException(String str) throws Exception {
    @Override
    public String testStringException(String str) throws Exception {
        if ("hao".equalsIgnoreCase(str)) {
            throw new ViewTypeException("haha exception " + str);
            //            throw new ParseException("ee", 4);
        }

        return "succeed: " + str;
    }
}
