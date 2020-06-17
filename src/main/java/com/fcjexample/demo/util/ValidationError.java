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

package com.fcjexample.demo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ValidationError {

    private final String summary;

    private List<Map<String, String>> errorList = new ArrayList<>();

    public ValidationError(String summary) {
        this.summary = summary;
    }

    public void addError(Map<String, String> errorMap) {
        errorList.add(errorMap);
    }

    public String getSummary() {
        return summary;
    }

    public List<Map<String, String>> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<Map<String, String>> errorList) {
        this.errorList = errorList;
    }

    @Override public String toString() {
        return "ValidationError{" +
                "summary='" + summary + '\'' +
                ", errorList=" + errorList +
                '}';
    }
}
