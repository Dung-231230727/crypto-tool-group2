package com.group2.crypto.model;

public class VigenereRequest {
    private String text = "";
    private String key = "";
    private String mode = "ENCRYPT";      // ENCRYPT | DECRYPT
    private String keyType = "REPEATING"; // REPEATING | AUTOKEY

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }
    public String getKeyType() { return keyType; }
    public void setKeyType(String keyType) { this.keyType = keyType; }
}
