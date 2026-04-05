package com.group2.crypto.model;

import java.util.List;

public class ModuloResponse {
    private long aVal;
    private long nVal;
    private long inverse;
    private long gcd;
    private boolean hasInverse;
    private String message;
    private List<EuclideanStep> table;
    private List<String> transcript;
    private String selectedMethod;
    private String errorMessage;

    public String getSelectedMethod() { return selectedMethod; }
    public void setSelectedMethod(String selectedMethod) { this.selectedMethod = selectedMethod; }
    public List<String> getTranscript() { return transcript; }
    public void setTranscript(List<String> transcript) { this.transcript = transcript; }

    public long getAVal() { return aVal; }
    public void setAVal(long aVal) { this.aVal = aVal; }
    public long getNVal() { return nVal; }
    public void setNVal(long nVal) { this.nVal = nVal; }
    public long getInverse() { return inverse; }
    public void setInverse(long inverse) { this.inverse = inverse; }
    public long getGcd() { return gcd; }
    public void setGcd(long gcd) { this.gcd = gcd; }
    public boolean isHasInverse() { return hasInverse; }
    public void setHasInverse(boolean hasInverse) { this.hasInverse = hasInverse; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public List<EuclideanStep> getTable() { return table; }
    public void setTable(List<EuclideanStep> table) { this.table = table; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
