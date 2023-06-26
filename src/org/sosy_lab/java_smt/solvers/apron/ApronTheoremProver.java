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

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nullable;
import org.sosy_lab.common.ShutdownNotifier;
import org.sosy_lab.java_smt.api.BooleanFormula;
import org.sosy_lab.java_smt.api.BooleanFormulaManager;
import org.sosy_lab.java_smt.api.Evaluator;
import org.sosy_lab.java_smt.api.Model;
import org.sosy_lab.java_smt.api.ProverEnvironment;
import org.sosy_lab.java_smt.api.SolverException;
import org.sosy_lab.java_smt.basicimpl.AbstractProverWithAllSat;

public class ApronTheoremProver extends AbstractProverWithAllSat<Void> implements ProverEnvironment {
  protected ApronTheoremProver(
      Set pSet,
      BooleanFormulaManager pBmgr,
      ShutdownNotifier pShutdownNotifier) {
    super(pSet, pBmgr, pShutdownNotifier);
  }

  @Override
  public void pop() {

  }

  @Nullable
  @Override
  public @org.checkerframework.checker.nullness.qual.Nullable Void addConstraint(BooleanFormula constraint) throws InterruptedException {
    return null;
  }

  @Override
  public void push() throws InterruptedException {

  }

  @Override
  public int size() {
    return 0;
  }

  @Override
  public boolean isUnsat() throws SolverException, InterruptedException {
    return false;
  }

  @Override
  public Model getModel() throws SolverException {
    return null;
  }

  @Override
  public List<BooleanFormula> getUnsatCore() {
    return null;
  }

  @Override
  public Optional<List<BooleanFormula>> unsatCoreOverAssumptions(Collection assumptions)
      throws SolverException, InterruptedException {
    return Optional.empty();
  }

  @Override
  public boolean isUnsatWithAssumptions(Collection assumptions)
      throws SolverException, InterruptedException {
    return false;
  }

  @Override
  protected Evaluator getEvaluatorWithoutChecks() throws SolverException {
    return null;
  }
}
