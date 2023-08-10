/*
 *  JavaSMT is an API wrapper for a collection of SMT solvers.
 *  This file is part of JavaSMT.
 *
 *  Copyright (C) 2007-2016  Dirk Beyer
 *  All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.sosy_lab.java_smt.solvers.dreal4.drealjni;
/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */



public class Context {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected Context(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(Context obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(Context obj) {
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
  protected void finalize1() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        drealJNI.delete_Context(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public Context() {
    this(drealJNI.new_Context__SWIG_0(), true);
  }

  public Context(Context context) {
    this(drealJNI.new_Context__SWIG_1(Context.swigRelease(context), context), true);
  }

  public Context(Config config) {
    this(drealJNI.new_Context__SWIG_2(Config.getCPtr(config), config), true);
  }

  public void Assert(Formula f) {
    drealJNI.Context_Assert(swigCPtr, this, Formula.getCPtr(f), f);
  }

  public SWIGTYPE_p_optionalT_dreal__Box_t CheckSat() {
    return new SWIGTYPE_p_optionalT_dreal__Box_t(drealJNI.Context_CheckSat(swigCPtr, this), true);
  }

  public void DeclareVariable(Variable v, boolean is_model_variable) {
    drealJNI.Context_DeclareVariable__SWIG_0(swigCPtr, this, Variable.getCPtr(v), v, is_model_variable);
  }

  public void DeclareVariable(Variable v) {
    drealJNI.Context_DeclareVariable__SWIG_1(swigCPtr, this, Variable.getCPtr(v), v);
  }

  public void DeclareVariable(Variable v, Expression lb, Expression ub, boolean is_model_variable) {
    drealJNI.Context_DeclareVariable__SWIG_2(swigCPtr, this, Variable.getCPtr(v), v, Expression.getCPtr(lb), lb, Expression.getCPtr(ub), ub, is_model_variable);
  }

  public void DeclareVariable(Variable v, Expression lb, Expression ub) {
    drealJNI.Context_DeclareVariable__SWIG_3(swigCPtr, this, Variable.getCPtr(v), v, Expression.getCPtr(lb), lb, Expression.getCPtr(ub), ub);
  }

  public static void Exit() {
    drealJNI.Context_Exit();
  }

  public void Minimize(Expression f) {
    drealJNI.Context_Minimize__SWIG_0(swigCPtr, this, Expression.getCPtr(f), f);
  }

  public void Minimize(ExpressionVector functions) {
    drealJNI.Context_Minimize__SWIG_1(swigCPtr, this, ExpressionVector.getCPtr(functions), functions);
  }

  public void Maximize(Expression f) {
    drealJNI.Context_Maximize(swigCPtr, this, Expression.getCPtr(f), f);
  }

  public void Pop(int n) {
    drealJNI.Context_Pop(swigCPtr, this, n);
  }

  public void Push(int n) {
    drealJNI.Context_Push(swigCPtr, this, n);
  }

  public void SetInfo(String key, double val) {
    drealJNI.Context_SetInfo__SWIG_0(swigCPtr, this, key, val);
  }

  public void SetInfo(String key, String val) {
    drealJNI.Context_SetInfo__SWIG_1(swigCPtr, this, key, val);
  }

  public void SetInterval(Variable v, double lb, double ub) {
    drealJNI.Context_SetInterval(swigCPtr, this, Variable.getCPtr(v), v, lb, ub);
  }

  public void SetLogic(Logic logic) {
    drealJNI.Context_SetLogic(swigCPtr, this, logic.swigValue());
  }

  public void SetOption(String key, double val) {
    drealJNI.Context_SetOption__SWIG_0(swigCPtr, this, key, val);
  }

  public void SetOption(String key, String val) {
    drealJNI.Context_SetOption__SWIG_1(swigCPtr, this, key, val);
  }

  public SWIGTYPE_p_optionalT_std__string_t GetOption(String key) {
    return new SWIGTYPE_p_optionalT_std__string_t(drealJNI.Context_GetOption(swigCPtr, this, key), true);
  }

  public Config config() {
    return new Config(drealJNI.Context_config(swigCPtr, this), false);
  }

  public Config mutable_config() {
    return new Config(drealJNI.Context_mutable_config(swigCPtr, this), false);
  }

  public static String version() {
    return drealJNI.Context_version();
  }

  public SWIGTYPE_p_ScopedVectorT_dreal__drake__symbolic__Formula_t assertions() {
    return new SWIGTYPE_p_ScopedVectorT_dreal__drake__symbolic__Formula_t(drealJNI.Context_assertions(swigCPtr, this), false);
  }

  public Box box() {
    return new Box(drealJNI.Context_box(swigCPtr, this), false);
  }

  public Box get_model() {
    return new Box(drealJNI.Context_get_model(swigCPtr, this), false);
  }

  // self written
  public boolean CheckSat(Box box) {
    return drealJNI.Context_CheckSat_0(Context.getCPtr(this), Box.getCPtr(box));
  }

  // This is only needed, if it is not possible to retrieve a Variable from Variables
  public void declareVaribales(Formula f) {
    drealJNI.Context_declareVariables(Context.getCPtr(this), Formula.getCPtr(f));
  }

  // For Testing
  public Box CheckSatBox() {
    return new Box(drealJNI.Context_CheckSatBox(Context.getCPtr(this)), true);
  }

}
