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
public class Expression {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected Expression(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(Expression obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(Expression obj) {
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
        DrealJNI.deleteExpression(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public Expression(Expression arg0) {
    this(DrealJNI.newExpressionSWIG0(Expression.getCPtr(arg0), arg0), true);
  }

  public Expression assignOperator(Expression arg0) {
    return new Expression(
        DrealJNI.expressionAssignOperatorSWIG0(swigCPtr, this, Expression.getCPtr(arg0), arg0),
        false);
  }

  public Expression() {
    this(DrealJNI.newExpressionSWIG1(), true);
  }

  public Expression(double d) {
    this(DrealJNI.newExpressionSWIG2(d), true);
  }

  public Expression(long l) {
    this(DrealJNI.newExpressionSWIG4(l), true);
  }

  public Expression(Variable var) {
    this(DrealJNI.newExpressionSWIG3(Variable.getCPtr(var), var), true);
  }

  public ExpressionKind getKind() {
    return ExpressionKind.swigToEnum(DrealJNI.expressionGetKind(swigCPtr, this));
  }

  public long getHash() {
    return DrealJNI.expressionGetHash(swigCPtr, this);
  }

  public Variables expressionGetVariables() {
    return new Variables(DrealJNI.expressionGetVariables(swigCPtr, this), false);
  }

  public boolean equalTo(Expression e) {
    return DrealJNI.expressionEqualTo(swigCPtr, this, Expression.getCPtr(e), e);
  }

  public boolean less(Expression e) {
    return DrealJNI.expressionLess(swigCPtr, this, Expression.getCPtr(e), e);
  }

  public boolean isPolynomial() {
    return DrealJNI.expressionIsPolynomial(swigCPtr, this);
  }

  public boolean includeIte() {
    return DrealJNI.expressionIncludeIte(swigCPtr, this);
  }

  public double evaluate(Environment env) {
    return DrealJNI.expressionEvaluateSWIG0(swigCPtr, this, Environment.getCPtr(env), env);
  }

  public double evaluate() {
    return DrealJNI.expressionEvaluateSWIG1(swigCPtr, this);
  }

  public Expression evaluatePartial(Environment env) {
    return new Expression(
        DrealJNI.expressionEvaluatePartial(swigCPtr, this, Environment.getCPtr(env), env), true);
  }

  public Expression expand() {
    return new Expression(DrealJNI.expressionExpand(swigCPtr, this), true);
  }

  public Expression substitute(Variable var, Expression e) {
    return new Expression(
        DrealJNI.expressionSubstituteSWIG0(
            swigCPtr, this, Variable.getCPtr(var), var, Expression.getCPtr(e), e),
        true);
  }

  public Expression substitute(
      SwigTypePStdUnorderedMapVariableExpressionHashValueVariable exprSubst,
      SwigTypePStdUnorderedMapVariableFormulaHashValueVariable formulaSubst) {
    return new Expression(
        DrealJNI.expressionSubstituteSWIG1(
            swigCPtr,
            this,
            SwigTypePStdUnorderedMapVariableExpressionHashValueVariable.getCPtr(exprSubst),
            SwigTypePStdUnorderedMapVariableFormulaHashValueVariable.getCPtr(formulaSubst)),
        true);
  }

  public Expression substitute(
      SwigTypePStdUnorderedMapVariableExpressionHashValueVariable exprSubst) {
    return new Expression(
        DrealJNI.expressionSubstituteSWIG2(
            swigCPtr,
            this,
            SwigTypePStdUnorderedMapVariableExpressionHashValueVariable.getCPtr(exprSubst)),
        true);
  }

  public Expression substitute(
      SwigTypePStdUnorderedMapVariableFormulaHashValueVariable formulaSubst) {
    return new Expression(
        DrealJNI.expressionSubstituteSWIG3(
            swigCPtr,
            this,
            SwigTypePStdUnorderedMapVariableFormulaHashValueVariable.getCPtr(formulaSubst)),
        true);
  }

  public Expression differentiate(Variable x) {
    return new Expression(
        DrealJNI.expressionDifferentiate(swigCPtr, this, Variable.getCPtr(x), x), true);
  }

  @Override
  public String toString() {
    return DrealJNI.expressionToString(swigCPtr, this);
  }

  public static Expression zero() {
    return new Expression(DrealJNI.expressionZero(), true);
  }

  public static Expression one() {
    return new Expression(DrealJNI.expressionOne(), true);
  }

  public static Expression pi() {
    return new Expression(DrealJNI.expressionPi(), true);
  }

  public static Expression e() {
    return new Expression(DrealJNI.expressionE(), true);
  }

  public static Expression naN() {
    return new Expression(DrealJNI.expressionNaN(), true);
  }

  public Expression increment() {
    return new Expression(DrealJNI.expressionIncrementSWIG0(swigCPtr, this), false);
  }

  public Expression increment(int arg0) {
    return new Expression(DrealJNI.expressionIncrementSWIG1(swigCPtr, this, arg0), true);
  }

  public Expression decrement() {
    return new Expression(DrealJNI.expressionDecrementSWIG0(swigCPtr, this), false);
  }

  public Expression decrement(int arg0) {
    return new Expression(DrealJNI.expressionDecrementSWIG1(swigCPtr, this, arg0), true);
  }

  // self written
  public VariableSet getVariables() {
    VariableSet set = new VariableSet();
    DrealJNI.getVariables(VariableSet.getCPtr(set), Expression.getCPtr(this));
    return set;
  }
}
