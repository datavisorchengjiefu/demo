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

package com.fcjexample.demo.test.caffeine;

public class DataObject {
    private final String data;

    private static int objectCounter = 0;
    // standard constructors/getters

    public DataObject(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public static int getObjectCounter() {
        return objectCounter;
    }

    public static void setObjectCounter(int objectCounter) {
        DataObject.objectCounter = objectCounter;
    }

    public static DataObject get(String data) {
        objectCounter++;
        return new DataObject(data);
    }
}
