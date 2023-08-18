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

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class VariableSet extends java.util.AbstractSet<Variable> {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected VariableSet(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(VariableSet obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(VariableSet obj) {
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
        DrealJNI.deleteVariableSet(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  @SuppressWarnings("unused")
  public VariableSet(java.util.Collection<? extends Variable> collection) {
    this();
    var unused = addAll(collection);
  }

  @Override
  public int size() {
    return sizeImpl();
  }

  @Override
  public boolean add(Variable key) {
    return addImpl(key);
  }

  @Override
  public boolean addAll(java.util.Collection<? extends Variable> collection) {
    boolean didAddElement = false;
    for (java.lang.Object object : collection) {
      didAddElement = add((Variable) object);
    }

    return didAddElement;
  }

  @Override
  public java.util.Iterator<Variable> iterator() {
    return new java.util.Iterator<Variable>() {
      private Iterator curr;
      private Iterator end;

      private java.util.Iterator<Variable> init() {
        curr = VariableSet.this.begin();
        end = VariableSet.this.end();
        return this;
      }

      @Override
      public Variable next() {
        if (!hasNext()) {
          throw new java.util.NoSuchElementException();
        }

        // Save the current position, increment it,
        // then return the value at the position before the increment.
        final Variable currValue = curr.derefUnchecked();
        curr.incrementUnchecked();
        return currValue;
      }

      @Override
      public boolean hasNext() {
        return curr.isNot(end);
      }

      @Override
      public void remove() {
        throw new java.lang.UnsupportedOperationException();
      }
    }.init();
  }

  @Override
  public boolean containsAll(java.util.Collection<?> collection) {
    for (java.lang.Object object : collection) {
      if (!contains(object)) {
        return false;
      }
    }

    return true;
  }

  @Override
  public boolean contains(java.lang.Object object) {
    if (!(object instanceof Variable)) {
      return false;
    }

    return containsImpl((Variable) object);
  }

  @Override
  public boolean removeAll(java.util.Collection<?> collection) {
    boolean didRemoveElement = false;
    for (java.lang.Object object : collection) {
      didRemoveElement = remove(object);
    }

    return didRemoveElement;
  }

  @Override
  public boolean remove(java.lang.Object object) {
    if (!(object instanceof Variable)) {
      return false;
    }

    return removeImpl((Variable) object);
  }
  @NotThreadSafe
  protected static class Iterator {
    private transient long swigCPtr;
    protected transient boolean swigCMemOwn;

    protected Iterator(long cPtr, boolean cMemoryOwn) {
      swigCMemOwn = cMemoryOwn;
      swigCPtr = cPtr;
    }

    protected static long getCPtr(Iterator obj) {
      return (obj == null) ? 0 : obj.swigCPtr;
    }

    protected static long swigRelease(Iterator obj) {
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
          DrealJNI.deleteVariableSetIterator(swigCPtr);
        }
        swigCPtr = 0;
      }
    }

    private void incrementUnchecked() {
      DrealJNI.variableSetIteratorIncrementUnchecked(swigCPtr, this);
    }

    private Variable derefUnchecked() {
      return new Variable(DrealJNI.variableSetIteratorDerefUnchecked(swigCPtr, this), true);
    }

    private boolean isNot(VariableSet.Iterator other) {
      return DrealJNI.variableSetIteratorIsNot(
          swigCPtr, this, VariableSet.Iterator.getCPtr(other), other);
    }
  }

  public VariableSet() {
    this(DrealJNI.newVariableSetSWIG0(), true);
  }

  public VariableSet(VariableSet other) {
    this(DrealJNI.newVariableSetSWIG1(VariableSet.getCPtr(other), other), true);
  }

  @Override
  public boolean isEmpty() {
    return DrealJNI.variableSetIsEmpty(swigCPtr, this);
  }

  @Override
  public void clear() {
    DrealJNI.variableSetClear(swigCPtr, this);
  }

  private VariableSet.Iterator begin() {
    return new VariableSet.Iterator(DrealJNI.variableSetBegin(swigCPtr, this), true);
  }

  private VariableSet.Iterator end() {
    return new VariableSet.Iterator(DrealJNI.variableSetEnd(swigCPtr, this), true);
  }

  public boolean addImpl(Variable key) {
    return DrealJNI.variableSetAddImpl(swigCPtr, this, Variable.getCPtr(key), key);
  }

  private boolean containsImpl(Variable key) {
    return DrealJNI.variableSetContainsImpl(swigCPtr, this, Variable.getCPtr(key), key);
  }

  private boolean removeImpl(Variable key) {
    return DrealJNI.variableSetRemoveImpl(swigCPtr, this, Variable.getCPtr(key), key);
  }

  private int sizeImpl() {
    return DrealJNI.variableSetSizeImpl(swigCPtr, this);
  }

  @SuppressWarnings("unused")
  private boolean hasNextImpl(VariableSet.Iterator itr) {
    return DrealJNI.variableSetHasNextImpl(swigCPtr, this, VariableSet.Iterator.getCPtr(itr), itr);
  }
}
