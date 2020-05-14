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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.Properties;

@Configuration
//@Component
public class KafkaStreamsConfig {

    private static final Logger logger = LoggerFactory.getLogger(KafkaStreamsConfig.class);
    public static final String INPUT_TOPIC = "streams-plaintext-input";
    public static final String OUTPUT_TOPIC = "streams-wordcount-output";

    Properties props;
    KafkaStreams kafkaStreams;
    @Autowired
    private ObjectMapper mapper;

    static Properties getStreamsConfig() {
        final Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-wordcount");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,
                Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,
                Serdes.String().getClass().getName());

        // setting offset reset to earliest so that we can re-run the demo code with the same pre-loaded data
        // Note: To re-run the demo, you need to use the offset reset tool:
        // https://cwiki.apache.org/confluence/display/KAFKA/Kafka+Streams+Application+Reset+Tool
        //        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return props;
    }

    //    @Bean
    public StreamsBuilder streamsBuilder() {
        return new StreamsBuilder();
    }

    //    @Bean(name = "kafkaStreamSignoff")
    public KStream<String, String> kafkaStreamSignoff(StreamsBuilder builder) {
        KStream<String, String> textLines = builder.stream(INPUT_TOPIC);
        KTable<String, Long> wordCounts = textLines
                .flatMapValues(new ValueMapper<String, Iterable<String>>() {
                    @Override
                    public Iterable<String> apply(String textLine) {
                        return Arrays.asList(textLine.toLowerCase().split("\\W+"));
                    }
                })
                .groupBy(new KeyValueMapper<String, String, String>() {
                    @Override
                    public String apply(String key, String word) {
                        return word;
                    }
                })
                .count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("counts-store"));

        wordCounts.toStream()
                .to(OUTPUT_TOPIC, Produced.with(Serdes.String(), Serdes.Long()));

        return textLines;
    }

    //    @Bean
    //    public KafkaStreams kafkaStreams(StreamsBuilder streamsBuilder) {
    //        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), props);
    //        kafkaStreams.start();
    //
    //    }
    private void createWordCountStream(final StreamsBuilder builder) {
        KStream<String, String> textLines = builder.stream(INPUT_TOPIC);
        KTable<String, Long> wordCounts = textLines
                .flatMapValues(new ValueMapper<String, Iterable<String>>() {
                    @Override
                    public Iterable<String> apply(String textLine) {
                        return Arrays.asList(textLine.toLowerCase().split("\\W+"));
                    }
                })
                .groupBy(new KeyValueMapper<String, String, String>() {
                    @Override
                    public String apply(String key, String word) {
                        return word;
                    }
                })
                .count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("counts-store"));

        wordCounts.toStream()
                .to(OUTPUT_TOPIC, Produced.with(Serdes.String(), Serdes.Long()));
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) throws Exception {
        props = getStreamsConfig();
        final StreamsBuilder builder = new StreamsBuilder();
        createWordCountStream(builder);
        kafkaStreams = new KafkaStreams(builder.build(), props);
        //        final CountDownLatch latch = new CountDownLatch(1);

        // attach shutdown handler to catch control-c
        //        Runtime.getRuntime().addShutdownHook(new Thread("streams-wordcount-shutdown-hook") {
        //            @Override
        //            public void run() {
        //                kafkaStreams.close();
        //                latch.countDown();
        //            }
        //        });

        //        try {
        kafkaStreams.start();
        //        }
    }

    @PreDestroy
    public void after() {
        logger.info("kafkaStreams preDestroy. ");
        kafkaStreams.close();
    }
}

