package com.group2.crypto.model;

import java.util.List;

public class RsaResponse {
    private String n;
    private String phi;
    private String e;
    private String d;
    private String publicKey;
    private String privateKey;
    private String ciphertext;
    private String plaintext;
    private List<String> transcript;
    private String errorMessage;

    public String getN() { return n; }
    public void setN(String n) { this.n = n; }
    public String getPhi() { return phi; }
    public void setPhi(String phi) { this.phi = phi; }
    public String getE() { return e; }
    public void setE(String e) { this.e = e; }
    public String getD() { return d; }
    public void setD(String d) { this.d = d; }
    public String getPublicKey() { return publicKey; }
    public void setPublicKey(String publicKey) { this.publicKey = publicKey; }
    public String getPrivateKey() { return privateKey; }
    public void setPrivateKey(String privateKey) { this.privateKey = privateKey; }
    public String getCiphertext() { return ciphertext; }
    public void setCiphertext(String ciphertext) { this.ciphertext = ciphertext; }
    public String getPlaintext() { return plaintext; }
    public void setPlaintext(String plaintext) { this.plaintext = plaintext; }
    public List<String> getTranscript() { return transcript; }
    public void setTranscript(List<String> transcript) { this.transcript = transcript; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
