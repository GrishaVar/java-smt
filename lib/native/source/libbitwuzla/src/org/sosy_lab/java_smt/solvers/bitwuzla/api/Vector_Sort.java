// This file is part of JavaSMT,
// an API wrapper for a collection of SMT solvers:
// https://github.com/sosy-lab/java-smt
//
// SPDX-FileCopyrightText: 2024 Dirk Beyer <https://www.sosy-lab.org>
//
// SPDX-License-Identifier: Apache-2.0

/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.0
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.sosy_lab.java_smt.solvers.bitwuzla.api;

public class Vector_Sort extends java.util.AbstractList<Sort> implements java.util.RandomAccess {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected Vector_Sort(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(Vector_Sort obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(Vector_Sort obj) {
    long ptr = 0;
    if (obj != null) {
      if (!obj.swigCMemOwn)
        throw new RuntimeException("Cannot release ownership as memory is not owned");
      ptr = obj.swigCPtr;
      obj.swigCMemOwn = false;
      obj.delete();
    }
    return ptr;
  }

  @SuppressWarnings("deprecation")
  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        BitwuzlaNativeJNI.delete_Vector_Sort(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public Vector_Sort(Sort[] initialElements) {
    this();
    reserve(initialElements.length);

    for (Sort element : initialElements) {
      add(element);
    }
  }

  public Vector_Sort(Iterable<Sort> initialElements) {
    this();
    for (Sort element : initialElements) {
      add(element);
    }
  }

  public Sort get(int index) {
    return doGet(index);
  }

  public Sort set(int index, Sort e) {
    return doSet(index, e);
  }

  public boolean add(Sort e) {
    modCount++;
    doAdd(e);
    return true;
  }

  public void add(int index, Sort e) {
    modCount++;
    doAdd(index, e);
  }

  public Sort remove(int index) {
    modCount++;
    return doRemove(index);
  }

  protected void removeRange(int fromIndex, int toIndex) {
    modCount++;
    doRemoveRange(fromIndex, toIndex);
  }

  public int size() {
    return doSize();
  }

  public Vector_Sort() {
    this(BitwuzlaNativeJNI.new_Vector_Sort__SWIG_0(), true);
  }

  public Vector_Sort(Vector_Sort other) {
    this(BitwuzlaNativeJNI.new_Vector_Sort__SWIG_1(Vector_Sort.getCPtr(other), other), true);
  }

  public long capacity() {
    return BitwuzlaNativeJNI.Vector_Sort_capacity(swigCPtr, this);
  }

  public void reserve(long n) {
    BitwuzlaNativeJNI.Vector_Sort_reserve(swigCPtr, this, n);
  }

  public boolean isEmpty() {
    return BitwuzlaNativeJNI.Vector_Sort_isEmpty(swigCPtr, this);
  }

  public void clear() {
    BitwuzlaNativeJNI.Vector_Sort_clear(swigCPtr, this);
  }

  public Vector_Sort(int count, Sort value) {
    this(BitwuzlaNativeJNI.new_Vector_Sort__SWIG_2(count, Sort.getCPtr(value), value), true);
  }

  private int doSize() {
    return BitwuzlaNativeJNI.Vector_Sort_doSize(swigCPtr, this);
  }

  private void doAdd(Sort x) {
    BitwuzlaNativeJNI.Vector_Sort_doAdd__SWIG_0(swigCPtr, this, Sort.getCPtr(x), x);
  }

  private void doAdd(int index, Sort x) {
    BitwuzlaNativeJNI.Vector_Sort_doAdd__SWIG_1(swigCPtr, this, index, Sort.getCPtr(x), x);
  }

  private Sort doRemove(int index) {
    return new Sort(BitwuzlaNativeJNI.Vector_Sort_doRemove(swigCPtr, this, index), true);
  }

  private Sort doGet(int index) {
    return new Sort(BitwuzlaNativeJNI.Vector_Sort_doGet(swigCPtr, this, index), false);
  }

  private Sort doSet(int index, Sort val) {
    return new Sort(BitwuzlaNativeJNI.Vector_Sort_doSet(swigCPtr, this, index, Sort.getCPtr(val), val), true);
  }

  private void doRemoveRange(int fromIndex, int toIndex) {
    BitwuzlaNativeJNI.Vector_Sort_doRemoveRange(swigCPtr, this, fromIndex, toIndex);
  }

}
