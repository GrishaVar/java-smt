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

package org.sosy_lab.java_smt.solvers.bitwuzla;

import java.math.BigInteger;
import org.sosy_lab.java_smt.basicimpl.AbstractBitvectorFormulaManager;
import org.sosy_lab.java_smt.basicimpl.AbstractBooleanFormulaManager;
import org.sosy_lab.java_smt.basicimpl.FormulaCreator;

public class BitwuzlaBitvectorFormulaManager extends
                                             AbstractBitvectorFormulaManager<Long, Long, Long, Long> {
  protected BitwuzlaBitvectorFormulaManager(
      FormulaCreator<Long, Long, Long, Long> pCreator,
      AbstractBooleanFormulaManager<Long, Long, Long, Long> pBmgr) {
    super(pCreator, pBmgr);
  }

  @Override
  protected Long makeBitvectorImpl(int length, Long pParam1) {
    return null;
  }

  @Override
  protected Long toIntegerFormulaImpl(Long pI, boolean signed) {
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
  protected Long divide(Long pParam1, Long pParam2, boolean signed) {
    return null;
  }

  @Override
  protected Long modulo(Long pParam1, Long pParam2, boolean signed) {
    return null;
  }

  @Override
  protected Long multiply(Long pParam1, Long pParam2) {
    return null;
  }

  @Override
  protected Long equal(Long pParam1, Long pParam2) {
    return null;
  }

  @Override
  protected Long greaterThan(Long pParam1, Long pParam2, boolean signed) {
    return null;
  }

  @Override
  protected Long greaterOrEquals(Long pParam1, Long pParam2, boolean signed) {
    return null;
  }

  @Override
  protected Long lessThan(Long pParam1, Long pParam2, boolean signed) {
    return null;
  }

  @Override
  protected Long lessOrEquals(Long pParam1, Long pParam2, boolean signed) {
    return null;
  }

  @Override
  protected Long not(Long pParam1) {
    return null;
  }

  @Override
  protected Long and(Long pParam1, Long pParam2) {
    return null;
  }

  @Override
  protected Long or(Long pParam1, Long pParam2) {
    return null;
  }

  @Override
  protected Long xor(Long pParam1, Long pParam2) {
    return null;
  }

  @Override
  protected Long makeBitvectorImpl(int pLength, BigInteger pI) {
    return null;
  }

  @Override
  protected Long makeVariableImpl(int pLength, String pVar) {
    return null;
  }

  @Override
  protected Long shiftRight(Long pNumber, Long toShift, boolean signed) {
    return null;
  }

  @Override
  protected Long shiftLeft(Long pExtract, Long pExtract2) {
    return null;
  }

  @Override
  protected Long concat(Long number, Long pAppend) {
    return null;
  }

  @Override
  protected Long extract(Long pNumber, int pMsb, int pLsb) {
    return null;
  }

  @Override
  protected Long extend(Long pNumber, int pExtensionBits, boolean pSigned) {
    return null;
  }
}
