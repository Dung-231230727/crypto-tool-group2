package com.group2.crypto.model;


public class PublicKeyRequest {
    private String algorithm = "DH"; // DH | ELGAMAL | DSA
    private String p = "";
    private String g = "";
    private String xA = ""; // Private key A
    private String yB = ""; // Public key B (for DH)
    private String k = "";  // k for ElGamal/DSA
    private String message = ""; // Message M to encrypt/sign
    private String q = ""; // For DSA
    private String h = ""; // For DSA (calculating g)
    private String subMethod = "CONFIDENTIALITY"; // RSA: CONFIDENTIALITY | SIGNATURE

    // Getters and Setters
    public String getSubMethod() { return subMethod; }
    public void setSubMethod(String subMethod) { this.subMethod = subMethod; }
    public String getAlgorithm() { return algorithm; }
    public void setAlgorithm(String algorithm) { this.algorithm = algorithm; }
    public String getP() { return p; }
    public void setP(String p) { this.p = p; }
    public String getG() { return g; }
    public void setG(String g) { this.g = g; }
    public String getXA() { return xA; }
    public void setXA(String xA) { this.xA = xA; }
    public String getYB() { return yB; }
    public void setYB(String yB) { this.yB = yB; }
    public String getK() { return k; }
    public void setK(String k) { this.k = k; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getQ() { return q; }
    public void setQ(String q) { this.q = q; }
    public String getH() { return h; }
    public void setH(String h) { this.h = h; }
}
