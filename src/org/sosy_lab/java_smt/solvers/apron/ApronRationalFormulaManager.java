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

package org.sosy_lab.java_smt.solvers.apron;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import org.sosy_lab.common.rationals.Rational;
import org.sosy_lab.java_smt.api.BooleanFormula;
import org.sosy_lab.java_smt.api.NumeralFormula;
import org.sosy_lab.java_smt.api.NumeralFormula.IntegerFormula;
import org.sosy_lab.java_smt.api.NumeralFormula.RationalFormula;
import org.sosy_lab.java_smt.api.RationalFormulaManager;
import org.sosy_lab.java_smt.basicimpl.FormulaCreator;

public class ApronRationalFormulaManager extends ApronNumeralFormulaManager<NumeralFormula,
    RationalFormula> implements RationalFormulaManager {
  protected ApronRationalFormulaManager(
      FormulaCreator<Long, Long, Long, Long> pCreator,
      NonLinearArithmetic pNonLinearArithmetic) {
    super(pCreator, pNonLinearArithmetic);
  }

  @Override
  public RationalFormula makeNumber(long number) {
    return null;
  }

  @Override
  public RationalFormula makeNumber(BigInteger number) {
    return null;
  }

  @Override
  public RationalFormula makeNumber(double number) {
    return null;
  }

  @Override
  public RationalFormula makeNumber(BigDecimal number) {
    return null;
  }

  @Override
  public RationalFormula makeNumber(String pI) {
    return null;
  }

  @Override
  public RationalFormula makeNumber(Rational pRational) {
    return null;
  }

  @Override
  public RationalFormula makeVariable(String pVar) {
    return null;
  }

  @Override
  public RationalFormula negate(NumeralFormula number) {
    return null;
  }

  @Override
  public RationalFormula add(NumeralFormula number1, NumeralFormula number2) {
    return null;
  }

  @Override
  public RationalFormula sum(List operands) {
    return null;
  }

  @Override
  public RationalFormula subtract(NumeralFormula number1, NumeralFormula number2) {
    return null;
  }

  @Override
  public RationalFormula divide(NumeralFormula numerator, NumeralFormula denumerator) {
    return null;
  }

  @Override
  public RationalFormula multiply(NumeralFormula number1, NumeralFormula number2) {
    return null;
  }

  @Override
  public BooleanFormula equal(NumeralFormula number1, NumeralFormula number2) {
    return null;
  }

  @Override
  public BooleanFormula distinct(List pNumbers) {
    return null;
  }

  @Override
  public BooleanFormula greaterThan(NumeralFormula number1, NumeralFormula number2) {
    return null;
  }

  @Override
  public BooleanFormula greaterOrEquals(NumeralFormula number1, NumeralFormula number2) {
    return null;
  }

  @Override
  public BooleanFormula lessThan(NumeralFormula number1, NumeralFormula number2) {
    return null;
  }

  @Override
  public BooleanFormula lessOrEquals(NumeralFormula number1, NumeralFormula number2) {
    return null;
  }

  @Override
  public IntegerFormula floor(NumeralFormula formula) {
    return null;
  }
}
