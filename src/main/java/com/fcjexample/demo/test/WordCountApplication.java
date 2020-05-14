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

package com.fcjexample.demo.test;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.Arrays;
import java.util.Properties;

public class WordCountApplication {

    private static final String BOOTSTRAP_SERVERS = "localhost:9092";

    public static void main(final String[] args) throws Exception {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-application");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> textLines = builder.stream("TextLinesTopic");
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
                .to("WordsWithCountsTopic", Produced.with(Serdes.String(), Serdes.Long()));

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
    }

    //    public static void main(final String[] args) throws Exception {
    //
    //        Properties props = new Properties();
    //        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-application");
    //        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
    //        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
    //        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
    //
    //        StreamsBuilder builder = new StreamsBuilder();
    //        KStream<String, String> textLines = builder.stream("TextLinesTopic");
    //        KTable<String, Long> wordCounts = textLines
    //                .flatMapValues(textLine -> Arrays.asList(textLine.toLowerCase().split("\\W+")))
    //                .groupBy((key, word) -> word)
    //                .count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("counts-store"));
    //        wordCounts.toStream()
    //                .to("WordsWithCountsTopic", Produced.with(Serdes.String(), Serdes.Long()));
    //
    //        KafkaStreams streams = new KafkaStreams(builder.build(), props);
    //        streams.start();
    //    }

}
