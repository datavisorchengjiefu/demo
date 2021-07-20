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

package com.fcjexample.demo.entity;

import java.util.LinkedList;
import java.util.List;

public class ReplayPreviewEntry {

    private static final long serialVersionUID = -4887909460807001755L;
    private String columnName;
    private List<String> examples = new LinkedList<>();

    public ReplayPreviewEntry() {
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public List<String> getExamples() {
        return examples;
    }

    public void setExamples(List<String> examples) {
        this.examples = examples;
    }

    @Override public String toString() {
        return "ReplayPreviewEntry{" +
                "co6666666='" + columnName + '\'' +
                ", examples=" + examples +
                '}';
    }
}
