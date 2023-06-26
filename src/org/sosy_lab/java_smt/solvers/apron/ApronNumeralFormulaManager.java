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
import org.sosy_lab.java_smt.api.FormulaType;
import org.sosy_lab.java_smt.api.NumeralFormula;
import org.sosy_lab.java_smt.basicimpl.AbstractNumeralFormulaManager;
import org.sosy_lab.java_smt.basicimpl.FormulaCreator;

public class ApronNumeralFormulaManager<
    ParamFormulaType extends NumeralFormula, ResultFormulaType extends NumeralFormula>
    extends AbstractNumeralFormulaManager<
    Long, Long, Long, ParamFormulaType, ResultFormulaType, Long> {
  protected ApronNumeralFormulaManager(
      FormulaCreator<Long, Long, Long, Long> pCreator,
      NonLinearArithmetic pNonLinearArithmetic) {
    super(pCreator, pNonLinearArithmetic);
  }

  @Override
  public FormulaType<ResultFormulaType> getFormulaType() {
    return null;
  }

  @Override
  protected boolean isNumeral(Long val) {
    return false;
  }

  @Override
  protected Long makeNumberImpl(long i) {
    return null;
  }

  @Override
  protected Long makeNumberImpl(BigInteger i) {
    return null;
  }

  @Override
  protected Long makeNumberImpl(String i) {
    return null;
  }

  @Override
  protected Long makeNumberImpl(double pNumber) {
    return null;
  }

  @Override
  protected Long makeNumberImpl(BigDecimal pNumber) {
    return null;
  }

  @Override
  protected Long makeVariableImpl(String i) {
    return null;
  }

  @Override
  protected Long negate(Long pParam1) {
    return null;
  }

  @Override
  protected Long add(Long pParam1, Long pParam2) {
    return null;
  }

  @Override
  protected Long subtract(Long pParam1, Long pParam2) {
    return null;
  }

  @Override
  protected Long equal(Long pParam1, Long pParam2) {
    return null;
  }

  @Override
  protected Long distinctImpl(List<Long> pNumbers) {
    return null;
  }

  @Override
  protected Long greaterThan(Long pParam1, Long pParam2) {
    return null;
  }

  @Override
  protected Long greaterOrEquals(Long pParam1, Long pParam2) {
    return null;
  }

  @Override
  protected Long lessThan(Long pParam1, Long pParam2) {
    return null;
  }

  @Override
  protected Long lessOrEquals(Long pParam1, Long pParam2) {
    return null;
  }
}
