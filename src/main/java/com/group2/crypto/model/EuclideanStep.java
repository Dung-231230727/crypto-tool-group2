package com.group2.crypto.model;

public class EuclideanStep {
    private int step;
    private String q;
    private long r;
    private long x;
    private long y;

    public EuclideanStep(int step, String q, long r, long x, long y) {
        this.step = step;
        this.q = q;
        this.r = r;
        this.x = x;
        this.y = y;
    }

    public int getStep() { return step; }
    public String getQ() { return q; }
    public long getR() { return r; }
    public long getX() { return x; }
    public long getY() { return y; }
}
