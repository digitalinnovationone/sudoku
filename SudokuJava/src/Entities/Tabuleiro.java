package Entities;

public class Tabuleiro {
    private int[][] grade;

    public Tabuleiro() {
        this.grade = new int[9][9];
        inicializarTabuleiro();
    }

    public void inicializarTabuleiro() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grade[i][j] = 0;
            }
        }
    }

    public boolean inserirNumero(int linha, int coluna, int numero) {
        if (linha < 0 || linha > 8 || coluna < 0 || coluna > 8 || numero < 1 || numero > 9) {
            return false;
        }
        if (grade[linha][coluna] == 0 && validarNumero(linha, coluna, numero)) {
            grade[linha][coluna] = numero;
            return true;
        }
        return false;
    }

    public boolean validarNumero(int linha, int coluna, int numero) {
        for (int i = 0; i < 9; i++) {
            if (grade[linha][i] == numero || grade[i][coluna] == numero) {
                return false;
            }
        }
        int inicioLinha = (linha / 3) * 3;
        int inicioColuna = (coluna / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grade[inicioLinha + i][inicioColuna + j] == numero) {
                    return false;
                }
            }
        }
        return true;
    }

    public void exibirTabuleiro() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print((grade[i][j] == 0 ? "." : grade[i][j]) + " ");
            }
            System.out.println();
        }
    }

    public boolean estaCompleto() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grade[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}