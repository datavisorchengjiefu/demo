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

package com.fcjexample.demo.service.Impl;

import com.fcjexample.demo.model.TestEntity;
import com.fcjexample.demo.service.DataViewService;
import com.fcjexample.demo.util.exception.DataViewException;
import com.fcjexample.demo.util.exception.ViewTypeException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DataViewServiceImpl implements DataViewService {
    @Override public String publishDataView(String tenant) {
        if ("hao".equalsIgnoreCase(tenant)) {
            throw new DataViewException("attention! hao is denied!!!");
        }

        return tenant + "666";
    }

    @Override public TestEntity publishEntity(String tenant) {
        if ("hao".equalsIgnoreCase(tenant)) {
            throw new DataViewException("attention! entity, hao is denied!!!");
        }

        TestEntity subEntity = new TestEntity();
        subEntity.setName("sub2");

        TestEntity entity = new TestEntity();
        entity.setId(4);
        entity.setName(tenant);
        entity.setCreateTime(System.currentTimeMillis());
        Map<Integer, TestEntity> map = new HashMap<>();
        map.put(1, subEntity);
        entity.setSubTestEntitys(map);
        return entity;

    }

    @Override
    public Integer getViewType(String tenant) {
        if ("hao".equalsIgnoreCase(tenant)) {
            throw new ViewTypeException("view type hao is wrong!");
        }
        return 33;
    }
}
