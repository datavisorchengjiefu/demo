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

package com.fcjexample.demo.util.enums;

public enum ApiStatus {
    FP_API_ERROR_DEFAULT_STATUS("FP_API_ERROR_DEFAULT_STATUS"),
    FP_API_ERROR_DEFAULT_ERROR("FP_API_ERROR_DEFAULT_ERROR"),
    FP_API_ERROR_VIEW_ERROR("FP_API_ERROR_VIEW_ERROR"),
    FP_API_ERROR_INVALID_ID("FP_API_ERROR_INVALID_ID"),
    FP_API_ERROR_INVALID_USERNAME("FP_API_ERROR_INVALID_USERNAME"),
    FP_API_ERROR_PERMISSION_DENIED("FP_API_ERROR_PERMISSION_DENIED"),
    FP_API_ERROR_ACCOUNT_NOT_ACTIVATED("FP_API_ERROR_ACCOUNT_NOT_ACTIVATED"),
    FP_API_ERROR_EMAIL_NONE_EXIST("FP_API_ERROR_EMAIL_NONE_EXIST"),
    FP_API_ERROR_VALIDATION("FP_API_ERROR_VALIDATION");

    private final String name;

    ApiStatus(String name) {
        this.name = name;
    }
}
