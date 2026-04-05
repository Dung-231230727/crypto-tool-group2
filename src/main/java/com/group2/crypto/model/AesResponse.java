package com.group2.crypto.model;

import java.util.ArrayList;
import java.util.List;

public class AesResponse {
    private String result = "";
    private String errorMessage = null;
    private String mode = "";
    private String inputData = "";
    private String inputKey = "";
    private List<String> transcript = new ArrayList<>();

    public List<String> getTranscript() { return transcript; }
    public void setTranscript(List<String> transcript) { this.transcript = transcript; }

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
