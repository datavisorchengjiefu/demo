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

import com.fcjexample.demo.model.TestEntity;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class TestList implements TestInterface {

    @Override public void testFunction() {

    }

    @Override public int size() {
        return 0;
    }

    @Override public boolean isEmpty() {
        return false;
    }

    @Override public boolean contains(Object o) {
        return false;
    }

    @Override public Iterator<TestEntity> iterator() {
        return null;
    }

    @Override public Object[] toArray() {
        return new Object[0];
    }

    @Override public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override public boolean add(TestEntity entity) {
        return false;
    }

    @Override public boolean remove(Object o) {
        return false;
    }

    @Override public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override public boolean addAll(Collection<? extends TestEntity> c) {
        return false;
    }

    @Override public boolean addAll(int index, Collection<? extends TestEntity> c) {
        return false;
    }

    @Override public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override public void clear() {

    }

    @Override public TestEntity get(int index) {
        return null;
    }

    @Override public TestEntity set(int index, TestEntity element) {
        return null;
    }

    @Override public void add(int index, TestEntity element) {

    }

    @Override public TestEntity remove(int index) {
        return null;
    }

    @Override public int indexOf(Object o) {
        return 0;
    }

    @Override public int lastIndexOf(Object o) {
        return 0;
    }

    @Override public ListIterator<TestEntity> listIterator() {
        return null;
    }

    @Override public ListIterator<TestEntity> listIterator(int index) {
        return null;
    }

    @Override public List<TestEntity> subList(int fromIndex, int toIndex) {
        return null;
    }
}
