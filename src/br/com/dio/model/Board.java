package br.com.dio.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.dio.model.GameStatusEnum.*;

public class Board {

    private final List<List<Space>> spaces;

    /**
     * Construtor do tabuleiro com cópia defensiva das linhas.
     */
    public Board(final List<List<Space>> spaces) {
        this.spaces = spaces.stream()
                .map(ArrayList::new) // cria cópia de cada linha
                .collect(Collectors.toList()); // compatível com Java 8
    }

    public List<List<Space>> getSpaces() {
        return spaces;
    }

    /**
     * Retorna o status atual do tabuleiro: não iniciado, incompleto ou completo.
     */
    public GameStatusEnum getStatus() {
        boolean anyFilled = spaces.stream()
                .flatMap(Collection::stream)
                .anyMatch(s -> !s.isFixed() && s.getValue() != null);

        if (!anyFilled) return NON_STARTED;

        boolean anyEmpty = spaces.stream()
                .flatMap(Collection::stream)
                .anyMatch(s -> s.getValue() == null);

        return anyEmpty ? INCOMPLETE : COMPLETE;
    }

    /**
     * Valida se uma jogada é permitida no tabuleiro (respeitando regras do Sudoku).
     */
    public boolean isValidMove(int row, int col, Integer value) {
        if (value == null || value < 1 || value > 9) return false;
        if (spaces.get(row).get(col).isFixed()) return false;

        // Verifica se o número já está na linha
        for (int c = 0; c < 9; c++) {
            if (c != col && value.equals(spaces.get(row).get(c).getValue())) {
                return false;
            }
        }

        // Verifica se o número já está na coluna
        for (int r = 0; r < 9; r++) {
            if (r != row && value.equals(spaces.get(r).get(col).getValue())) {
                return false;
            }
        }

        // Verifica se o número já está no bloco 3x3
        int boxRowStart = (row / 3) * 3;
        int boxColStart = (col / 3) * 3;

        for (int r = boxRowStart; r < boxRowStart + 3; r++) {
            for (int c = boxColStart; c < boxColStart + 3; c++) {
                if ((r != row || c != col) && value.equals(spaces.get(r).get(c).getValue())) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Insere um número no tabuleiro, se for permitido.
     */
    public boolean setNumber(int row, int col, Integer value) {
        if (isValidMove(row, col, value)) {
            spaces.get(row).get(col).setValue(value);
            return true;
        }
        return false;
    }

    /**
     * Imprime o tabuleiro no terminal.
     */
    public void printBoard() {
        System.out.println("+-------+-------+-------+");
        for (int i = 0; i < spaces.size(); i++) {
            System.out.print("| ");
            for (int j = 0; j < spaces.get(i).size(); j++) {
                Integer val = spaces.get(i).get(j).getValue();
                System.out.print((val == null ? "." : val) + " ");
                if ((j + 1) % 3 == 0) System.out.print("| ");
            }
            System.out.println();
            if ((i + 1) % 3 == 0) System.out.println("+-------+-------+-------+");
        }
    }
}
