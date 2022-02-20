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

package com.fcjexample.demo.test.multithread.disruptor;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LongEventMain {
    public static void main(String[] args) throws Exception {
        int bufferSize = 2;

        Disruptor<LongEvent> disruptor =
                new Disruptor<>(new LongEventFactory(), bufferSize, DaemonThreadFactory.INSTANCE);
        //                new Disruptor<>(LongEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);
        //                new Disruptor<>(() -> new LongEvent(), bufferSize, DaemonThreadFactory.INSTANCE);

        //        disruptor.handleEventsWith((event, sequence, endOfBatch) ->
        //                System.out.println("Event: " + event));
        disruptor.handleEventsWith(new LongEventHandler());
        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        //        ByteBuffer bb = ByteBuffer.allocate(8);
        //        for (long l = 0; true; l++) {
        //            bb.putLong(0, l);
        //            ringBuffer.publishEvent(new EventTranslatorOneArg<LongEvent, ByteBuffer>() {
        //                @Override public void translateTo(LongEvent event, long sequence,
        //                        ByteBuffer buffer) {
        //                    event.setValue(buffer.getLong(0));
        //                }
        //            }, bb);
        //            Thread.sleep(1000);
        //        }

        List<Long> longList = new ArrayList<>(Arrays.asList(2L, 4L, 5L, 8L));
        for (Long l : longList) {
            ringBuffer.publishEvent(new EventTranslatorOneArg<LongEvent, Object>() {
                @Override public void translateTo(LongEvent event, long sequence, Object arg0) {
                    event.setValue(
                            (Long) arg0);// should not be setValue(l), which will instantiate an object
                }
            }, l);
            Thread.sleep(1000);
        }

    }
}
