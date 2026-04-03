package com.group2.crypto.model;

public class AesRequest {
    private String data = "";
    private String key = "";
    private String mode = "encrypt"; // encrypt or decrypt
    private String stepMode = "FULL"; // FULL or SINGLE

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }
    public String getStepMode() { return stepMode; }
    public void setStepMode(String stepMode) { this.stepMode = stepMode; }
}
