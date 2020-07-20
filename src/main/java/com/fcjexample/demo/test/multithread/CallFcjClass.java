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

package com.fcjexample.demo.test.multithread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

public class CallFcjClass implements Callable<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CallFcjClass.class);

    @Override public String call() throws Exception {
        try {
            Thread.sleep(1000);
            int a = 8 / 0;
            return "Call: " + Thread.currentThread().getName();
        } catch (Exception e) {
            //                System.out.println("call fcj: " + e.getMessage());
            LOGGER.error("call fcj failed. ", e);
            //            throw e; 如果没有logger.error，并且外面没有future.get()，即使throw e也没有用的。
        }
        return null;

        //        Thread.sleep(1000);
        //        int a = 8 / 0;
        //        return "Call: " + Thread.currentThread().getName();
    }
}
