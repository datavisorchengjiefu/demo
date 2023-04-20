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

package com.fcjexample.demo.test.testpackage;

import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TestMain02 {
    static RestTemplate restTemplate = new RestTemplate();
    private static final Logger LOGGER = LoggerFactory.getLogger(TestMain02.class);

    public static void main(String[] args) throws Exception {
        String ekata01 = "headers.set(\"Authorization\", \"Bearer $bearer_token\");";
        String ekata02 = "headers.set(\"Authorization\", Bearer $bearer_token);";
        System.out.println(ekata01);
        System.out.println(ekata02);
        ekata01 = ekata01.replace("$bearer_token", "ha32");
        ekata02 = ekata02.replace("$bearer_token", "ha66");
        System.out.println(ekata01);
        System.out.println(ekata02);

        URIBuilder uriBuilder = new URIBuilder()
                .setScheme("https")
                .setHost("api.ekata.com")
                .setPath("/3.3/identity_check")

                .setParameter("api_key", "xxx")
                .setParameter("primary.name", "Waidong L Syrws")
                .setParameter("primary.phone", "2069735100")
                .setParameter("primary.email_address", "waidong@gmail.com")
                .setParameter("ip_address", "54.190.251.42");

        //        String url = uriBuilder.build().toString();// 这种事错误的，如果有特殊字符的话
        // 1. uri
        URI uri = uriBuilder.build();
        LOGGER.info("haha uri is: {}", uri.toURL());
        LOGGER.info("haha uri is: {}", decodeValue(uri.toString()));

        ResponseEntity<Map> response = restTemplate.getForEntity(uri, Map.class);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer gggxxx");
        HttpEntity<Map> entity = new HttpEntity<>(headers);
        response = restTemplate.exchange(uri, HttpMethod.GET, entity, Map.class);

        //        // 2. url, need use @, not use %40
        //        String urlTest = "https://api.ekata.com/3.3/identity_check?api_key=xxx&primary.name=Waidong L Syrws&primary.phone=2069735100&primary.email_address=waidong@gmail.com&ip_address=54.190.251.42";
        //        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        //        //        ResponseEntity<String> response = restTemplate.getForEntity(urlTest, String.class);
        //
        //        int statusCode = response.getStatusCodeValue();
        //        String reasonPhrase = response.getStatusCode().getReasonPhrase();
        //        System.out.println(reasonPhrase);

    }

    public List test01(Object... args) {
        if ("userAB0".equals(args[0]) && "device2".equals(args[1])) {
            return Arrays.asList(args[0], args[1], true);
        }
        if ("userAB1".equals(args[0]) && "device12".equals(args[1])) {
            return Arrays.asList(args[0], args[1], true);
        }
        if ("userAB3".equals(args[0]) && "device35".equals(args[1])) {
            return Arrays.asList(args[0], args[1], true);
        }

        return Arrays.asList(args[0], args[1], false);
    }

    private static String encodeValue(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }

    private static String decodeValue(String value) throws UnsupportedEncodingException {
        return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
    }
}
