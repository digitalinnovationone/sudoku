import br.com.dio.model.Board;
import br.com.dio.model.Space;
import br.com.dio.util.BoardTemplate;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Board board = new Board(BoardTemplate.generateBoard());

        while (!board.isComplete()) {

            printBoard(board.getSpaces());

            System.out.println("Digite linha (0-8): ");
            int row = scanner.nextInt();

            System.out.println("Digite coluna (0-8): ");
            int col = scanner.nextInt();

            System.out.println("Digite valor (1-9): ");
            int value = scanner.nextInt();

            if (!board.setValue(row, col, value)) {
                System.out.println("Movimento inválido!");
            }
        }

        System.out.println("Parabéns! Você completou o Sudoku!");
    }

    private static void printBoard(Space[][] board) {
        for (Space[] row : board) {
            for (Space s : row) {
                System.out.print((s.getValue() == 0 ? "." : s.getValue()) + " ");
            }
            System.out.println();
        }
    }
}