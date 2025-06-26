package br.com.dio;

import br.com.dio.model.Board;
import br.com.dio.model.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Criar tabuleiro vazio 9x9
        List<List<Space>> spaces = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            List<Space> row = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                // 0 representa célula vazia no template
                row.add(new Space(0, false));
            }
            spaces.add(row);
        }

        // Exemplo: preencher algumas células fixas (pode modificar)
        spaces.get(0).set(0, new Space(5, true));
        spaces.get(1).set(3, new Space(6, true));
        spaces.get(2).set(6, new Space(9, true));
        // Adicione mais se quiser...

        Board board = new Board(spaces);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            board.printBoard();
            System.out.println("Status do jogo: " + board.getStatus());

            System.out.print("Digite linha (0-8), coluna (0-8) e valor (1-9), separados por espaço, ou 'exit': ");
            String line = scanner.nextLine();

            if (line.equalsIgnoreCase("exit")) {
                System.out.println("Saindo...");
                break;
            }

            String[] parts = line.trim().split("\s+");
            if (parts.length != 3) {
                System.out.println("Entrada inválida, tente novamente.");
                continue;
            }

            try {
                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);
                int val = Integer.parseInt(parts[2]);

                if (!board.setNumber(row, col, val)) {
                    System.out.println("Movimento inválido.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida, use números.");
            }
        }

        scanner.close();
    }
}
