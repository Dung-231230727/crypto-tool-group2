package com.group2.crypto.model;

public class VigenereStep {
    private int position;
    private char inputChar;
    private char keyChar;
    private int inputVal;
    private int keyVal;
    private String formula;
    private int outputVal;
    private char outputChar;
    private boolean alpha;

    public VigenereStep(int position, char inputChar, char keyChar,
                        int inputVal, int keyVal, String formula,
                        int outputVal, char outputChar, boolean alpha) {
        this.position = position;
        this.inputChar = inputChar;
        this.keyChar = keyChar;
        this.inputVal = inputVal;
        this.keyVal = keyVal;
        this.formula = formula;
        this.outputVal = outputVal;
        this.outputChar = outputChar;
        this.alpha = alpha;
    }

    public int getPosition() { return position; }
    public char getInputChar() { return inputChar; }
    public char getKeyChar() { return keyChar; }
    public int getInputVal() { return inputVal; }
    public int getKeyVal() { return keyVal; }
    public String getFormula() { return formula; }
    public int getOutputVal() { return outputVal; }
    public char getOutputChar() { return outputChar; }
    public boolean isAlpha() { return alpha; }
}
