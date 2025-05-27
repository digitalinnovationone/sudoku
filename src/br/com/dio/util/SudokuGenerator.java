package br.com.dio.util;

import br.com.dio.Difficulty;
import br.com.dio.model.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SudokuGenerator {

    public static List<List<Space>> generate(Difficulty level) {
        List<List<Space>> board = new ArrayList<>();

        int clues = switch (level) {
            case EASY -> 40;
            case MEDIUM -> 30;
            case HARD -> 20;
        };

        // Inicializa tabuleiro vazio
        for (int i = 0; i < 9; i++) {
            board.add(new ArrayList<>());
            for (int j = 0; j < 9; j++) {
                board.get(i).add(new Space(null, false));
            }
        }

        // Preenche aleatoriamente
        Random rand = new Random();
        while (clues > 0) {
            int row = rand.nextInt(9);
            int col = rand.nextInt(9);
            int value = rand.nextInt(9) + 1;

            Space space = board.get(row).get(col);
            if (space.getActual() == null) {
                space.setExpected(value);
                space.setActual(value);
                space.setFixed(true);
                clues--;
            }
        }

        return board;
    }
}
