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

import java.util.Date;

import static org.quartz.DateBuilder.evenMinuteDate;
import static org.quartz.JobBuilder.newJob;

public class QuartzTest {
    public static void main(String[] args) throws Exception {
        try {
            // Grab the Scheduler instance from the Factory
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // define the job and tie it to our HelloJob class
            JobDetail job = newJob(SimpleJob.class)
                    .withIdentity("job1", "group1")
                    .usingJobData("jobSays", "Hello World!")
                    .usingJobData("myFloatValue", 3.141f)
                    .build();

            // compute a time that is on the next round minute
            Date runTime = evenMinuteDate(new Date());

            // Trigger the job to run on the next round minute
            //            Trigger trigger = newTrigger()
            //                    .withIdentity("trigger1", "group1")
            //                    .startAt(runTime)
            //                    .build();

            //
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger3", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?"))
                    //                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?"))
                    //                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 10-19 * * ?"))
                    //                    .forJob("myJob", "myGroup")// the key is different
                    //                    .forJob("job1", "group1")
                    .build();

            // Tell quartz to schedule the job using our trigger
            scheduler.scheduleJob(job, trigger);

            // and start it off
            scheduler.start();

            Thread.sleep(40L * 1000L);
            //            Thread.sleep(150L * 1000L);

            scheduler.shutdown(true);

        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }
}
