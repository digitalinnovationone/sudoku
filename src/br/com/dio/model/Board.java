package br.com.dio.model;

public class Board {

    private Space[][] spaces = new Space[9][9];
    private GameStatusEnum status = GameStatusEnum.NAO_INICIADO;

    public Board(Space[][] spaces) {
        this.spaces = spaces;
        this.status = GameStatusEnum.EM_ANDAMENTO;
    }

    public Space[][] getSpaces() {
        return spaces;
    }

    public boolean setValue(int row, int col, int value) {
        if (spaces[row][col].isFixed()) return false;

        if (isValid(row, col, value)) {
            spaces[row][col].setValue(value);
            return true;
        }
        return false;
    }

    private boolean isValid(int row, int col, int value) {

        for (int i = 0; i < 9; i++) {
            if (spaces[row][i].getValue() == value) return false;
            if (spaces[i][col].getValue() == value) return false;
        }

        int startRow = row - row % 3;
        int startCol = col - col % 3;

        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (spaces[i][j].getValue() == value) return false;
            }
        }

        return true;
    }

    public boolean isComplete() {
        for (Space[] row : spaces) {
            for (Space s : row) {
                if (s.getValue() == 0) return false;
            }
        }
        status = GameStatusEnum.COMPLETO;
        return true;
    }

    public GameStatusEnum getStatus() {
        return status;
    }
}