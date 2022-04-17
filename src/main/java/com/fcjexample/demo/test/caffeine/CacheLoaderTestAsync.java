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

import com.github.benmanes.caffeine.cache.AsyncCacheLoader;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

public class CacheLoaderTestAsync implements AsyncCacheLoader<String, DataObject> {

    private static final Logger logger = LoggerFactory.getLogger(CacheLoaderTestAsync.class);

    @Override public @NonNull CompletableFuture<DataObject> asyncLoad(@NonNull String key,
            @NonNull Executor executor) {
        CompletableFuture<DataObject> future = CompletableFuture.supplyAsync(
                new Supplier<DataObject>() {
                    @Override public DataObject get() {
                        logger.info("start get. ");
                        return DataObject.get("load Data01 ha for " + key);
                    }
                });

        return future;
    }

    @Override public @NonNull CompletableFuture<Map<String, DataObject>> asyncLoadAll(
            @NonNull Iterable<? extends String> keys, @NonNull Executor executor) {

        CompletableFuture<Map<String, DataObject>> future = CompletableFuture.supplyAsync(
                new Supplier<Map<String, DataObject>>() {
                    @Override public Map<String, DataObject> get() {
                        Map<String, DataObject> map = new HashMap<>();
                        logger.info("start loadAll");
                        for (String key : keys) {
                            logger.info("key is {}", key);
                            if ("A".equals(key) || "D".equals(key)) {
                                map.put(key, DataObject.get("loadAll Data02 ha for " + key));
                            }
                        }
                        return map;
                    }
                });

        return future;
    }

}
