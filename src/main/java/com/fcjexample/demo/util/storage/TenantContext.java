/*************************************************************************
 *
 * Copyright (c) 2019, DATAVISOR, INC.
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

package com.fcjexample.demo.util.storage;

import org.slf4j.MDC;

public class TenantContext {

    public static final String DEFAULT_TENANT_IDENTIFIER = "admin";
    private static final String TENANT_MDC_KEY = "tenant";

    private static final ThreadLocal<String> TENANT_IDENTIFIER = new ThreadLocal<>();
    public static final ThreadLocal<Boolean> CACHE_LEVEL_ONLY = new ThreadLocal<>();

    public static String getTenant() {
        return TENANT_IDENTIFIER.get();
    }

    public static Boolean isCacheLevelOnly() {
        if (CACHE_LEVEL_ONLY.get() == null) {
            return false;
        }
        return CACHE_LEVEL_ONLY.get();
    }

    public static void setTenant(String tenantIdentifier) {
        TENANT_IDENTIFIER.set(tenantIdentifier);
        MDC.put(TENANT_MDC_KEY, tenantIdentifier);
    }

    public static void setCacheLevelOnly(Boolean cacheLevelOnly) {
        CACHE_LEVEL_ONLY.set(cacheLevelOnly);
    }

    public static void reset(String tenantIdentifier) {
        TENANT_IDENTIFIER.remove();
        CACHE_LEVEL_ONLY.remove();
        MDC.clear();
    }

    public static void reset() {
        TENANT_IDENTIFIER.remove();
        CACHE_LEVEL_ONLY.remove();
        MDC.clear();
    }

    public static void setToAdminTenant() {
        setTenant(DEFAULT_TENANT_IDENTIFIER);
        MDC.put(TENANT_MDC_KEY, DEFAULT_TENANT_IDENTIFIER);
    }
}
