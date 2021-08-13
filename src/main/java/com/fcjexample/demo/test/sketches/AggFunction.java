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
import java.util.Map;

public class AggFunction {


    public Object add(Map<String, Object> value, Object accumulator) throws Exception {

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
        String str = (String) value.values().iterator().next();
        if (accumulator == null) {
            final int lgK = 11;
            CpcSketch sketch = new CpcSketch(lgK);
            sketch.update(str);
            return Base64.getEncoder().encodeToString(sketch.toByteArray());
        }
        byte[] decodedBytes = Base64.getDecoder().decode((String) accumulator);
        CpcSketch sketch = CpcSketch.heapify(Memory.wrap(decodedBytes));
        sketch.update(str);
        return Base64.getEncoder().encodeToString(sketch.toByteArray());

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
        return String.valueOf(union.getResult().getEstimate());
    }
}
