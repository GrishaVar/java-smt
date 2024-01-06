// This file is part of JavaSMT,
// an API wrapper for a collection of SMT solvers:
// https://github.com/sosy-lab/java-smt
//
// SPDX-FileCopyrightText: 2024 Dirk Beyer <https://www.sosy-lab.org>
//
// SPDX-License-Identifier: Apache-2.0

package org.sosy_lab.java_smt.delegate.debugging;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.List;
import org.sosy_lab.java_smt.api.BooleanFormula;
import org.sosy_lab.java_smt.api.InterpolatingProverEnvironment;
import org.sosy_lab.java_smt.api.SolverException;
import org.sosy_lab.java_smt.delegate.debugging.DebuggingSolverContext.NodeManager;

public class DebuggingInterpolatingProverEnvironment<T> extends DebuggingBasicProverEnvironment<T>
    implements InterpolatingProverEnvironment<T> {
  private final InterpolatingProverEnvironment<T> delegate;

  public DebuggingInterpolatingProverEnvironment(
      InterpolatingProverEnvironment<T> pDelegate, NodeManager pLocalFormulas) {
    super(pDelegate, pLocalFormulas);
    delegate = checkNotNull(pDelegate);
  }

  @Override
  public BooleanFormula getInterpolant(Collection<T> formulasOfA)
      throws SolverException, InterruptedException {
    assertThreadLocal();
    // FIXME: We should probably check that the formula ids are valid
    BooleanFormula result = delegate.getInterpolant(formulasOfA);
    addFormulaToContext(result);
    return result;
  }

  @Override
  public List<BooleanFormula> getSeqInterpolants(List<? extends Collection<T>> partitionedFormulas)
      throws SolverException, InterruptedException {
    assertThreadLocal();
    List<BooleanFormula> result = delegate.getSeqInterpolants(partitionedFormulas);
    for (BooleanFormula t : result) {
      addFormulaToContext(t);
    }
    return result;
  }

  @Override
  public List<BooleanFormula> getTreeInterpolants(
      List<? extends Collection<T>> partitionedFormulas, int[] startOfSubTree)
      throws SolverException, InterruptedException {
    assertThreadLocal();
    List<BooleanFormula> result = delegate.getTreeInterpolants(partitionedFormulas, startOfSubTree);
    for (BooleanFormula t : result) {
      addFormulaToContext(t);
    }
    return result;
  }
}
