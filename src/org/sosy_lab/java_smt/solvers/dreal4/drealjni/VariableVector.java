// This file is part of JavaSMT,
// an API wrapper for a collection of SMT solvers:
// https://github.com/sosy-lab/java-smt
//
// SPDX-FileCopyrightText: 2023 Dirk Beyer <https://www.sosy-lab.org>
//
// SPDX-License-Identifier: Apache-2.0

/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */
package org.sosy_lab.java_smt.solvers.dreal4.drealjni;

import com.google.common.collect.Iterables;
import java.util.Arrays;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class VariableVector extends java.util.AbstractList<Variable>
    implements java.util.RandomAccess {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected VariableVector(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(VariableVector obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(VariableVector obj) {
    long ptr = 0;
    if (obj != null) {
      if (!obj.swigCMemOwn) {
        throw new RuntimeException("Cannot release ownership as memory is not owned");
      }
      ptr = obj.swigCPtr;
      obj.swigCMemOwn = false;
      obj.delete();
    }
    return ptr;
  }

  @SuppressWarnings("deprecation")
  protected void finalize1() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        DrealJNI.deleteVariableVector(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public VariableVector(Variable[] initialElements) {
    this();
    reserve(initialElements.length);
    this.addAll(Arrays.asList(initialElements));
  }

  public VariableVector(Iterable<Variable> initialElements) {
    this();
    Iterables.addAll(this, initialElements);
  }

  @Override
  public Variable get(int index) {
    return doGet(index);
  }

  @Override
  public Variable set(int index, Variable e) {
    return doSet(index, e);
  }

  @Override
  public boolean add(Variable e) {
    modCount++;
    doAdd(e);
    return true;
  }

  @Override
  public void add(int index, Variable e) {
    modCount++;
    doAdd(index, e);
  }

  @Override
  public Variable remove(int index) {
    modCount++;
    return doRemove(index);
  }

  @Override
  protected void removeRange(int fromIndex, int toIndex) {
    modCount++;
    doRemoveRange(fromIndex, toIndex);
  }

  @Override
  public int size() {
    return doSize();
  }

  public VariableVector() {
    this(DrealJNI.newVariableVectorSWIG0(), true);
  }

  public VariableVector(VariableVector other) {
    this(DrealJNI.newVariableVectorSWIG1(VariableVector.getCPtr(other), other), true);
  }

  public long capacity() {
    return DrealJNI.variableVectorCapacity(swigCPtr, this);
  }

  public void reserve(long n) {
    DrealJNI.variableVectorReserve(swigCPtr, this, n);
  }

  @Override
  public boolean isEmpty() {
    return DrealJNI.variableVectorIsEmpty(swigCPtr, this);
  }

  @Override
  public void clear() {
    DrealJNI.variableVectorClear(swigCPtr, this);
  }

  public VariableVector(int count, Variable value) {
    this(DrealJNI.newVariableVectorSWIG2(count, Variable.getCPtr(value), value), true);
  }

  private int doSize() {
    return DrealJNI.variableVectorDoSize(swigCPtr, this);
  }

  private void doAdd(Variable x) {
    DrealJNI.variableVectorDoAddSWIG0(swigCPtr, this, Variable.getCPtr(x), x);
  }

  private void doAdd(int index, Variable x) {
    DrealJNI.variableVectorDoAddSWIG1(swigCPtr, this, index, Variable.getCPtr(x), x);
  }

  private Variable doRemove(int index) {
    return new Variable(DrealJNI.variableVectorDoRemove(swigCPtr, this, index), true);
  }

  private Variable doGet(int index) {
    return new Variable(DrealJNI.variableVectorDoGet(swigCPtr, this, index), false);
  }

  private Variable doSet(int index, Variable val) {
    return new Variable(
        DrealJNI.variableVectorDoSet(swigCPtr, this, index, Variable.getCPtr(val), val), true);
  }

  private void doRemoveRange(int fromIndex, int toIndex) {
    DrealJNI.variableVectorDoRemoveRange(swigCPtr, this, fromIndex, toIndex);
  }
}
