/*************************************************************************
 *
 * Copyright (c) 2023, DATAVISOR, INC.
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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import scala.Tuple2;

import java.util.Map;

public class Test {
    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {
        String requestBody = "{\n"
                + "    \"user_id\": \"chengjie_QA_784_fpcompute_user_id_20200101_01\",\n"
                + "    \"event_type\": \"chengjie_QA_784_event_type_20200101\",\n"
                + "    \"event_time\": \"2020-01-01 00:00:01\",\n"
                + "    \"QA_784_target_01\": \"chengjie_QA_784_fpcompute_target_20200101_01\",\n"
                + "    \"QA_784_dimension_01\": \"chengjie_QA_784_dimension_20200101_01\"\n"
                + "}";

        Object object = callDetection(requestBody);
        logger.info("object is {}. ", object);
    }

    public static Object callDetection(String requestBody) {
        //        if (Arrays.stream(springEnvironment.getActiveProfiles())
        //                .anyMatch("test"::equalsIgnoreCase)) {
        //            return DETECTION_MAP_PREFIX + requestBody;
        //        }

        RestTemplate restTemplate = new RestTemplate();
        String path = "http://k8s-us-dev-a-qa.dv-api.com/qa/fp-ui/api-1.0-SNAPSHOT/qa748/run-rule/compute-features/external/checkpoint?updateVelocity=false";

        //        ResponseEntity<Object> responseEntity = restTemplate.exchange(
        //                path,
        //                HttpMethod.POST,
        //                new HttpEntity<>(requestBody),
        //                new ParameterizedTypeReference<Object>() {
        //                }
        //        );

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                path,
                HttpMethod.POST,
                new HttpEntity<>(requestBody),
                new ParameterizedTypeReference<String>() {
                }
        );

        //        if (!forPipeline) {
        //            return responseEntity.getBody();
        //        }

        String responseBody =
                responseEntity.getBody() == null ? null : responseEntity.getBody().toString();
        return processFeaturesForFerryData(new Tuple2<>(requestBody, responseBody));
    }

    /**
     * Logic is from platform spark ferry. GenericFeaturePlatformResponseProcessor
     *
     * @param data
     * @return
     */
    public static String processFeaturesForFerryData(Tuple2<String, String> data) {
        if (data == null) {
            return null;
        }
        String response = "";
        try {
            JsonObject rawJson = new JsonParser().parse(data._1).getAsJsonObject();
            JsonObject responseJson = new JsonParser().parse(data._2).getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : responseJson.entrySet()) {
                boolean isNum = true;
                for (int i = 0; i < entry.getKey().length(); i++) {
                    if (!Character.isDigit(entry.getKey().charAt(i))) {
                        isNum = false;
                        break;
                    }
                }
                if (!isNum) {
                    rawJson.add(entry.getKey(), entry.getValue());
                }
            }
            response = rawJson.toString();
        } catch (Exception e) {
            logger.error("processFeaturesForFerryData failed.", e);
        }

        return response;
    }
}
