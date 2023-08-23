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
import org.sosy_lab.java_smt.solvers.dreal4.drealjni.OptionValueDouble.Type;

@NotThreadSafe
public class OptionValueInt {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected OptionValueInt(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(OptionValueInt obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(OptionValueInt obj) {
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
        DrealJNI.deleteOptionValueInt(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public OptionValueInt(int value) {
    this(DrealJNI.newOptionValueIntSWIG0(value), true);
  }

  public OptionValueInt(OptionValueInt arg0) {
    this(DrealJNI.newOptionValueIntSWIG1(OptionValueInt.getCPtr(arg0), arg0), true);
  }

  public OptionValueInt assignOperator(OptionValueInt arg0) {
    return new OptionValueInt(
        DrealJNI.optionValueIntAssignOperatorSWIG0(
            swigCPtr, this, OptionValueInt.getCPtr(arg0), arg0),
        false);
  }

  public OptionValueInt assignOperator(int value) {
    return new OptionValueInt(
        DrealJNI.optionValueIntAssignOperatorSWIG2(swigCPtr, this, value), false);
  }

  public OptionValueInt assignOperator(SwigTypePInt value) {
    return new OptionValueInt(
        DrealJNI.optionValueIntAssignOperatorSWIG3(swigCPtr, this, SwigTypePInt.swigRelease(value)),
        false);
  }

  public int get() {
    return DrealJNI.optionValueIntGet(swigCPtr, this);
  }

  public void setFromCommandLine(int value) {
    DrealJNI.optionValueIntSetFromCommandLine(swigCPtr, this, value);
  }

  public void setFromFile(int value) {
    DrealJNI.optionValueIntSetFromFile(swigCPtr, this, value);
  }

  public static final class Type {
    public static final OptionValueInt.Type.Kind DEFAULT = new OptionValueInt.Type.Kind("DEFAULT");
    public static final OptionValueInt.Type.Kind FROM_FILE =
        new OptionValueInt.Type.Kind("FROM_FILE");
    public static final OptionValueInt.Type.Kind FROM_COMMAND_LINE =
        new OptionValueInt.Type.Kind("FROM_COMMAND_LINE");
    public static final OptionValueInt.Type.Kind FROM_CODE =
        new OptionValueInt.Type.Kind("FROM_CODE");

    private static Type.Kind[] swigValues = {DEFAULT, FROM_FILE, FROM_COMMAND_LINE, FROM_CODE};
    private static int swigNext = 0;

    public static Type.Kind swigToEnum(int swigValue) {
      if (swigValue < swigValues.length
          && swigValue >= 0
          && swigValues[swigValue].swigValue == swigValue) {
        return swigValues[swigValue];
      }
      for (int i = 0; i < swigValues.length; i++) {
        if (swigValues[i].swigValue == swigValue) {
          return swigValues[i];
        }
      }
      throw new IllegalArgumentException("No enum " + Type.Kind.class + " with value " + swigValue);
    }

    public static class Kind {
      private final int swigValue;
      private final String swigName;

      public Kind(String swigName) {
        ;
        this.swigName = swigName;
        this.swigValue = swigNext;
        incrementSwigNext();
      }

      private void incrementSwigNext() {
        swigNext++;
      }

      public int swigValue() {
        return this.swigValue;
      }

      @Override
      public String toString() {
        return this.swigName;
      }
    }
  }
}
