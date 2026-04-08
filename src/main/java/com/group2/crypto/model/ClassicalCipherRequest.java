package com.group2.crypto.model;


public class ClassicalCipherRequest {
    private String algorithm = "CAESAR"; 
    private String mode = "ENCRYPT";      
    private String text = "";
    private String key = "";
    private String keyType = "REPEATING"; // For Vigenere: REPEATING | AUTOKEY

    // Getters and Setters
    public String getAlgorithm() { return algorithm; }
    public void setAlgorithm(String algorithm) { this.algorithm = algorithm; }
    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    public String getKeyType() { return keyType; }
    public void setKeyType(String keyType) { this.keyType = keyType; }
}
