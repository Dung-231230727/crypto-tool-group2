package com.group2.crypto.model;

public class ModuloRequest {
    private String a = "";
    private String n = "";
    private String method = "euclidean"; // default is euclidean

    public String getA() { return a; }
    public void setA(String a) { this.a = a; }
    public String getN() { return n; }
    public void setN(String n) { this.n = n; }
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
}
