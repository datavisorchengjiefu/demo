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

package com.fcjexample.demo.service.Impl;

import com.datavisor.file.s3io.S3Utils;
import com.datavisor.util.base.UtilDataVisor;
import com.fcjexample.demo.service.SignoffService;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class SignoffServiceImpl implements SignoffService {

    private int num = 3;

    @PostConstruct
    public void init() {
        System.out.println("init");
        System.out.println("init: " + num);
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) throws Exception {

        System.out.println("listener.");
        System.out.println("init: " + num);

    }

    @Override public void test() throws IOException {
        String file = "s3a://datavisor-unittest/test-resources/unittest.txt";
        String clientName = "unittest";

        String[] args = new String[] {
                String.format("--clientName=%s", clientName),
                //                String.format("--pipelineS3Folder=%s", ""),
                "--pipelineConfig=local"
        };
        UtilDataVisor.init(args);

        //        FileSystem fileSystem = S3Utils.CreateFs(file74k, ConfigInfo.get().getRoot());
        InputStream is = S3Utils.openResourceOrS3File(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        // Remove header
        br.readLine();
        String line = null;
        do {
            line = br.readLine();
            if (line != null) {
                System.out.println(line);
            }
        } while (line != null);
    }
}
