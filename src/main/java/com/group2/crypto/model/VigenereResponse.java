package com.group2.crypto.model;

import java.util.List;

public class VigenereResponse {
    private String result;
    private String mode;
    private String keyType;
    private List<VigenereStep> steps;
    private String errorMessage;

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }
    public String getKeyType() { return keyType; }
    public void setKeyType(String keyType) { this.keyType = keyType; }
    public List<VigenereStep> getSteps() { return steps; }
    public void setSteps(List<VigenereStep> steps) { this.steps = steps; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
