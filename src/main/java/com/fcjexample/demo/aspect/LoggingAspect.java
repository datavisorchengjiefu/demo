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

package com.fcjexample.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component // 别忘了这个。。。
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerFactory.class);

    //    @AfterThrowing(pointcut = "execution(* com.fcjexample.demo.controller.HelloController.publishDataView2(..))", throwing = "ex")
    //    @AfterThrowing(pointcut = "execution(* com.fcjexample.demo.controller.*.*(..))", throwing = "ex")
    //    public void logAfterThrowingAllMethods(Exception ex) throws Throwable {
    //        //    public void logAfterThrowingAllMethods() throws Throwable {
    //        System.out.println("****LoggingAspect.logAfterThrowingAllMethods() fcj: " + ex.toString());
    //
    //    }

    //    @Around("execution(* com.fcjexample.demo.controller.*.*(..))")
    //    public ApiResult<String> logAfterThrowingAllMethods(ProceedingJoinPoint jp)
    //            throws Throwable {
    //
    //        try {
    //            String res = (String) jp.proceed();
    //            return new ApiResult<>(ApiResult.CORRECT, "SUCCEED", res);
    //        } catch (Exception e) {
    //            LOGGER.error("log exception 666", e);
    //            return new ApiResult<>(ApiResult.ERROR, "failedhaha!!!", e.toString());
    //        }
    //    }

    //    @Around("execution(* com.fcjexample.demo.controller.*.*(..))")
    public Object logAfterThrowingAllMethods(ProceedingJoinPoint pjp) throws Throwable {
        try {
            Object res = null;
            res = pjp.proceed();
            return res;
        } catch (Exception e) {
            LOGGER.error("log exception 666", e);
            throw e;
        }
    }
}
