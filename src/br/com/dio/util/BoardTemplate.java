package br.com.dio.util;

import br.com.dio.model.Space;

public class BoardTemplate {

    public static Space[][] generateBoard() {

        int[][] template = {
            {5,3,0,0,7,0,0,0,0},
            {6,0,0,1,9,5,0,0,0},
            {0,9,8,0,0,0,0,6,0},

            {8,0,0,0,6,0,0,0,3},
            {4,0,0,8,0,3,0,0,1},
            {7,0,0,0,2,0,0,0,6},

            {0,6,0,0,0,0,2,8,0},
            {0,0,0,4,1,9,0,0,5},
            {0,0,0,0,8,0,0,7,9}
        };

        Space[][] board = new Space[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boolean fixed = template[i][j] != 0;
                board[i][j] = new Space(template[i][j], fixed);
            }
        }

        return board;
    }
}