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

package com.fcjexample.demo.test.multithread.disruptor.demo;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import sun.misc.Contended;

import java.util.concurrent.ThreadFactory;

public class TestOriginal01 {
    public static void main(String[] args) {
        class Element {
            @Contended private String value;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        ThreadFactory threadFactory = new ThreadFactory() {
            int i = 0;

            @Override public Thread newThread(Runnable r) {
                return new Thread(r, "simpleThread" + String.valueOf(i++));
            }
        };

        EventFactory<Element> factory = new EventFactory<Element>() {
            @Override public Element newInstance() {
                return new Element();
            }
        };

        EventHandler<Element> handler = new EventHandler<Element>() {
            @Override public void onEvent(Element element, long sequence, boolean endOfBatch)
                    throws InterruptedException {
                System.out.println(
                        "Element: " + Thread.currentThread().getName() + ": " + element.getValue()
                                + ": "
                                + sequence);
                Thread.sleep(3000);

            }
        };

        BlockingWaitStrategy strategy = new BlockingWaitStrategy();

        // 会有影响，对于生产者
        int bufferSize = 4;
        //        int bufferSize = 8;
        //        int bufferSize = 16;

        Disruptor<Element> disruptor = new Disruptor(factory, bufferSize, threadFactory,
                ProducerType.SINGLE, strategy);
        // 设置EventHandler
        disruptor.handleEventsWith(handler);

        disruptor.start();
        for (int i = 0; i < 10; i++) {
            disruptor.publishEvent(new EventTranslator<Element>() {
                @Override public void translateTo(Element element, long sequence) {
                    System.out.println("之前的数据" + element.getValue() + "当前的sequence" + sequence);
                    element.setValue("我是第" + sequence + "个");
                }
            });
        }

    }
}
