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

package com.fcjexample.demo.test.sketches;

import org.apache.datasketches.cpc.CpcSketch;
import org.apache.datasketches.cpc.CpcUnion;
import org.apache.datasketches.memory.Memory;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class AggFunction {

    public static void main(String[] args) throws Exception {
        AggFunction aggFunction = new AggFunction();
        Map<String, Object> map = new HashMap<>();
        map.put("s01", "1");
        //        map.put("s02", 2);
        double r1 = (double) aggFunction.add(map, null);
        //        long r1 = (Long) aggFunction.add(map, null);
        double r2 = (double) aggFunction.add(map, 3);
        //        long r2 = (Long) aggFunction.add(map, 3);
        System.out.println("hh is" + r1 + " and " + r2);
    }

    public Object add(Map<String, Object> value, Object accumulator) throws Exception {
        Object s = null;
        /**
         * Exception in thread "main" java.lang.NullPointerException
         * 	at java.util.Base64$Decoder.decode(Base64.java:549)
         */
        String s1 = (String) s;
        /**
         * Exception in thread "main" java.lang.AssertionError: 233 != 1
         * 	at org.apache.datasketches.cpc.RuntimeAsserts.error(RuntimeAsserts.java:117)
         * 	at org.apache.datasketches.cpc.RuntimeAsserts.rtAssertEquals(RuntimeAsserts.java:39)
         * 	at org.apache.datasketches.cpc.PreambleUtil.checkLoPreamble(PreambleUtil.java:810)
         * 	at org.apache.datasketches.cpc.CompressedState.importFromMemory(CompressedState.java:122)
         * 	at org.apache.datasketches.cpc.CpcSketch.heapify(CpcSketch.java:253)
         * 	at org.apache.datasketches.cpc.CpcSketch.heapify(CpcSketch.java:234)
         */
        //        String s1 = String.valueOf(s);
        Object result = getResult(s1);

        //        return "DUMMY_ADD_SCRIPT";

        //        // 1.
        //        if (accumulator == null) {
        //            return value.values().iterator().next();
        //        }
        //        Object obj = value.values().iterator().next();
        //        final int lgK = 11;
        //        byte[] decodedBytes1 = Base64.getDecoder().decode((String) obj);
        //        byte[] decodedBytes2 = Base64.getDecoder().decode((String) accumulator);
        //        CpcSketch sketch1 = CpcSketch.heapify(Memory.wrap(decodedBytes1));
        //        CpcSketch sketch2 = CpcSketch.heapify(Memory.wrap(decodedBytes2));
        //        CpcUnion union = new CpcUnion(lgK);
        //        union.update(sketch1);
        //        union.update(sketch2);
        //        return Base64.getEncoder().encodeToString(union.getResult().toByteArray());

        // 1.2
        //        String str = String.valueOf(value.values().iterator().next());
        //        if (accumulator == null) {
        //            final int lgK = 11;
        //            CpcSketch sketch = new CpcSketch(lgK);
        //            sketch.update(str);
        //            return Base64.getEncoder().encodeToString(sketch.toByteArray());
        //        }
        //        byte[] decodedBytes = Base64.getDecoder().decode(String.valueOf(accumulator));
        //        CpcSketch sketch = CpcSketch.heapify(Memory.wrap(decodedBytes));
        //        sketch.update(str);
        //        return Base64.getEncoder().encodeToString(sketch.toByteArray());

        //        if (accumulator == null) return ((Double) value.values().iterator().next()).doubleValue();
        //        return ((Double) value.values().iterator().next()).longValue() + (Long) accumulator;

        // 1. correct
        if (accumulator == null)
            return Double.parseDouble(String.valueOf(value.values().iterator().next()));
        return Math.min(Double.parseDouble(String.valueOf(value.values().iterator().next())),
                Double.parseDouble(String.valueOf(accumulator)));

        // 2. test
        //        if (accumulator == null) return ((Number) value.values().iterator().next()).longValue();
        //        return ((Number) value.values().iterator().next()).longValue() + (Long) accumulator;
        // 2. correct
        //        if (accumulator == null) return Long.parseLong(String.valueOf(value.values().iterator().next()));
        //        return Long.parseLong(String.valueOf(value.values().iterator().next())) + Long.parseLong(String.valueOf(accumulator));

        //        // 1. test
        //        if (accumulator == null) return ((Number) value.values().iterator().next()).longValue();
        //        return ((Number) value.values().iterator().next()).longValue() + (Long) accumulator;

        // 2. test
        //        if (accumulator == null) return ((Number) value.values().iterator().next()).doubleValue();
        //        return ((Number) value.values().iterator().next()).doubleValue() + ((Number) accumulator).doubleValue();

        //                "return Math.max((double)o1 , (double)o2); ",
        //                "return Double.valueOf(accumulator);",

        // 2.
        //        return merge(temp, accumulator);

        // 3.
        //        int num = Integer.parseInt((String) value.values().iterator().next());
        //
        //        final int lgK = 11;
        //        CpcSketch sketch = new CpcSketch(lgK);
        //        for (int key = 0; key < 10000 * num; key++) {
        //            sketch.update(key);
        //        }
        //
        //        String encodedString = Base64.getEncoder().encodeToString(sketch.toByteArray());
        //        return encodedString;

    }

    public Object merge(Object o1, Object o2) throws Exception {
        final int lgK = 11;
        byte[] decodedBytes1 = Base64.getDecoder().decode((String) o1);
        byte[] decodedBytes2 = Base64.getDecoder().decode((String) o2);
        CpcSketch sketch1 = CpcSketch.heapify(Memory.wrap(decodedBytes1));
        CpcSketch sketch2 = CpcSketch.heapify(Memory.wrap(decodedBytes2));
        CpcUnion union = new CpcUnion(lgK);
        union.update(sketch1);
        union.update(sketch2);
        return Base64.getEncoder().encodeToString(union.getResult().toByteArray());
    }

    public Object getResult(String accumulator) throws Exception {
        final int lgK = 11;
        byte[] decodedBytes = Base64.getDecoder().decode(accumulator);
        CpcSketch sketch = CpcSketch.heapify(Memory.wrap(decodedBytes));
        CpcUnion union = new CpcUnion(lgK);
        union.update(sketch);
        return (int) union.getResult().getEstimate();
    }

}
