package Services;

import Entities.Tabuleiro;

import java.util.Scanner;

public class JogoSudoku {
    private Tabuleiro tabuleiro;
    private Scanner scanner;

    public JogoSudoku() {
        tabuleiro = new Tabuleiro();
        scanner = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("Bem-vindo ao Sudoku!");
        while (!tabuleiro.estaCompleto()) {
            tabuleiro.exibirTabuleiro();
            System.out.print("Informe a linha (0-8): ");
            int linha = scanner.nextInt();
            System.out.print("Informe a coluna (0-8): ");
            int coluna = scanner.nextInt();
            System.out.print("Informe o número (1-9): ");
            int numero = scanner.nextInt();

            if (!tabuleiro.inserirNumero(linha, coluna, numero)) {
                System.out.println("Movimento inválido. Tente novamente.");
            }
        }
        System.out.println("Parabéns! Você completou o Sudoku!");
        tabuleiro.exibirTabuleiro();
    }
}