package com.group2.crypto.model;

public class RsaRequest {
    private String p = "";
    private String q = "";
    private String e = "";
    private String message = "";

    public String getP() { return p; }
    public void setP(String p) { this.p = p; }
    public String getQ() { return q; }
    public void setQ(String q) { this.q = q; }
    public String getE() { return e; }
    public void setE(String e) { this.e = e; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
