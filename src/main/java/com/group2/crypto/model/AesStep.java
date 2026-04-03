package com.group2.crypto.model;

public class AesStep {
    private String name;
    private int roundNumber;
    
    // Matrices for the vertical pipeline (Round-based)
    private String[][] stateStart;
    private String[][] stateAfterSub;
    private String[][] stateAfterShift;
    private String[][] stateAfterMix;
    private String[][] stateAfterAdd;
    private String[][] roundKey;
    
    // Binary versions for tooltips / details
    private String[][] binStateStart;
    private String[][] binStateAfterSub;
    private String[][] binStateAfterShift;
    private String[][] binStateAfterMix;
    private String[][] binStateAfterAdd;
    private String[][] binRoundKey;

    public AesStep() {}

    public AesStep(String name, int roundNumber) {
        this.name = name;
        this.roundNumber = roundNumber;
    }

    public void setStateStart(byte[][] data) {
        this.stateStart = convertToHex(data);
        this.binStateStart = convertToBin(data);
    }

    public void setStateAfterSub(byte[][] data) {
        this.stateAfterSub = convertToHex(data);
        this.binStateAfterSub = convertToBin(data);
    }

    public void setStateAfterShift(byte[][] data) {
        this.stateAfterShift = convertToHex(data);
        this.binStateAfterShift = convertToBin(data);
    }

    public void setStateAfterMix(byte[][] data) {
        this.stateAfterMix = convertToHex(data);
        this.binStateAfterMix = convertToBin(data);
    }

    public void setStateAfterAdd(byte[][] data) {
        this.stateAfterAdd = convertToHex(data);
        this.binStateAfterAdd = convertToBin(data);
    }

    public void setRoundKey(byte[][] data) {
        this.roundKey = convertToHex(data);
        this.binRoundKey = convertToBin(data);
    }

    private String[][] convertToHex(byte[][] data) {
        String[][] res = new String[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int val = data[i][j] & 0xFF;
                String hex = Integer.toHexString(val).toUpperCase();
                if (hex.length() == 1) hex = "0" + hex;
                res[i][j] = hex;
            }
        }
        return res;
    }

    private String[][] convertToBin(byte[][] data) {
        String[][] res = new String[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int val = data[i][j] & 0xFF;
                res[i][j] = String.format("%8s", Integer.toBinaryString(val)).replace(' ', '0');
            }
        }
        return res;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getRoundNumber() { return roundNumber; }
    public void setRoundNumber(int roundNumber) { this.roundNumber = roundNumber; }
    public String[][] getStateStart() { return stateStart; }
    public String[][] getStateAfterSub() { return stateAfterSub; }
    public String[][] getStateAfterShift() { return stateAfterShift; }
    public String[][] getStateAfterMix() { return stateAfterMix; }
    public String[][] getStateAfterAdd() { return stateAfterAdd; }
    public String[][] getRoundKey() { return roundKey; }
    public String[][] getBinStateStart() { return binStateStart; }
    public String[][] getBinStateAfterSub() { return binStateAfterSub; }
    public String[][] getBinStateAfterShift() { return binStateAfterShift; }
    public String[][] getBinStateAfterMix() { return binStateAfterMix; }
    public String[][] getBinStateAfterAdd() { return binStateAfterAdd; }
    public String[][] getBinRoundKey() { return binRoundKey; }
}
