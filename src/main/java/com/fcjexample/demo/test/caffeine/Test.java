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

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class Test {
    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) throws Exception {
        Cache<String, DataObject> cache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(100)
                .build();

        String key = "A";
        DataObject result = cache.getIfPresent(key);
        logger.info("dataObject is {}", result);

        DataObject dataObject = new DataObject("data01");
        cache.put("01", dataObject);

        result = cache.getIfPresent("01");
        logger.info("dataObject is {}", result.getData());

        result = cache.get("02", new Function<String, DataObject>() {
            @Override public DataObject apply(String s) {
                return new DataObject("data02");
            }
        });

        logger.info("dataObject is {}", result.getData());
        cache.invalidate("01");
        result = cache.getIfPresent("01");
        logger.info("dataObject is {}", result);

        //
        System.out.println("===============");
        LoadingCache<String, DataObject> loadingCache = Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .build(new CacheLoader<String, DataObject>() {
                    @Nullable @Override public DataObject load(@NonNull String k) throws Exception {
                        return DataObject.get("Data for " + k);
                    }
                });
        Map<String, DataObject> dataObjectMap
                = loadingCache.getAll(Arrays.asList("A", "B", "C"));
        loadingCache.cleanUp();

        for (Map.Entry<String, DataObject> entry : dataObjectMap.entrySet()) {
            logger.info("entry is {}, {}. ", entry.getKey(), entry.getValue().getData());
        }

        // loadAll
        System.out.println("===============");
        CacheLoader<String, DataObject> cacheLoaderAll = new CacheLoaderTest01();
        DataObject resultAll = cacheLoaderAll.load("A");
        logger.info("entry is {}. ", resultAll.getData());

        Map<String, DataObject> dataObjectMapAll
                = cacheLoaderAll.loadAll(Arrays.asList("A", "B", "C", "D", "E"));
        for (Map.Entry<String, DataObject> entry : dataObjectMapAll.entrySet()) {
            logger.info("entry is {}, {}. ", entry.getKey(), entry.getValue().getData());
        }

    }
}
