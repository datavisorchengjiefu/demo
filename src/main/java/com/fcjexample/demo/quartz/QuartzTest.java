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

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static org.quartz.DateBuilder.evenMinuteDate;
import static org.quartz.JobBuilder.newJob;

public class QuartzTest {
    private static final Logger logger = LoggerFactory.getLogger(QuartzTest.class);

    public static void main(String[] args) throws Exception {
        // Grab the Scheduler instance from the Factory
        //            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        try {

            // define the job and tie it to our HelloJob class
            JobDetail job01 = newJob(SimpleJob.class)
                    .withIdentity("job1", "group1")
                    .usingJobData("jobSays", "Hello World!")
                    .usingJobData("myFloatValue", 3.141f)
                    .build();

            JobDetail job02 = newJob(SimpleJob.class)
                    .withIdentity("job2", "group2")
                    .usingJobData("jobSays", "Hello World2!")
                    .usingJobData("myFloatValue", 2f)
                    .build();

            // compute a time that is on the next round minute
            Date runTime = evenMinuteDate(new Date());

            // Trigger the job to run on the next round minute
            //            Trigger trigger = newTrigger()
            //                    .withIdentity("trigger1", "group1")
            //                    .startAt(runTime)
            //                    .build();

            //
            CronTrigger trigger01 = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1", "groupT1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?"))
                    //                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?"))
                    //                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 10-19 * * ?"))
                    //                    .forJob("myJob", "myGroup")// the key is different
                    //                    .forJob("job1", "group1")
                    .build();

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
            scheduler.scheduleJob(job01, trigger01);

            scheduler.scheduleJob(job02, trigger02);

            // and start it off
            scheduler.start();
            logger.info("Current time is {}", new Date());

            Thread.sleep(10L * 1000L);

            // 1. delete
            logger.info("job02 key is {}. ", job02.getKey());
            scheduler.deleteJob(job02.getKey());
            // delete multiple times or delete non job is fine.
            scheduler.deleteJob(job02.getKey());
            scheduler.deleteJob(new JobKey("testName", "testGroup"));

            // 2. pause: no work???
            //            scheduler.pauseJob(job02.getKey());
            //            Thread.sleep(20L * 1000L);
            //            scheduler.resumeJob(job02.getKey());

            // 3. pause: no work
            //            scheduler.pauseTrigger(trigger02.getKey());
            //            Thread.sleep(20L * 1000L);
            //            scheduler.resumeTrigger(trigger02.getKey());

            // 4. unschedule: work, but can't schedule again.
            // We can unschedule a Job by calling the unschedule() method of the Scheduler class and passing the TriggerKey.
            // If the related job does not have any other triggers, and the job is not durable, then the job will also be deleted.
            //                        scheduler.unscheduleJob(trigger02.getKey());
            //            Thread.sleep(20L * 1000L);
            //            scheduler.scheduleJob(trigger02);

            //            scheduler.addJob();
            //            scheduler.pauseJob();
            //            scheduler.deleteJob();

            Thread.sleep(20L * 1000L);
            //            Thread.sleep(150L * 1000L);

            scheduler.shutdown(true);

        } catch (Exception e) {
            logger.error("Failed. ", e);
            scheduler.shutdown(true);
        }
    }
}
