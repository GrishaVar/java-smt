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
public class ExpressionExpressionMap extends java.util.AbstractMap<Expression, Expression> {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected ExpressionExpressionMap(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(ExpressionExpressionMap obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(ExpressionExpressionMap obj) {
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
        DrealJNI.deleteExpressionExpressionMap(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  @Override
  public int size() {
    return sizeImpl();
  }

  @Override
  public boolean containsKey(java.lang.Object key) {
    if (!(key instanceof Expression)) {
      return false;
    }

    return containsImpl((Expression) key);
  }

  @Override
  public Expression get(java.lang.Object key) {
    if (!(key instanceof Expression)) {
      return null;
    }

    Iterator itr = find((Expression) key);
    if (itr.isNot(end())) {
      return itr.getValue();
    }

    return null;
  }

  @Override
  public Expression put(Expression key, Expression value) {
    Iterator itr = find(key);
    if (itr.isNot(end())) {
      Expression oldValue = itr.getValue();
      itr.setValue(value);
      return oldValue;
    } else {
      putUnchecked(key, value);
      return null;
    }
  }

  @Override
  public Expression remove(java.lang.Object key) {
    if (!(key instanceof Expression)) {
      return null;
    }

    Iterator itr = find((Expression) key);
    if (itr.isNot(end())) {
      Expression oldValue = itr.getValue();
      removeUnchecked(itr);
      return oldValue;
    } else {
      return null;
    }
  }

  @Override
  public java.util.Set<Entry<Expression, Expression>> entrySet() {
    java.util.Set<Entry<Expression, Expression>> setToReturn = new java.util.HashSet<>();

    Iterator itr = begin();
    final Iterator end = end();
    while (itr.isNot(end)) {
      setToReturn.add(
          new Entry<Expression, Expression>() {
            private Iterator iterator;

            private Entry<Expression, Expression> init(Iterator iter) {
              this.iterator = iter;
              return this;
            }

            @Override
            public Expression getKey() {
              return iterator.getKey();
            }

            @Override
            public Expression getValue() {
              return iterator.getValue();
            }

            @Override
            public Expression setValue(Expression newValue) {
              Expression oldValue = iterator.getValue();
              iterator.setValue(newValue);
              return oldValue;
            }
          }.init(itr));
      itr = itr.getNextUnchecked();
    }

    return setToReturn;
  }

  public ExpressionExpressionMap() {
    this(DrealJNI.newExpressionExpressionMapSWIG0(), true);
  }

  public ExpressionExpressionMap(ExpressionExpressionMap other) {
    this(
        DrealJNI.newExpressionExpressionMapSWIG1(ExpressionExpressionMap.getCPtr(other), other),
        true);
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
          DrealJNI.deleteExpressionExpressionMapIterator(swigCPtr);
        }
        swigCPtr = 0;
      }
    }

    private ExpressionExpressionMap.Iterator getNextUnchecked() {
      return new ExpressionExpressionMap.Iterator(
          DrealJNI.expressionExpressionMapIteratorGetNextUnchecked(swigCPtr, this), true);
    }

    private boolean isNot(ExpressionExpressionMap.Iterator other) {
      return DrealJNI.expressionExpressionMapIteratorIsNot(
          swigCPtr, this, ExpressionExpressionMap.Iterator.getCPtr(other), other);
    }

    private Expression getKey() {
      return new Expression(DrealJNI.expressionExpressionMapIteratorGetKey(swigCPtr, this), true);
    }

    private Expression getValue() {
      return new Expression(DrealJNI.expressionExpressionMapIteratorGetValue(swigCPtr, this), true);
    }

    private void setValue(Expression newValue) {
      DrealJNI.expressionExpressionMapIteratorSetValue(
          swigCPtr, this, Expression.getCPtr(newValue), newValue);
    }
  }

  @Override
  public boolean isEmpty() {
    return DrealJNI.expressionExpressionMapIsEmpty(swigCPtr, this);
  }

  @Override
  public void clear() {
    DrealJNI.expressionExpressionMapClear(swigCPtr, this);
  }

  private ExpressionExpressionMap.Iterator find(Expression key) {
    return new ExpressionExpressionMap.Iterator(
        DrealJNI.expressionExpressionMapFind(swigCPtr, this, Expression.getCPtr(key), key), true);
  }

  private ExpressionExpressionMap.Iterator begin() {
    return new ExpressionExpressionMap.Iterator(
        DrealJNI.expressionExpressionMapBegin(swigCPtr, this), true);
  }

  private ExpressionExpressionMap.Iterator end() {
    return new ExpressionExpressionMap.Iterator(
        DrealJNI.expressionExpressionMapEnd(swigCPtr, this), true);
  }

  private int sizeImpl() {
    return DrealJNI.expressionExpressionMapSizeImpl(swigCPtr, this);
  }

  private boolean containsImpl(Expression key) {
    return DrealJNI.expressionExpressionMapContainsImpl(
        swigCPtr, this, Expression.getCPtr(key), key);
  }

  private void putUnchecked(Expression key, Expression value) {
    DrealJNI.expressionExpressionMapPutUnchecked(
        swigCPtr, this, Expression.getCPtr(key), key, Expression.getCPtr(value), value);
  }

  private void removeUnchecked(ExpressionExpressionMap.Iterator itr) {
    DrealJNI.expressionExpressionMapRemoveUnchecked(
        swigCPtr, this, ExpressionExpressionMap.Iterator.getCPtr(itr), itr);
  }
}
