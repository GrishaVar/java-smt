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
package org.sosy_lab.java_smt.test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.TruthJUnit.assume;
import static org.sosy_lab.java_smt.api.FormulaType.BooleanType;
import static org.sosy_lab.java_smt.api.FormulaType.IntegerType;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.testing.EqualsTester;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.sosy_lab.java_smt.SolverContextFactory.Solvers;
import org.sosy_lab.java_smt.api.BitvectorFormula;
import org.sosy_lab.java_smt.api.BooleanFormula;
import org.sosy_lab.java_smt.api.FormulaType;
import org.sosy_lab.java_smt.api.FunctionDeclaration;
import org.sosy_lab.java_smt.api.NumeralFormula.IntegerFormula;
import org.sosy_lab.java_smt.api.SolverException;

@RunWith(Parameterized.class)
public class FormulaManagerTest extends SolverBasedTest0 {

  @Parameters(name = "{0}")
  public static Object[] getAllSolvers() {
    return Solvers.values();
  }

  @Parameter(0)
  public Solvers solver;

  @Override
  protected Solvers solverToUse() {
    return solver;
  }

  @Test
  public void testEmptySubstitution() throws SolverException, InterruptedException {
    // Boolector does not support substitution
    assume().that(solverToUse()).isNotEqualTo(Solvers.BOOLECTOR);
    assume().withMessage("Princess fails").that(solver).isNotEqualTo(Solvers.PRINCESS);

    IntegerFormula variable1 = imgr.makeVariable("variable1");
    IntegerFormula variable2 = imgr.makeVariable("variable2");
    IntegerFormula variable3 = imgr.makeVariable("variable3");
    IntegerFormula variable4 = imgr.makeVariable("variable4");

    FunctionDeclaration<BooleanFormula> uf2Decl =
        fmgr.declareUF("uf", BooleanType, IntegerType, IntegerType);
    BooleanFormula f1 = fmgr.callUF(uf2Decl, variable1, variable3);
    BooleanFormula f2 = fmgr.callUF(uf2Decl, variable2, variable4);
    BooleanFormula input = bmgr.xor(f1, f2);

    BooleanFormula out = mgr.substitute(input, ImmutableMap.of());
    assertThatFormula(out).isEquivalentTo(input);
  }

  @Test
  public void testNoSubstitution() throws SolverException, InterruptedException {
    // Boolector does not support substitution
    assume().that(solverToUse()).isNotEqualTo(Solvers.BOOLECTOR);
    assume().withMessage("Princess fails").that(solver).isNotEqualTo(Solvers.PRINCESS);

    IntegerFormula variable1 = imgr.makeVariable("variable1");
    IntegerFormula variable2 = imgr.makeVariable("variable2");
    IntegerFormula variable3 = imgr.makeVariable("variable3");
    IntegerFormula variable4 = imgr.makeVariable("variable4");

    FunctionDeclaration<BooleanFormula> uf2Decl =
        fmgr.declareUF("uf", BooleanType, IntegerType, IntegerType);
    BooleanFormula f1 = fmgr.callUF(uf2Decl, variable1, variable3);
    BooleanFormula f2 = fmgr.callUF(uf2Decl, variable2, variable4);
    BooleanFormula input = bmgr.xor(f1, f2);

    Map<BooleanFormula, BooleanFormula> substitution =
        ImmutableMap.of(
            bmgr.makeVariable("a"), bmgr.makeVariable("a1"),
            bmgr.makeVariable("b"), bmgr.makeVariable("b1"),
            bmgr.and(bmgr.makeVariable("c"), bmgr.makeVariable("d")), bmgr.makeVariable("e"));

    BooleanFormula out = mgr.substitute(input, substitution);
    assertThatFormula(out).isEquivalentTo(input);
  }

  @Test
  public void testSubstitution() throws SolverException, InterruptedException {
    // Boolector does not support substitution
    assume().that(solverToUse()).isNotEqualTo(Solvers.BOOLECTOR);
    BooleanFormula input =
        bmgr.or(
            bmgr.and(bmgr.makeVariable("a"), bmgr.makeVariable("b")),
            bmgr.and(bmgr.makeVariable("c"), bmgr.makeVariable("d")));
    BooleanFormula out =
        mgr.substitute(
            input,
            ImmutableMap.of(
                bmgr.makeVariable("a"), bmgr.makeVariable("a1"),
                bmgr.makeVariable("b"), bmgr.makeVariable("b1"),
                bmgr.and(bmgr.makeVariable("c"), bmgr.makeVariable("d")), bmgr.makeVariable("e")));
    assertThatFormula(out)
        .isEquivalentTo(
            bmgr.or(
                bmgr.and(bmgr.makeVariable("a1"), bmgr.makeVariable("b1")),
                bmgr.makeVariable("e")));
  }

  @Test
  public void testSubstitutionTwice() throws SolverException, InterruptedException {
    // Boolector does not support substitution
    assume().that(solverToUse()).isNotEqualTo(Solvers.BOOLECTOR);
    BooleanFormula input =
        bmgr.or(
            bmgr.and(bmgr.makeVariable("a"), bmgr.makeVariable("b")),
            bmgr.and(bmgr.makeVariable("c"), bmgr.makeVariable("d")));
    ImmutableMap<BooleanFormula, BooleanFormula> substitution =
        ImmutableMap.of(
            bmgr.makeVariable("a"), bmgr.makeVariable("a1"),
            bmgr.makeVariable("b"), bmgr.makeVariable("b1"),
            bmgr.and(bmgr.makeVariable("c"), bmgr.makeVariable("d")), bmgr.makeVariable("e"));
    BooleanFormula out = mgr.substitute(input, substitution);
    assertThatFormula(out)
        .isEquivalentTo(
            bmgr.or(
                bmgr.and(bmgr.makeVariable("a1"), bmgr.makeVariable("b1")),
                bmgr.makeVariable("e")));

    BooleanFormula out2 = mgr.substitute(out, substitution);
    assertThatFormula(out2).isEquivalentTo(out);
  }

  @Test
  public void formulaEqualsAndHashCode() {
    // Solvers without integers (Boolector) get their own test below
    assume().that(solverToUse()).isNotEqualTo(Solvers.BOOLECTOR);
    FunctionDeclaration<IntegerFormula> fb =
        fmgr.declareUF("f_b", FormulaType.IntegerType, FormulaType.IntegerType);

    new EqualsTester()
        .addEqualityGroup(bmgr.makeBoolean(true))
        .addEqualityGroup(bmgr.makeBoolean(false))
        .addEqualityGroup(bmgr.makeVariable("bool_a"))
        .addEqualityGroup(imgr.makeVariable("int_a"))

        // Way of creating numbers should not make a difference.
        .addEqualityGroup(
            imgr.makeNumber(0.0),
            imgr.makeNumber(0L),
            imgr.makeNumber(BigInteger.ZERO),
            imgr.makeNumber(BigDecimal.ZERO),
            imgr.makeNumber("0"))
        .addEqualityGroup(
            imgr.makeNumber(1.0),
            imgr.makeNumber(1L),
            imgr.makeNumber(BigInteger.ONE),
            imgr.makeNumber(BigDecimal.ONE),
            imgr.makeNumber("1"))

        // The same formula when created twice should compare equal.
        .addEqualityGroup(bmgr.makeVariable("bool_b"), bmgr.makeVariable("bool_b"))
        .addEqualityGroup(
            bmgr.and(bmgr.makeVariable("bool_a"), bmgr.makeVariable("bool_b")),
            bmgr.and(bmgr.makeVariable("bool_a"), bmgr.makeVariable("bool_b")))
        .addEqualityGroup(
            imgr.equal(imgr.makeNumber(0), imgr.makeVariable("int_a")),
            imgr.equal(imgr.makeNumber(0), imgr.makeVariable("int_a")))

        // UninterpretedFunctionDeclarations should not compare equal to Formulas,
        // but declaring one twice needs to return the same UIF.
        .addEqualityGroup(
            fmgr.declareUF("f_a", FormulaType.IntegerType, FormulaType.IntegerType),
            fmgr.declareUF("f_a", FormulaType.IntegerType, FormulaType.IntegerType))
        .addEqualityGroup(fb)
        .addEqualityGroup(fmgr.callUF(fb, imgr.makeNumber(0)))
        .addEqualityGroup(
            fmgr.callUF(fb, imgr.makeNumber(1)),
            fmgr.callUF(fb, imgr.makeNumber(1)))
        .testEquals();
  }

  @Test
  public void bitvectorFormulaEqualsAndHashCode() {
    // Boolector does not support integers and its easier to make a new test with bvs
    assume().that(solverToUse()).isEqualTo(Solvers.BOOLECTOR);
    FunctionDeclaration<BitvectorFormula> fb =
        fmgr.declareUF(
            "f_bv",
            FormulaType.getBitvectorTypeWithSize(8),
            FormulaType.getBitvectorTypeWithSize(8));

    new EqualsTester()
        .addEqualityGroup(bmgr.makeBoolean(true))
        .addEqualityGroup(bmgr.makeBoolean(false))
        .addEqualityGroup(bmgr.makeVariable("bool_a"))
        .addEqualityGroup(bvmgr.makeVariable(8, "bv_a"))

        // Way of creating numbers should not make a difference.
        .addEqualityGroup(
            bvmgr.makeBitvector(8, 0L),
            bvmgr.makeBitvector(8, 0),
            bvmgr.makeBitvector(8, BigInteger.ZERO))
        .addEqualityGroup(
            bvmgr.makeBitvector(8, 1L),
            bvmgr.makeBitvector(8, 1),
            bvmgr.makeBitvector(8, BigInteger.ONE))
        // The same formula when created twice should compare equal.
        .addEqualityGroup(bmgr.makeVariable("bool_b"), bmgr.makeVariable("bool_b"))
        .addEqualityGroup(
            bmgr.and(bmgr.makeVariable("bool_a"), bmgr.makeVariable("bool_b")),
            bmgr.and(bmgr.makeVariable("bool_a"), bmgr.makeVariable("bool_b")))
        .addEqualityGroup(
            bvmgr.equal(bvmgr.makeBitvector(8, 0), bvmgr.makeVariable(8, "int_a")),
            bvmgr.equal(bvmgr.makeBitvector(8, 0), bvmgr.makeVariable(8, "int_a")))

        // UninterpretedFunctionDeclarations should not compare equal to Formulas,
        // but declaring one twice needs to return the same UIF.
        .addEqualityGroup(
            fmgr.declareUF(
                "f_a",
                FormulaType.getBitvectorTypeWithSize(8),
                FormulaType.getBitvectorTypeWithSize(8)),
            fmgr.declareUF(
                "f_a",
                FormulaType.getBitvectorTypeWithSize(8),
                FormulaType.getBitvectorTypeWithSize(8)))
        .addEqualityGroup(fb)
        .addEqualityGroup(fmgr.callUF(fb, bvmgr.makeBitvector(8, 0)))
        .addEqualityGroup(
            fmgr.callUF(fb, bvmgr.makeBitvector(8, 1)), // why not equal?!
            fmgr.callUF(fb, bvmgr.makeBitvector(8, 1)))
        .testEquals();
  }

  @Test
  public void variableNameExtractorTest() {
    // Since Boolector does not support integers we use bitvectors
    if (imgr != null) {
      BooleanFormula constr =
          bmgr.or(
              imgr.equal(
                  imgr.subtract(
                      imgr.add(imgr.makeVariable("x"), imgr.makeVariable("z")),
                      imgr.makeNumber(10)),
                  imgr.makeVariable("y")),
              imgr.equal(imgr.makeVariable("xx"), imgr.makeVariable("zz")));
      assertThat(mgr.extractVariables(constr).keySet()).containsExactly("x", "y", "z", "xx", "zz");
      assertThat(mgr.extractVariablesAndUFs(constr)).isEqualTo(mgr.extractVariables(constr));
    } else {
      BooleanFormula bvConstr =
          bmgr.or(
              bvmgr.equal(
                  bvmgr.subtract(
                      bvmgr.add(bvmgr.makeVariable(8, "x"), bvmgr.makeVariable(8, "z")),
                      bvmgr.makeBitvector(8, 10)),
                  bvmgr.makeVariable(8, "y")),
              bvmgr.equal(bvmgr.makeVariable(8, "xx"), bvmgr.makeVariable(8, "zz")));

      requireVisitor();

      assertThat(mgr.extractVariables(bvConstr).keySet())
          .containsExactly("x", "y", "z", "xx", "zz");
      assertThat(mgr.extractVariablesAndUFs(bvConstr)).isEqualTo(mgr.extractVariables(bvConstr));
    }
  }

  @Test
  public void ufNameExtractorTest() {
    // Since Boolector does not support integers we use bitvectors for constraints
    if (imgr != null) {
      BooleanFormula constraint =
          imgr.equal(
              fmgr.declareAndCallUF(
                  "uf1", FormulaType.IntegerType, ImmutableList.of(imgr.makeVariable("x"))),
              fmgr.declareAndCallUF(
                  "uf2", FormulaType.IntegerType, ImmutableList.of(imgr.makeVariable("y"))));
      assertThat(mgr.extractVariablesAndUFs(constraint).keySet())
          .containsExactly("uf1", "uf2", "x", "y");

      assertThat(mgr.extractVariables(constraint).keySet()).containsExactly("x", "y");
    } else {
      BooleanFormula bvConstraint =
          bvmgr.equal(
              fmgr.declareAndCallUF(
                  "uf1",
                  FormulaType.getBitvectorTypeWithSize(8),
                  ImmutableList.of(bvmgr.makeVariable(8, "x"))),
              fmgr.declareAndCallUF(
                  "uf2",
                  FormulaType.getBitvectorTypeWithSize(8),
                  ImmutableList.of(bvmgr.makeVariable(8, "y"))));

      requireVisitor();

      assertThat(mgr.extractVariablesAndUFs(bvConstraint).keySet())
          .containsExactly("uf1", "uf2", "x", "y");

      assertThat(mgr.extractVariables(bvConstraint).keySet()).containsExactly("x", "y");
    }
  }
}
