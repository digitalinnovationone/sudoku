package br.com.dio.model;

public class Space {

    private int value;
    private boolean fixed;

    public Space(int value, boolean fixed) {
        this.value = value;
        this.fixed = fixed;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (!fixed) {
            this.value = value;
        }
    }

    public boolean isFixed() {
        return fixed;
    }
}