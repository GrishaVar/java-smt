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

import apron.Abstract1;
import apron.ApronException;
import apron.Tcons1;
import apron.Texpr0Node;
import apron.Texpr1Node;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nullable;
import org.sosy_lab.common.ShutdownNotifier;
import org.sosy_lab.java_smt.api.BooleanFormula;
import org.sosy_lab.java_smt.api.BooleanFormulaManager;
import org.sosy_lab.java_smt.api.Evaluator;
import org.sosy_lab.java_smt.api.Model;
import org.sosy_lab.java_smt.api.Model.ValueAssignment;
import org.sosy_lab.java_smt.api.ProverEnvironment;
import org.sosy_lab.java_smt.api.SolverException;
import org.sosy_lab.java_smt.basicimpl.AbstractProverWithAllSat;
import org.sosy_lab.java_smt.solvers.apron.types.ApronNode.ApronConstraint;
import org.sosy_lab.java_smt.solvers.apron.types.ApronNode;


public class ApronTheoremProver extends AbstractProverWithAllSat<Void>
    implements ProverEnvironment {

  private Abstract1 abstract1;
  private final ApronSolverContext solverContext;

  private final List<Collection<ApronConstraint>> assertedFormulas = new ArrayList<>();

  protected ApronTheoremProver(
      Set pSet,
      BooleanFormulaManager pBmgr,
      ShutdownNotifier pShutdownNotifier,
      ApronSolverContext pApronSolverContext) throws ApronException {
    super(pSet, pBmgr, pShutdownNotifier);
    this.solverContext = pApronSolverContext;
    this.abstract1 = new Abstract1(pApronSolverContext.getManager(),
        pApronSolverContext.getFormulaCreator().getEnvironment());
    this.assertedFormulas.add(new LinkedHashSet<>());
  }

  public ApronSolverContext getSolverContext() {
    return solverContext;
  }

  public List<Collection<ApronConstraint>> getAssertedFormulas() {
    return assertedFormulas;
  }

  @Override
  public ImmutableList<ValueAssignment> getModelAssignments() throws SolverException {
    Preconditions.checkState(!closed);
    return super.getModelAssignments();
  }

  @Override
  public void pop() {
    Preconditions.checkState(!closed);
    Preconditions.checkState(assertedFormulas.size() > 1);
    assertedFormulas.remove(assertedFormulas.size() - 1);
  }

  @Override
  public @Nullable Void addConstraint(BooleanFormula constraint)
      throws InterruptedException {
    Preconditions.checkState(!closed);
    ApronNode node = ApronFormulaManager.getTerm(constraint);
      if(node instanceof ApronConstraint){
        addConstraintException((ApronConstraint) node);
      } else {
        throw new IllegalArgumentException("Constraint of wrong Type!");
      }

    return null;
  }

  private void addConstraintException(ApronConstraint pConstraint) {
    try {
      for (Map.Entry<Tcons1, Texpr1Node> cons:pConstraint.getConstraintNodes().entrySet()) {
        Tcons1[] consOld = abstract1.toTcons(solverContext.getManager());
        Tcons1[] newCons = new Tcons1[consOld.length + 1];
        int i = 0;
        for (Tcons1 c : consOld) {
          c.extendEnvironment(solverContext.getFormulaCreator().getEnvironment());
          newCons[i] = c;
          i++;
        }
        newCons[consOld.length] = cons.getKey();
        this.abstract1.changeEnvironment(solverContext.getManager(),
            solverContext.getFormulaCreator().getEnvironment(),false);
        this.abstract1 = new Abstract1(solverContext.getManager(), newCons);
        Iterables.getLast(assertedFormulas).add(pConstraint);
      }
    } catch (ApronException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void push() throws InterruptedException {
    Preconditions.checkState(!closed);
    assertedFormulas.add(new LinkedHashSet<>());
  }

  @Override
  public int size() {
    Preconditions.checkState(!closed);
    return assertedFormulas.size() - 1;
  }

  @Override
  public boolean isUnsat() throws SolverException, InterruptedException {
    Preconditions.checkState(!closed);
    return isUnsatApron();
  }

  private boolean isUnsatApron() {
    try {
      return abstract1.isBottom(solverContext.getManager());
    } catch (ApronException pApronException) {
      throw new RuntimeException(pApronException);
    }
  }

  @Override
  public Model getModel() throws SolverException {
    Preconditions.checkState(!closed);
    return new ApronModel(this, solverContext.getFormulaCreator(), getAssertedExpressions());
  }

  private Collection<ApronConstraint> getAssertedExpressions() {
    List<ApronConstraint> result = new ArrayList<>();
    assertedFormulas.forEach(result::addAll);
    return result;
  }

  @Override
  public List<BooleanFormula> getUnsatCore() {
    throw new UnsupportedOperationException("Unsatcore not supported.");
  }

  @Override
  public Optional<List<BooleanFormula>> unsatCoreOverAssumptions(Collection<BooleanFormula> assumptions)
      throws SolverException, InterruptedException {
    throw new NullPointerException();
  }

  /**
   * with the help of the join() method form the Apron-library one can build a new abstract1
   * object with additional constraints
   * @param assumptions A list of literals.
   * @return if the prover is satisfiable with some additional assumptions
   * @throws SolverException
   * @throws InterruptedException
   */
  @Override
  public boolean isUnsatWithAssumptions(Collection<BooleanFormula> assumptions)
      throws SolverException, InterruptedException {
    Preconditions.checkState(!closed);
    ArrayList<Tcons1> constraints = new ArrayList<>();
    for (BooleanFormula assumption:assumptions) {
      ApronConstraint cons = (ApronConstraint) ApronFormulaManager.getTerm(assumption);
      for(Map.Entry<Tcons1, Texpr1Node> entry: cons.getConstraintNodes().entrySet()){
        constraints.add(entry.getKey());
      }
    }
    Tcons1[] tcons1s = constraints.toArray(new Tcons1[constraints.size()]);
    try {
      Abstract1 absNew = new Abstract1(solverContext.getManager(), tcons1s);
      Abstract1 result = this.abstract1.joinCopy(solverContext.getManager(), absNew);
      return result.isBottom(solverContext.getManager());
    } catch (ApronException e){
      throw new RuntimeException(e.toString());
    }
  }

  public Abstract1 getAbstract1() {
    return abstract1;
  }

  @Override
  protected Evaluator getEvaluatorWithoutChecks() throws SolverException {
    throw new UnsupportedOperationException("Apron does not support Evaluator without checks."); }

  @Override
  public void close() {
    if(!closed){
      assertedFormulas.clear();
      closed = true;
    }
    super.close();
  }
}
