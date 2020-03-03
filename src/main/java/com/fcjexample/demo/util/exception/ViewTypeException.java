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

package com.fcjexample.demo.util.exception;

public class ViewTypeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ViewTypeException() {
    }

    public ViewTypeException(String message) {
        super(message);
    }

    public ViewTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
