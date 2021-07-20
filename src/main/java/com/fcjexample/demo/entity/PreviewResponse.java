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

import java.util.HashMap;
import java.util.Map;

public class PreviewResponse {
    private static final long serialVersionUID = 4867391423017598039L;
    private int errorLines;
    private int totalLines;
    private Map<String, ReplayPreviewEntry> examples = new HashMap<>();

    public PreviewResponse() {
    }

    public int getTotalLines() {
        return totalLines;
    }

    public void setTotalLines(int totalLines) {
        this.totalLines = totalLines;
    }

    public int getErrorLines() {
        return errorLines;
    }

    public void setErrorLines(int errorLines) {
        this.errorLines = errorLines;
    }

    public Map<String, ReplayPreviewEntry> getExamples() {
        return examples;
    }

    public void setExamples(
            Map<String, ReplayPreviewEntry> examples) {
        this.examples = examples;
    }

    @Override public String toString() {
        return "PreviewResponse{" +
                "errorLines=" + errorLines +
                ", totalLines=" + totalLines +
                ", examples=" + examples +
                '}';
    }
}
