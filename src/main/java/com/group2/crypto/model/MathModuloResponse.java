package com.group2.crypto.model;

import java.util.List;

public class MathModuloResponse {
    private String result = "";
    private List<String> transcript;
    private String errorMessage;

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
    public List<String> getTranscript() { return transcript; }
    public void setTranscript(List<String> transcript) { this.transcript = transcript; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
