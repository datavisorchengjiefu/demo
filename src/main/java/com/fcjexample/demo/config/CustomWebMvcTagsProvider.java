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

package com.fcjexample.demo.config;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import org.springframework.boot.actuate.metrics.web.servlet.DefaultWebMvcTagsProvider;
import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTagsProvider;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
class CustomWebMvcTagsProvider implements WebMvcTagsProvider {

    private static final String PATH_VARIABLES = "org.springframework.web.servlet.View.pathVariables";
    private static final String DUMMY_TENANT_NAME = "dummy$Tenant";

    private final WebMvcTagsProvider delegate = new DefaultWebMvcTagsProvider();

    @Override
    public Iterable<Tag> getTags(HttpServletRequest request, HttpServletResponse response,
            Object handler,
            Throwable exception) {
        String tenant = getTenant(request);

        return Tags.of(this.delegate.getTags(request, response, handler, exception))
                .and("tenant", tenant);
    }

    @Override
    public Iterable<Tag> getLongRequestTags(HttpServletRequest request, Object handler) {
        return Tags.of(this.delegate.getLongRequestTags(request, handler))
                .and("test022", "haha0122");
    }

    private String getTenant(HttpServletRequest request) {
        String tenant = DUMMY_TENANT_NAME;
        if (request.getAttribute(PATH_VARIABLES) != null
                && Map.class.isAssignableFrom(request.getAttribute(PATH_VARIABLES).getClass())) {
            Map<String, String> map = (Map<String, String>) request.getAttribute(PATH_VARIABLES);
            tenant = map.getOrDefault("tenant", DUMMY_TENANT_NAME);
        }
        return tenant;
    }

}
