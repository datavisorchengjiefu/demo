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

import com.github.benmanes.caffeine.cache.CacheLoader;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.Map;

public class CacheLoaderTest01 implements CacheLoader<String, DataObject> {
    @Nullable @Override public DataObject load(@NonNull String key) throws Exception {
        return DataObject.get("load Data01 ha for " + key);
    }

    @Override public @NonNull Map<String, DataObject> loadAll(
            @NonNull Iterable<? extends String> keys) throws Exception {
        Map<String, DataObject> map = new HashMap<>();
        for (String key : keys) {
            if ("A".equals(key) || "D".equals(key)) {
                map.put(key, DataObject.get("loadAll Data02 ha for " + key));
            }
        }
        return map;
    }
}
