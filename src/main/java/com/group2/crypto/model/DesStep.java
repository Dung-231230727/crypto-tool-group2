package com.group2.crypto.model;

public class DesStep {
    private String round;
    private String l_prev;
    private String r_prev;
    private String e_out;
    private String xor_k;
    private String sbox_out;
    private String pbox_out;
    private String left;
    private String right;
    private String key;
    private String leftBin;
    private String rightBin;
    private String keyBin;
    private String e_outBin;
    private String xor_kBin;
    private String sbox_outBin;
    private String pbox_outBin;

    public DesStep() {}

    public DesStep(String round, String l_prev, String r_prev, String e_out, String xor_k, String sbox_out, String pbox_out, String left, String right, String key, String leftBin, String rightBin, String keyBin, String e_outBin, String xor_kBin, String sbox_outBin, String pbox_outBin) {
        this.round = round;
        this.l_prev = l_prev;
        this.r_prev = r_prev;
        this.e_out = e_out;
        this.xor_k = xor_k;
        this.sbox_out = sbox_out;
        this.pbox_out = pbox_out;
        this.left = left;
        this.right = right;
        this.key = key;
        this.leftBin = leftBin;
        this.rightBin = rightBin;
        this.keyBin = keyBin;
        this.e_outBin = e_outBin;
        this.xor_kBin = xor_kBin;
        this.sbox_outBin = sbox_outBin;
        this.pbox_outBin = pbox_outBin;
    }

    public String getRound() { return round; }
    public void setRound(String round) { this.round = round; }
    public String getL_prev() { return l_prev; }
    public void setL_prev(String l_prev) { this.l_prev = l_prev; }
    public String getR_prev() { return r_prev; }
    public void setR_prev(String r_prev) { this.r_prev = r_prev; }
    public String getE_out() { return e_out; }
    public void setE_out(String e_out) { this.e_out = e_out; }
    public String getXor_k() { return xor_k; }
    public void setXor_k(String xor_k) { this.xor_k = xor_k; }
    public String getSbox_out() { return sbox_out; }
    public void setSbox_out(String sbox_out) { this.sbox_out = sbox_out; }
    public String getPbox_out() { return pbox_out; }
    public void setPbox_out(String pbox_out) { this.pbox_out = pbox_out; }
    public String getLeft() { return left; }
    public void setLeft(String left) { this.left = left; }
    public String getRight() { return right; }
    public void setRight(String right) { this.right = right; }
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    public String getLeftBin() { return leftBin; }
    public void setLeftBin(String leftBin) { this.leftBin = leftBin; }
    public String getRightBin() { return rightBin; }
    public void setRightBin(String rightBin) { this.rightBin = rightBin; }
    public String getKeyBin() { return keyBin; }
    public void setKeyBin(String keyBin) { this.keyBin = keyBin; }
    public String getE_outBin() { return e_outBin; }
    public void setE_outBin(String e_outBin) { this.e_outBin = e_outBin; }
    public String getXor_kBin() { return xor_kBin; }
    public void setXor_kBin(String xor_kBin) { this.xor_kBin = xor_kBin; }
    public String getSbox_outBin() { return sbox_outBin; }
    public void setSbox_outBin(String sbox_outBin) { this.sbox_outBin = sbox_outBin; }
    public String getPbox_outBin() { return pbox_outBin; }
    public void setPbox_outBin(String pbox_outBin) { this.pbox_outBin = pbox_outBin; }
}
