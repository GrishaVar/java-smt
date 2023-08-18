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

@SuppressWarnings({"unused"})
@NotThreadSafe
public class Variables {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected Variables(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(Variables obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(Variables obj) {
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
        DrealJNI.deleteVariables(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public Variables(Variables arg0) {
    this(DrealJNI.newVariablesSWIG0(Variables.getCPtr(arg0), arg0), true);
  }

  public Variables assignOperator(Variables arg0) {
    return new Variables(
        DrealJNI.variablesAssignOperatorSWIG0(swigCPtr, this, Variables.getCPtr(arg0), arg0),
        false);
  }

  public Variables() {
    this(DrealJNI.newVariablesSWIG1(), true);
  }

  public Variables(Variable[] arr) {
    this(DrealJNI.newVariablesSWIG1(), true);
    for (Variable var : arr) {
      this.insert(var);
    }
  }

  public Variables(SwigTypePStdInitializerListTDrealDrakeSymboliVariableT init) {
    this(
        DrealJNI.newVariablesSWIG2(
            SwigTypePStdInitializerListTDrealDrakeSymboliVariableT.getCPtr(init)),
        true);
  }

  public long getHash() {
    return DrealJNI.variablesGetHash(swigCPtr, this);
  }

  public long size() {
    return DrealJNI.variablesSize(swigCPtr, this);
  }

  public boolean empty() {
    return DrealJNI.variablesEmpty(swigCPtr, this);
  }

  @Override
  public String toString() {
    return DrealJNI.variablesToString(swigCPtr, this);
  }

  public SwigTypePStdSetTDrealDrakeSymbolicVariableTIterator begin() {
    return new SwigTypePStdSetTDrealDrakeSymbolicVariableTIterator(
        DrealJNI.variablesBeginSWIG0(swigCPtr, this), true);
  }

  public SwigTypePStdSetTDrealDrakeSymbolicVariableTIterator end() {
    return new SwigTypePStdSetTDrealDrakeSymbolicVariableTIterator(
        DrealJNI.variablesEndSWIG0(swigCPtr, this), true);
  }

  public SwigTypePStdSetTDrealDrakeSymbolicVariableTConstIterator cbegin() {
    return new SwigTypePStdSetTDrealDrakeSymbolicVariableTConstIterator(
        DrealJNI.variablesCbegin(swigCPtr, this), true);
  }

  public SwigTypePStdSetTDrealDrakeSymbolicVariableTConstIterator cend() {
    return new SwigTypePStdSetTDrealDrakeSymbolicVariableTConstIterator(
        DrealJNI.variablesCend(swigCPtr, this), true);
  }

  public SwigTypePStdSetTDrealDrakeSymbolicVariableTReverseIterator rbegin() {
    return new SwigTypePStdSetTDrealDrakeSymbolicVariableTReverseIterator(
        DrealJNI.variablesRbeginSWIG0(swigCPtr, this), true);
  }

  public SwigTypePStdSetTDrealDrakeSymbolicVariableTReverseIterator rend() {
    return new SwigTypePStdSetTDrealDrakeSymbolicVariableTReverseIterator(
        DrealJNI.variablesRendSWIG0(swigCPtr, this), true);
  }

  public SwigTypePStdSetTDrealDrakeSymbolicVariableTConstReverseIterator crbegin() {
    return new SwigTypePStdSetTDrealDrakeSymbolicVariableTConstReverseIterator(
        DrealJNI.variablesCrbegin(swigCPtr, this), true);
  }

  public SwigTypePStdSetTDrealDrakeSymbolicVariableTConstReverseIterator crend() {
    return new SwigTypePStdSetTDrealDrakeSymbolicVariableTConstReverseIterator(
        DrealJNI.variablesCrend(swigCPtr, this), true);
  }

  public void insert(Variable var) {
    DrealJNI.variablesInsertSWIG0(swigCPtr, this, Variable.getCPtr(var), var);
  }

  public void insert(Variables vars) {
    DrealJNI.variablesInsertSWIG2(swigCPtr, this, Variables.getCPtr(vars), vars);
  }

  public long erase(Variable key) {
    return DrealJNI.variablesEraseSWIG0(swigCPtr, this, Variable.getCPtr(key), key);
  }

  public long erase(Variables vars) {
    return DrealJNI.variablesEraseSWIG1(swigCPtr, this, Variables.getCPtr(vars), vars);
  }

  public SwigTypePStdSetTDrealDrakeSymbolicVariableTIterator find(Variable key) {
    return new SwigTypePStdSetTDrealDrakeSymbolicVariableTIterator(
        DrealJNI.variablesFindSWIG0(swigCPtr, this, Variable.getCPtr(key), key), true);
  }

  public boolean include(Variable key) {
    return DrealJNI.variablesInclude(swigCPtr, this, Variable.getCPtr(key), key);
  }

  public boolean isSubsetOf(Variables vars) {
    return DrealJNI.variablesIsSubsetOf(swigCPtr, this, Variables.getCPtr(vars), vars);
  }

  public boolean isSupersetOf(Variables vars) {
    return DrealJNI.variablesIsSupersetOf(swigCPtr, this, Variables.getCPtr(vars), vars);
  }

  public boolean isStrictSubsetOf(Variables vars) {
    return DrealJNI.variablesIsStrictSubsetOf(swigCPtr, this, Variables.getCPtr(vars), vars);
  }

  public boolean isStrictSupersetOf(Variables vars) {
    return DrealJNI.variablesIsStrictSupersetOf(swigCPtr, this, Variables.getCPtr(vars), vars);
  }
}
