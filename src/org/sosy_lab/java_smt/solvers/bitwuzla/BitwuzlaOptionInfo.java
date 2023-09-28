/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.sosy_lab.java_smt.solvers.bitwuzla;

public class BitwuzlaOptionInfo {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected BitwuzlaOptionInfo(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(BitwuzlaOptionInfo obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  @SuppressWarnings("deprecation")
  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        bitwuzlaJNI.delete_BitwuzlaOptionInfo(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setOpt(BitwuzlaOption value) {
    bitwuzlaJNI.BitwuzlaOptionInfo_opt_set(swigCPtr, this, value.swigValue());
  }

  public BitwuzlaOption getOpt() {
    return BitwuzlaOption.swigToEnum(bitwuzlaJNI.BitwuzlaOptionInfo_opt_get(swigCPtr, this));
  }

  public void setShrt(String value) {
    bitwuzlaJNI.BitwuzlaOptionInfo_shrt_set(swigCPtr, this, value);
  }

  public String getShrt() {
    return bitwuzlaJNI.BitwuzlaOptionInfo_shrt_get(swigCPtr, this);
  }

  public void setLng(String value) {
    bitwuzlaJNI.BitwuzlaOptionInfo_lng_set(swigCPtr, this, value);
  }

  public String getLng() {
    return bitwuzlaJNI.BitwuzlaOptionInfo_lng_get(swigCPtr, this);
  }

  public void setDescription(String value) {
    bitwuzlaJNI.BitwuzlaOptionInfo_description_set(swigCPtr, this, value);
  }

  public String getDescription() {
    return bitwuzlaJNI.BitwuzlaOptionInfo_description_get(swigCPtr, this);
  }

  public void setNumeric(NumericValue value) {
    bitwuzlaJNI.BitwuzlaOptionInfo_numeric_set(swigCPtr, this, NumericValue.getCPtr(value), value);
  }

  public NumericValue getNumeric() {
    long cPtr = bitwuzlaJNI.BitwuzlaOptionInfo_numeric_get(swigCPtr, this);
    return (cPtr == 0) ? null : new NumericValue(cPtr, false);
  }

  public void setMode(ModeValue value) {
    bitwuzlaJNI.BitwuzlaOptionInfo_mode_set(swigCPtr, this, ModeValue.getCPtr(value), value);
  }

  public ModeValue getMode() {
    long cPtr = bitwuzlaJNI.BitwuzlaOptionInfo_mode_get(swigCPtr, this);
    return (cPtr == 0) ? null : new ModeValue(cPtr, false);
  }

  public BitwuzlaOptionInfo() {
    this(bitwuzlaJNI.new_BitwuzlaOptionInfo(), true);
  }

}
