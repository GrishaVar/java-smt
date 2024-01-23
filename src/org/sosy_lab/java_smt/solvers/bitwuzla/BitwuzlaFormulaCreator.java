// This file is part of JavaSMT,
// an API wrapper for a collection of SMT solvers:
// https://github.com/sosy-lab/java-smt
//
// SPDX-FileCopyrightText: 2023 Dirk Beyer <https://www.sosy-lab.org>
//
// SPDX-License-Identifier: Apache-2.0

package org.sosy_lab.java_smt.solvers.bitwuzla;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Table;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import org.sosy_lab.java_smt.api.ArrayFormula;
import org.sosy_lab.java_smt.api.BitvectorFormula;
import org.sosy_lab.java_smt.api.BooleanFormula;
import org.sosy_lab.java_smt.api.FloatingPointFormula;
import org.sosy_lab.java_smt.api.Formula;
import org.sosy_lab.java_smt.api.FormulaType;
import org.sosy_lab.java_smt.api.FormulaType.ArrayFormulaType;
import org.sosy_lab.java_smt.api.FormulaType.FloatingPointType;
import org.sosy_lab.java_smt.api.FunctionDeclarationKind;
import org.sosy_lab.java_smt.api.QuantifiedFormulaManager.Quantifier;
import org.sosy_lab.java_smt.api.visitors.FormulaVisitor;
import org.sosy_lab.java_smt.basicimpl.FormulaCreator;
import org.sosy_lab.java_smt.basicimpl.FunctionDeclarationImpl;
import org.sosy_lab.java_smt.solvers.bitwuzla.BitwuzlaFormula.BitwuzlaArrayFormula;
import org.sosy_lab.java_smt.solvers.bitwuzla.BitwuzlaFormula.BitwuzlaBitvectorFormula;
import org.sosy_lab.java_smt.solvers.bitwuzla.BitwuzlaFormula.BitwuzlaBooleanFormula;
import org.sosy_lab.java_smt.solvers.bitwuzla.BitwuzlaFormula.BitwuzlaFloatingPointFormula;
import org.sosy_lab.java_smt.solvers.bitwuzla.BitwuzlaFormula.BitwuzlaFloatingPointRoundingModeFormula;
import org.sosy_lab.java_smt.solvers.bitwuzla.api.Bitwuzla;
import org.sosy_lab.java_smt.solvers.bitwuzla.api.Kind;
import org.sosy_lab.java_smt.solvers.bitwuzla.api.Map_TermTerm;
import org.sosy_lab.java_smt.solvers.bitwuzla.api.Sort;
import org.sosy_lab.java_smt.solvers.bitwuzla.api.Term;
import org.sosy_lab.java_smt.solvers.bitwuzla.api.Vector_Sort;
import org.sosy_lab.java_smt.solvers.bitwuzla.api.Vector_Term;

public class BitwuzlaFormulaCreator extends FormulaCreator<Term, Sort, Void, BitwuzlaDeclaration> {
  private final Table<String, Sort, Term> formulaCache = HashBasedTable.create();

  // private final Table<String, Long, Long> boundFormulaCache = HashBasedTable.create();

  protected BitwuzlaFormulaCreator() {
    super(null, Bitwuzla.mk_bool_sort(), null, null, null, null);
  }

  @Override
  public Sort getBitvectorType(int bitwidth) {
    return Bitwuzla.mk_bv_sort(bitwidth);
  }

  @Override
  public BitvectorFormula encapsulateBitvector(Term pTerm) {
    assert getFormulaType(pTerm).isBitvectorType()
        : "Unexpected formula type for BV formula: " + getFormulaType(pTerm);
    return new BitwuzlaBitvectorFormula(pTerm);
  }

  // Assuming that JavaSMT FloatingPointType follows IEEE 754, if it is in the decimal
  // system instead use bitwuzla_mk_fp_value_from_real somehow or convert myself
  @Override
  public Sort getFloatingPointType(FloatingPointType type) {
    return Bitwuzla.mk_fp_sort(type.getExponentSize(), type.getMantissaSize() + 1);
  }

  @Override
  @SuppressWarnings("MethodTypeParameterName")
  protected <TI extends Formula, TE extends Formula> FormulaType<TE> getArrayFormulaElementType(
      ArrayFormula<TI, TE> pArray) {
    return ((BitwuzlaArrayFormula<TI, TE>) pArray).getElementType();
  }

  @Override
  @SuppressWarnings("MethodTypeParameterName")
  protected <TI extends Formula, TE extends Formula> FormulaType<TI> getArrayFormulaIndexType(
      ArrayFormula<TI, TE> pArray) {
    return ((BitwuzlaArrayFormula<TI, TE>) pArray).getIndexType();
  }

  @Override
  public Sort getArrayType(Sort indexType, Sort elementType) {
    return Bitwuzla.mk_array_sort(indexType, elementType);
  }

  @Override
  protected FloatingPointFormula encapsulateFloatingPoint(Term pTerm) {
    assert getFormulaType(pTerm).isFloatingPointType()
        : String.format("%s is no FP, but %s (%s)", pTerm, pTerm.sort(), getFormulaType(pTerm));
    return new BitwuzlaFloatingPointFormula(pTerm);
  }

  @Override
  @SuppressWarnings("MethodTypeParameterName")
  protected <TI extends Formula, TE extends Formula> ArrayFormula<TI, TE> encapsulateArray(
      Term pTerm, FormulaType<TI> pIndexType, FormulaType<TE> pElementType) {
    assert getFormulaType(pTerm).isArrayType()
        : "Unexpected formula type for array formula: " + getFormulaType(pTerm);
    return new BitwuzlaArrayFormula<>(pTerm, pIndexType, pElementType);
  }

  @Override
  public Term makeVariable(Sort pSort, String varName) {
    Term maybeFormula = formulaCache.get(varName, pSort);
    if (maybeFormula != null) {
      return maybeFormula;
    }

    Term newVar = Bitwuzla.mk_const(pSort, varName);
    formulaCache.put(varName, pSort, newVar);
    return newVar;
  }

  public Term makeBoundVariable(Term var) {
    String name = var.symbol();
    Sort sort = var.sort();
    // TODO: do we need a bound cache?
    // Term maybeVar = boundFormulaCache.get(name, sort);
    // if (maybeVar != null) {
    // return maybeVar;
    // }

    Term newVar = Bitwuzla.mk_var(sort, name);
    // boundFormulaCache.put(name, sort, newVar);
    return newVar;
  }

  public FormulaType<?> bitwuzlaSortToType(Sort pSort) {
    // UFs play by different rules. For them, we need to extract the domain
    if (pSort.is_fp()) {
      int exponent = pSort.fp_exp_size();
      int mantissa = pSort.fp_sig_size() - 1;
      return FormulaType.getFloatingPointType(exponent, mantissa);
    } else if (pSort.is_bv()) {
      return FormulaType.getBitvectorTypeWithSize(pSort.bv_size());
    } else if (pSort.is_array()) {
      FormulaType<?> domainSort = bitwuzlaSortToType(pSort.array_index());
      FormulaType<?> rangeSort = bitwuzlaSortToType(pSort.array_element());
      return FormulaType.getArrayType(domainSort, rangeSort);
    } else if (pSort.is_bool()) {
      return FormulaType.BooleanType;
    } else if (pSort.is_rm()) {
      return FormulaType.FloatingPointRoundingModeType;
    }

    throw new UnsupportedOperationException(
        "Could not find the JavaSMT type for sort" + pSort + ".");
  }

  private FunctionDeclarationKind getDeclarationKind(Term term) {
    Kind kind = term.kind();

    if (kind.equals(Kind.AND)) {
      return FunctionDeclarationKind.AND;
    } else if (kind.equals(Kind.DISTINCT)) {
      return FunctionDeclarationKind.DISTINCT;
    } else if (kind.equals(Kind.EQUAL)) {
      return FunctionDeclarationKind.EQ;
    } else if (kind.equals(Kind.IFF)) {
      return FunctionDeclarationKind.IFF;
    } else if (kind.equals(Kind.IMPLIES)) {
      return FunctionDeclarationKind.IMPLIES;
    } else if (kind.equals(Kind.NOT)) {
      return FunctionDeclarationKind.NOT;
    } else if (kind.equals(Kind.OR)) {
      return FunctionDeclarationKind.OR;
    } else if (kind.equals(Kind.XOR)) {
      return FunctionDeclarationKind.XOR;
    } else if (kind.equals(Kind.ITE)) {
      return FunctionDeclarationKind.ITE;
    } else if (kind.equals(Kind.APPLY)) {
      return FunctionDeclarationKind.UF;
    } else if (kind.equals(Kind.ARRAY_SELECT)) {
      return FunctionDeclarationKind.SELECT;
    } else if (kind.equals(Kind.ARRAY_STORE)) {
      return FunctionDeclarationKind.STORE;
    } else if (kind.equals(Kind.BV_ADD)) {
      return FunctionDeclarationKind.BV_ADD;
    } else if (kind.equals(Kind.BV_AND)) {
      return FunctionDeclarationKind.BV_AND;
    } else if (kind.equals(Kind.BV_ASHR)) {
      return FunctionDeclarationKind.BV_ASHR;
    } else if (kind.equals(Kind.BV_CONCAT)) {
      return FunctionDeclarationKind.BV_CONCAT;
    } else if (kind.equals(Kind.BV_MUL)) {
      return FunctionDeclarationKind.BV_MUL;
    } else if (kind.equals(Kind.BV_NEG)) {
      return FunctionDeclarationKind.BV_NEG;
    } else if (kind.equals(Kind.BV_NOT)) {
      return FunctionDeclarationKind.BV_NOT;
    } else if (kind.equals(Kind.BV_OR)) {
      return FunctionDeclarationKind.BV_OR;
    } else if (kind.equals(Kind.BV_SDIV)) {
      return FunctionDeclarationKind.BV_SDIV;
    } else if (kind.equals(Kind.BV_SGE)) {
      return FunctionDeclarationKind.BV_SGE;
    } else if (kind.equals(Kind.BV_SGT)) {
      return FunctionDeclarationKind.BV_SGT;
    } else if (kind.equals(Kind.BV_SHL)) {
      return FunctionDeclarationKind.BV_SHL;
    } else if (kind.equals(Kind.BV_SLE)) {
      return FunctionDeclarationKind.BV_SLE;
    } else if (kind.equals(Kind.BV_SLT)) {
      return FunctionDeclarationKind.BV_SLT;
    } else if (kind.equals(Kind.BV_SREM)) {
      return FunctionDeclarationKind.BV_SREM;
    } else if (kind.equals(Kind.BV_SUB)) {
      return FunctionDeclarationKind.BV_SUB;
    } else if (kind.equals(Kind.BV_UDIV)) {
      return FunctionDeclarationKind.BV_UDIV;
    } else if (kind.equals(Kind.BV_UGE)) {
      return FunctionDeclarationKind.BV_UGE;
    } else if (kind.equals(Kind.BV_UGT)) {
      return FunctionDeclarationKind.BV_UGT;
    } else if (kind.equals(Kind.BV_ULE)) {
      return FunctionDeclarationKind.BV_ULE;
    } else if (kind.equals(Kind.BV_ULT)) {
      return FunctionDeclarationKind.BV_ULT;
    } else if (kind.equals(Kind.BV_UREM)) {
      return FunctionDeclarationKind.BV_UREM;
    } else if (kind.equals(Kind.BV_EXTRACT)) {
      return FunctionDeclarationKind.BV_EXTRACT;
    } else if (kind.equals(Kind.BV_SIGN_EXTEND)) {
      return FunctionDeclarationKind.BV_SIGN_EXTENSION;
    } else if (kind.equals(Kind.BV_ZERO_EXTEND)) {
      return FunctionDeclarationKind.BV_ZERO_EXTENSION;
    } else if (kind.equals(Kind.FP_ABS)) {
      return FunctionDeclarationKind.FP_ABS;
    } else if (kind.equals(Kind.FP_ADD)) {
      return FunctionDeclarationKind.FP_ADD;
    } else if (kind.equals(Kind.FP_DIV)) {
      return FunctionDeclarationKind.FP_DIV;
    } else if (kind.equals(Kind.FP_EQUAL)) {
      return FunctionDeclarationKind.FP_EQ;
    } else if (kind.equals(Kind.FP_GEQ)) {
      return FunctionDeclarationKind.FP_GE;
    } else if (kind.equals(Kind.FP_GT)) {
      return FunctionDeclarationKind.FP_GT;
    } else if (kind.equals(Kind.FP_IS_INF)) {
      return FunctionDeclarationKind.FP_IS_INF;
    } else if (kind.equals(Kind.FP_IS_NAN)) {
      return FunctionDeclarationKind.FP_IS_NAN;
    } else if (kind.equals(Kind.FP_IS_NEG)) {
      return FunctionDeclarationKind.FP_IS_NEGATIVE;
    } else if (kind.equals(Kind.FP_IS_NORMAL)) {
      return FunctionDeclarationKind.FP_IS_NORMAL;
    } else if (kind.equals(Kind.FP_IS_SUBNORMAL)) {
      return FunctionDeclarationKind.FP_IS_SUBNORMAL;
    } else if (kind.equals(Kind.FP_IS_ZERO)) {
      return FunctionDeclarationKind.FP_IS_ZERO;
    } else if (kind.equals(Kind.FP_LEQ)) {
      return FunctionDeclarationKind.FP_LE;
    } else if (kind.equals(Kind.FP_LT)) {
      return FunctionDeclarationKind.FP_LT;
    } else if (kind.equals(Kind.FP_MAX)) {
      return FunctionDeclarationKind.FP_MAX;
    } else if (kind.equals(Kind.FP_MIN)) {
      return FunctionDeclarationKind.FP_MIN;
    } else if (kind.equals(Kind.FP_MUL)) {
      return FunctionDeclarationKind.FP_MUL;
    } else if (kind.equals(Kind.FP_NEG)) {
      return FunctionDeclarationKind.FP_NEG;
    } else if (kind.equals(Kind.FP_RTI)) {
      return FunctionDeclarationKind.FP_ROUND_TO_INTEGRAL;
    } else if (kind.equals(Kind.FP_SQRT)) {
      return FunctionDeclarationKind.FP_SQRT;
    } else if (kind.equals(Kind.FP_SUB)) {
      return FunctionDeclarationKind.FP_SUB;
    } else if (kind.equals(Kind.FP_TO_FP_FROM_BV)) {
      return FunctionDeclarationKind.BV_UCASTTO_FP;
    } else if (kind.equals(Kind.FP_TO_FP_FROM_FP)) {
      return FunctionDeclarationKind.FP_CASTTO_FP;
    } else if (kind.equals(Kind.FP_TO_FP_FROM_SBV)) {
      return FunctionDeclarationKind.BV_SCASTTO_FP;
    } else if (kind.equals(Kind.FP_TO_FP_FROM_UBV)) {
      return FunctionDeclarationKind.BV_UCASTTO_FP;
    } else if (kind.equals(Kind.FP_TO_SBV)) {
      return FunctionDeclarationKind.FP_CASTTO_SBV;
    } else if (kind.equals(Kind.FP_TO_UBV)) {
      return FunctionDeclarationKind.FP_CASTTO_UBV;
    } else if (kind.equals(Kind.BV_XOR)) {
      return FunctionDeclarationKind.BV_XOR;
    }
    throw new UnsupportedOperationException("Can not discern formula kind " + kind);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T extends Formula> T encapsulate(FormulaType<T> pType, Term pTerm) {
    assert pType.equals(getFormulaType(pTerm))
        : String.format(
            "Trying to encapsulate formula of type %s as %s", getFormulaType(pTerm), pType);
    if (pType.isBooleanType()) {
      return (T) new BitwuzlaBooleanFormula(pTerm);
    } else if (pType.isArrayType()) {
      ArrayFormulaType<?, ?> arrFt = (ArrayFormulaType<?, ?>) pType;
      return (T) new BitwuzlaArrayFormula<>(pTerm, arrFt.getIndexType(), arrFt.getElementType());
    } else if (pType.isBitvectorType()) {
      return (T) new BitwuzlaBitvectorFormula(pTerm);
    } else if (pType.isFloatingPointType()) {
      return (T) new BitwuzlaFloatingPointFormula(pTerm);
    } else if (pType.isFloatingPointRoundingModeType()) {
      return (T) new BitwuzlaFloatingPointRoundingModeFormula(pTerm);
    }
    throw new IllegalArgumentException("Cannot create formulas of type " + pType + " in Bitwuzla");
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T extends Formula> FormulaType<T> getFormulaType(T pFormula) {
    Sort sort = extractInfo(pFormula).sort();
    if (pFormula instanceof BitvectorFormula) {
      checkArgument(sort.is_bv(), "BitvectorFormula with type missmatch: %s", pFormula);
      return (FormulaType<T>) FormulaType.getBitvectorTypeWithSize(sort.bv_size());
    } else if (pFormula instanceof ArrayFormula<?, ?>) {
      FormulaType<T> arrayIndexType = getArrayFormulaIndexType((ArrayFormula<T, T>) pFormula);
      FormulaType<T> arrayElementType = getArrayFormulaElementType((ArrayFormula<T, T>) pFormula);
      return (FormulaType<T>) FormulaType.getArrayType(arrayIndexType, arrayElementType);
    } else if (pFormula instanceof FloatingPointFormula) {
      if (!sort.is_fp()) {
        throw new IllegalArgumentException(
            "FloatingPointFormula with actual type " + sort + ": " + pFormula);
      }
      int exp = sort.fp_exp_size();
      int man = sort.fp_sig_size() - 1;
      return (FormulaType<T>) FormulaType.getFloatingPointType(exp, man);
    } else if (sort.is_rm()) {
      return (FormulaType<T>) FormulaType.FloatingPointRoundingModeType;
    }
    return super.getFormulaType(pFormula);
  }

  @Override
  public FormulaType<?> getFormulaType(Term formula) {
    Sort pType = formula.sort();
    return bitwuzlaSortToType(pType);
  }

  private BigDecimal parseIEEEbinaryFP(Term pTerm) {
    // The Bitwuzla string for FPs is always in binary, regardless of the second argument.

    String fp = pTerm.toString();

    if (fp.length() == 32) {
      float result = Float.intBitsToFloat(Integer.parseUnsignedInt(fp, 2));
      return new BigDecimal(result);
    } else if (fp.length() == 64) {
      double result = Double.longBitsToDouble(Long.parseUnsignedLong(fp, 2));
      return new BigDecimal(result);
    } else {
      throw new UnsupportedOperationException(
          "Visitor can only visit constant FPs of 32 or 64 " + "bits.");
    }

    //    String fpSMTLIB = bitwuzlaJNI.bitwuzla_term_to_string(pTerm);
    //    String[] mySplit = fpSMTLIB.split(" #b");
    //    mySplit[3] = mySplit[3].replace(")", "");
    //    double result = calculateDecimal(mySplit[3], mySplit[2], mySplit[1]);
  }

  @Override
  public <R> R visit(FormulaVisitor<R> visitor, Formula formula, Term f)
      throws UnsupportedOperationException {
    Kind kind = f.kind();
    if (f.is_value()) {
      return visitor.visitConstant(formula, convertValue(f));
    } else if (f.sort().is_fp()) {
      return visitor.visitConstant(formula, parseIEEEbinaryFP(f));

    } else if (f.is_const()) {
      String name = f.symbol();
      return visitor.visitFreeVariable(formula, name);

    } else if (f.is_variable()) {
      String name = f.symbol();
      Sort sort = f.sort();
      Term originalVar = formulaCache.get(name, sort);
      return visitor.visitBoundVariable(encapsulate(getFormulaType(originalVar), originalVar), 0);

    } else if (kind.equals(Kind.EXISTS) || kind.equals(Kind.FORALL)) {
      Vector_Term children = f.children();
      // QUANTIFIER: replace bound variable with free variable for visitation
      int size = children.size();
      assert size == 2;
      Term body = children.get(size - 1);
      List<Formula> freeEncVars = new ArrayList<>();
      // The first length - 2 elements are bound vars, and the last element is the body
      Term[] boundVars = new Term[size - 1];
      Term[] freeVars = new Term[size - 1];
      for (int i = 0; i < size - 1; i++) {
        Term boundVar = children.get(i);
        boundVars[i] = boundVar;
        String name = boundVar.symbol();
        assert name != null;
        Sort sort = boundVar.sort();
        Term freeVar;
        if (formulaCache.contains(name, sort)) {
          freeVar = formulaCache.get(name, sort);
        } else {
          // no free var existing (e.g. from parsing), create a new one
          freeVar = makeVariable(sort, name);
        }
        freeVars[i] = freeVar;
        freeEncVars.add(encapsulate(getFormulaType(freeVar), freeVar));
      }

      Map_TermTerm map = new Map_TermTerm();
      for (int i = 0; i < boundVars.length; i++) {
        map.put(boundVars[i], freeVars[i]);
      }
      // FIXME: This will change the original term
      body.substitute(map);

      Quantifier quant = kind.equals(Kind.EXISTS) ? Quantifier.EXISTS : Quantifier.FORALL;
      return visitor.visitQuantifier(
          (BooleanFormula) formula, quant, freeEncVars, encapsulateBoolean(body));

    } else {
      Vector_Term args = f.children();
      ImmutableList.Builder<Formula> arguments = ImmutableList.builder();
      ImmutableList.Builder<FormulaType<?>> argumentTypes = ImmutableList.builder();

      String name = f.symbol();

      BitwuzlaDeclaration decl = null;
      for (int i = 0; i < args.size(); i++) {
        Term argument = args.get(i);
        if (kind == Kind.APPLY && i == 0) {
          // UFs carry the decl in the first child and the decl has the name
          decl = new BitwuzlaDeclaration(argument);
          name = argument.symbol();
          continue;
        }
        FormulaType<?> type = getFormulaType(argument);
        arguments.add(encapsulate(type, argument));
        argumentTypes.add(type);
      }

      if (name == null) {
        name = f.kind().toString();
      }
      if (decl == null) {
        decl = new BitwuzlaDeclaration(f.kind());
      }
      if (f.num_indices() > 0) {
        // We need to retain the original formula as the declaration for indexed formulas,
        // otherwise we loose the index info, but we also need to know if its a kind or term
        decl = new BitwuzlaDeclaration(f);
      }

      return visitor.visitFunction(
          formula,
          arguments.build(),
          FunctionDeclarationImpl.of(
              name, getDeclarationKind(f), argumentTypes.build(), getFormulaType(f), decl));
    }
  }

  @Override
  public Term callFunctionImpl(BitwuzlaDeclaration declaration, List<Term> args) {
    // For UFs the declaration needs to be a const wrapping of the function sort
    // For all other functions it needs to be the kind
    // BUT, we can never use a bitwuzla_term_is... function on a KIND
    if (!declaration.isKind() && declaration.getTerm().num_indices() > 0) {
      // The term might be indexed, then we need index creation
      Term term = declaration.getTerm();
      Kind properKind = term.kind();
      if (properKind == Kind.BV_ZERO_EXTEND) {
        // FIXME: Not implemented
        throw new UnsupportedOperationException();
      }
      return Bitwuzla.mk_term(properKind, new Vector_Term(args), term.indices());
    }

    if (!declaration.isKind() && declaration.getTerm().sort().is_fun()) {
      Vector_Term functionAndArgs = new Vector_Term();
      functionAndArgs.add(declaration.getTerm());
      functionAndArgs.addAll(args);
      return Bitwuzla.mk_term(Kind.APPLY, functionAndArgs);
    }

    assert declaration.isKind();

    return Bitwuzla.mk_term(declaration.getKind(), new Vector_Term(args));
  }

  @Override
  public BitwuzlaDeclaration declareUFImpl(String name, Sort pReturnType, List<Sort> pArgTypes) {
    if (pArgTypes.isEmpty()) {
      // Bitwuzla does not support UFs with no args, so we make a variable
      // TODO: implement
      throw new UnsupportedOperationException("Bitwuzla does not support 0 arity UFs.");
    }
    Sort functionSort = Bitwuzla.mk_fun_sort(new Vector_Sort(pArgTypes), pReturnType);

    Term maybeFormula = formulaCache.get(name, functionSort);
    if (maybeFormula != null) {
      return new BitwuzlaDeclaration(maybeFormula);
    }

    Term uf = Bitwuzla.mk_const(functionSort, name);
    formulaCache.put(name, functionSort, uf);
    return new BitwuzlaDeclaration(uf);
  }

  @Override
  protected BitwuzlaDeclaration getBooleanVarDeclarationImpl(Term pTerm) {
    Kind kind = pTerm.kind();

    // CONSTANTS are "variables" and Kind.VARIABLES are bound variables in for example quantifiers
    assert kind == Kind.APPLY || kind == Kind.CONSTANT : kind.toString();
    if (kind == Kind.APPLY) {
      // Returns pointer to Uninterpreted Function used in Apply
      return new BitwuzlaDeclaration(pTerm.get(0));
    } else {
      return new BitwuzlaDeclaration(pTerm);
    }
  }

  @Override
  public Term extractInfo(Formula pT) {
    return BitwuzlaFormulaManager.getBitwuzlaTerm(pT);
  }

  @Override
  public BooleanFormula encapsulateBoolean(Term pTerm) {
    assert getFormulaType(pTerm).isBooleanType();
    return new BitwuzlaBooleanFormula(pTerm);
  }

  protected Table<String, Sort, Term> getCache() {
    return formulaCache;
  }

  // True if the entered String has an existing variable in the cache.
  protected boolean formulaCacheContains(String variable) {
    // There is always only 1 type permitted per variable
    return formulaCache.containsRow(variable);
  }

  // Optional that contains the variable to the entered String if there is one.
  protected Optional<Term> getFormulaFromCache(String variable) {
    Iterator<Entry<Sort, Term>> entrySetIter = formulaCache.row(variable).entrySet().iterator();
    if (entrySetIter.hasNext()) {
      // If there is a non-empty row for an entry, there is only one entry
      return Optional.of(entrySetIter.next().getValue());
    }
    return Optional.empty();
  }

  @Override
  public Object convertValue(Term term) {
    Sort sort = term.sort();
    if (term.is_const()) {
      return null;
    }
    if (sort.is_fun()) {
      // TODO: this is wrong
      throw new AssertionError("Error: Unknown sort and term");
    } else {
      String value = term.toString();
      if (value.startsWith("#b")) {
        // Bitvectors in Bitwuzla start with a #b
        return new BigInteger(value.substring(2), 2);
      } else if (value.equals("true")) {
        return true;
      } else if (value.equals("false")) {
        return false;
      } else if (value.startsWith("(fp")) {
        return value
            .replace("(fp", "")
            .replace(")", "")
            .replace("#b", "")
            .replace("#b", "")
            .replace("#b", "")
            .strip();
      } else if (sort.is_rm()) {
        return value;
      }
    }

    throw new AssertionError(
        "Error: Could not convert term to value; Unknown sort and term. " + "Value: " + term);
  }
}
