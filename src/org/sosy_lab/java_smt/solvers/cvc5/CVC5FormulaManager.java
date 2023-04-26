// This file is part of JavaSMT,
// an API wrapper for a collection of SMT solvers:
// https://github.com/sosy-lab/java-smt
//
// SPDX-FileCopyrightText: 2022 Dirk Beyer <https://www.sosy-lab.org>
//
// SPDX-License-Identifier: Apache-2.0

package org.sosy_lab.java_smt.solvers.cvc5;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import de.uni_freiburg.informatik.ultimate.logic.PrintTerm;
import io.github.cvc5.CVC5ApiException;
import io.github.cvc5.Kind;
import io.github.cvc5.Solver;
import io.github.cvc5.Sort;
import io.github.cvc5.Term;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.sosy_lab.common.Appender;
import org.sosy_lab.common.Appenders;
import org.sosy_lab.java_smt.api.BooleanFormula;
import org.sosy_lab.java_smt.api.Formula;
import org.sosy_lab.java_smt.api.FormulaType;
import org.sosy_lab.java_smt.basicimpl.AbstractFormulaManager;

class CVC5FormulaManager extends AbstractFormulaManager<Term, Sort, Solver, Term> {

  private final CVC5FormulaCreator creator;

  @SuppressWarnings("checkstyle:parameternumber")
  CVC5FormulaManager(
      CVC5FormulaCreator pFormulaCreator,
      CVC5UFManager pFfmgr,
      CVC5BooleanFormulaManager pBfmgr,
      CVC5IntegerFormulaManager pIfmgr,
      CVC5RationalFormulaManager pRfmgr,
      CVC5BitvectorFormulaManager pBvfmgr,
      CVC5FloatingPointFormulaManager pFpfmgr,
      CVC5QuantifiedFormulaManager pQfmgr,
      CVC5ArrayFormulaManager pAfmgr,
      CVC5SLFormulaManager pSLfmgr,
      CVC5StringFormulaManager pStrmgr) {
    super(
        pFormulaCreator,
        pFfmgr,
        pBfmgr,
        pIfmgr,
        pRfmgr,
        pBvfmgr,
        pFpfmgr,
        pQfmgr,
        pAfmgr,
        pSLfmgr,
        pStrmgr,
        null);
    creator = pFormulaCreator;
  }

  static Term getCVC5Term(Formula pT) {
    if (pT instanceof CVC5Formula) {
      return ((CVC5Formula) pT).getTerm();
    }
    throw new IllegalArgumentException(
        "Cannot get the formula info of type " + pT.getClass().getSimpleName() + " in the Solver!");
  }

  @Override
  public BooleanFormula parse(String pS) throws IllegalArgumentException {
    throw new UnsupportedOperationException();
  }

  @Override
  public Appender dumpFormula(Term f) {
    assert getFormulaCreator().getFormulaType(f) == FormulaType.BooleanType
        : "Only BooleanFormulas may be dumped";

    return new Appenders.AbstractAppender() {

      @Override
      public void appendTo(Appendable out) throws IOException {

        // get all symbols
        final Map<String, Term> allVars = new LinkedHashMap<>();
        creator.extractVariablesAndUFs(f, true, allVars::put);

        // print all symbols
        for (Map.Entry<String, Term> entry : allVars.entrySet()) {
          String name = entry.getKey();
          Term var = entry.getValue();

          // escaping is stolen from SMTInterpol, lets hope this remains consistent
          out.append("(declare-fun ").append(PrintTerm.quoteIdentifier(name)).append(" (");

          // add function parameters
          Iterable<Sort> childrenTypes;
          try {
            if (var.getSort().isFunction() || var.getKind() == Kind.APPLY_UF) {
              childrenTypes = Iterables.skip(Iterables.transform(var, Term::getSort), 1);
            } else {
              childrenTypes = Iterables.transform(var, Term::getSort);
            }
          } catch (CVC5ApiException e) {
            childrenTypes = Iterables.transform(var, Term::getSort);
          }
          out.append(Joiner.on(" ").join(childrenTypes));

          // and return type
          out.append(") ").append(var.getSort().toString()).append(")\n");
        }

        // now add the final assert
        out.append("(assert ");
        // Formerly in CVC4:
        // f.toString() does expand all nested sub-expressions and causes exponential overhead.
        // f.toStream() uses LET-expressions and is exactly what we want.
        // However, in CVC5 toStream() does no longer exists.
        // TODO: either toString() will do, or we may need iterator().
        /*
        try (OutputStream stream =
            new OutputStream() {

              @Override
              public void write(int chr) throws IOException {
                out.append((char) chr);
              }
            }) {
          f.toStream(stream);
        }
        */
        out.append(f.toString());
        out.append(')');
      }
    };
  }

  @Override
  public <T extends Formula> T substitute(
      final T pF, final Map<? extends Formula, ? extends Formula> pFromToMapping) {
    Term termThatGetsSubst = extractInfo(pF);
    Term[] toBeSubstituted = new Term[pFromToMapping.size()];
    Term[] substitutes = new Term[pFromToMapping.size()];
    int i = 0;
    for (Map.Entry<? extends Formula, ? extends Formula> e : pFromToMapping.entrySet()) {
      toBeSubstituted[i] = extractInfo(e.getKey());
      substitutes[i] = extractInfo(e.getValue());
      i++;
    }
    // CVC5 stops after the first term is replaced, rerun until nothing changes
    boolean matches = false;
    while (!matches) {
      Term newTerm = termThatGetsSubst.substitute(toBeSubstituted, substitutes);
      matches = termThatGetsSubst.equals(newTerm);
      termThatGetsSubst = newTerm;
    }
    FormulaType<T> type = getFormulaType(pF);
    return getFormulaCreator().encapsulate(type, termThatGetsSubst);
  }
}
