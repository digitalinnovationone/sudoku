package br.com.dio;

import java.util.function.DoubleSupplier;

public enum Difficulty implements DoubleSupplier {
    EASY(40), MEDIUM(30), HARD(20);

    private final int clues;

    Difficulty(int clues){
        this.clues = clues;
    }

    public int getClues(){
        return clues;
    }

    @Override
    public double getAsDouble() {
        return 0;
    }
}
