package com.group2.crypto.model;


public class DesResponse {
    private String result = "";
    private String errorMessage = null;
    private String mode = "";
    private String inputData = "";
    private String inputKey = "";
    private java.util.List<DesStep> steps = new java.util.ArrayList<>();
    
    public java.util.List<DesStep> getSteps() { return steps; }
    public void setSteps(java.util.List<DesStep> steps) { this.steps = steps; }
    
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }
    public String getInputData() { return inputData; }
    public void setInputData(String inputData) { this.inputData = inputData; }
    public String getInputKey() { return inputKey; }
    public void setInputKey(String inputKey) { this.inputKey = inputKey; }
}
