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

package com.fcjexample.demo.model;

public class ApiException {

    private String statusCode;
    private String msg;
    private String exInfo;

    public ApiException() {
    }

    public ApiException(String statusCode, String msg, String exInfo) {
        this.statusCode = statusCode;
        this.msg = msg;
        this.exInfo = exInfo;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getExInfo() {
        return exInfo;
    }

    public void setExInfo(String exInfo) {
        this.exInfo = exInfo;
    }

    @Override public String toString() {
        return "ApiException{" +
                "statusCode='" + statusCode + '\'' +
                ", msg='" + msg + '\'' +
                ", exInfo='" + exInfo + '\'' +
                '}';
    }
}
