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

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * A feature metrics aspect to record feature value to meter registry
 */
@Aspect
@Component
public class SignoffAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(SignoffAspect.class);

    /**
     * Advice feature metrics when a method is entered and exited.
     *
     * @param joinPoint join point for advice
     * @return result
     * @throws Throwable throws IllegalArgumentException
     */
    @Around("applicationPackagePointcut() && featureControllerPointcut() && "
            + " computeFeaturesPointcutList()")
    @SuppressWarnings("unchecked")
    public Object metricsAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        String[] parameterNames = methodSignature.getParameterNames();

        try {
            Object result = joinPoint.proceed();
            LOGGER.info("start to record feature values for api {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            LOGGER.error("metricsAround failed. ", e);
            throw e;
        } finally {

        }
    }

    /**
     * Pointcut that matches {@link SignoffController} REST endpoints.
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
     * Pointcut that matched all feature compute methods with {@link com.datavisor.velocity.FeatureValueMap}
     * as its return type
     */
    //    @Pointcut("execution(com.datavisor.velocity.FeatureValueMap compute*(..))")
    public void computeFeaturesPointcut() {
    }

}
