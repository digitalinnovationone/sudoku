package br.com.dio;

import br.com.dio.model.Board;
import br.com.dio.model.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

import static br.com.dio.util.BoardTemplate.BOARD_TEMPLATE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;

public class Main {

    private final static Scanner scanner = new Scanner(System.in);
    private static Board board;
    private final static int BOARD_LIMIT = 9;

    public static void main(String[] args) {
        final var positions = Stream.of(args)
                .collect(toMap(
                        k -> k.split(";")[0],
                        v -> v.split(";")[1]
                ));

        while (true) {
            System.out.println("\n🧩 Bem-vindo ao Sudoku!");
            System.out.println("1 - Iniciar um novo Jogo");
            System.out.println("2 - Colocar um novo número");
            System.out.println("3 - Remover um número");
            System.out.println("4 - Visualizar jogo atual");
            System.out.println("5 - Verificar status do jogo");
            System.out.println("6 - Limpar jogo");
            System.out.println("7 - Finalizar jogo");
            System.out.println("8 - Sair");

            int option = scanner.nextInt();

            switch (option) {
                case 1 -> startGame(positions);
                case 2 -> inputNumber();
                case 3 -> removeNumber();
                case 4 -> showCurrentGame();
                case 5 -> showGameStatus();
                case 6 -> clearGame();
                case 7 -> finishGame();
                case 8 -> {
                    System.out.println("👋 Obrigado por jogar! Até a próxima.");
                    System.exit(0);
                }
                default -> System.out.println("❌ Opção inválida! Escolha novamente.");
            }
        }
    }

    private static void startGame(final Map<String, String> positions) {
        if (nonNull(board)) {
            System.out.println("⚠ O jogo já foi iniciado!");
            return;
        }

        List<List<Space>> spaces = new ArrayList<>();
        for (int i = 0; i < BOARD_LIMIT; i++) {
            spaces.add(new ArrayList<>());
            for (int j = 0; j < BOARD_LIMIT; j++) {
                var positionConfig = positions.get(String.format("%d,%d", i, j));
                var expected = Integer.parseInt(positionConfig.split(",")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
                spaces.get(i).add(new Space(expected, fixed));
            }
        }

        board = new Board(spaces);
        System.out.println("✅ O jogo está pronto para começar!");
    }

    private static void inputNumber() {
        if (isNull(board)) {
            System.out.println("⚠ O jogo ainda não foi iniciado!");
            return;
        }

        System.out.println("Informe a coluna onde o número será inserido:");
        var col = runUntilGetValidNumber(0, 8);
        System.out.println("Informe a linha onde o número será inserido:");
        var row = runUntilGetValidNumber(0, 8);
        System.out.printf("Informe o número que vai entrar na posição [%d,%d]:\n", col, row);
        var value = runUntilGetValidNumber(1, 9);

        if (!board.changeValue(col, row, value)) {
            System.out.printf("🚫 A posição [%d,%d] tem um valor fixo e não pode ser alterada!\n", col, row);
        }
    }

    private static void removeNumber() {
        if (isNull(board)) {
            System.out.println("⚠ O jogo ainda não foi iniciado!");
            return;
        }

        System.out.println("Informe a coluna do número a ser removido:");
        var col = runUntilGetValidNumber(0, 8);
        System.out.println("Informe a linha do número a ser removido:");
        var row = runUntilGetValidNumber(0, 8);

        if (!board.clearValue(col, row)) {
            System.out.printf("🚫 A posição [%d,%d] tem um valor fixo e não pode ser alterada!\n", col, row);
        }
    }

    private static void showCurrentGame() {
        if (isNull(board)) {
            System.out.println("⚠ O jogo ainda não foi iniciado!");
            return;
        }

        System.out.println("📌 Seu jogo está assim:");

        for (List<Space> row : board.getSpaces()) {
            for (Space space : row) {
                System.out.print(" " + (isNull(space.getActual()) ? " " : space.getActual()) + " ");
            }
            System.out.println();
        }
    }

    private static void showGameStatus() {
        if (isNull(board)) {
            System.out.println("⚠ O jogo ainda não foi iniciado!");
            return;
        }

        System.out.printf("📌 Status atual do jogo: %s\n", board.getStatus().getLabel());
        System.out.println(board.hasErrors() ? "❌ O jogo contém erros!" : "✅ O jogo não contém erros!");
    }

    private static void clearGame() {
        if (isNull(board)) {
            System.out.println("⚠ O jogo ainda não foi iniciado!");
            return;
        }

        System.out.println("Tem certeza que deseja limpar seu jogo? (sim/não)");
        var confirm = scanner.next().toLowerCase();

        if (confirm.equals("sim")) {
            board.reset();
            System.out.println("🔄 O jogo foi limpo!");
        }
    }

    private static void finishGame() {
        if (isNull(board)) {
            System.out.println("⚠ O jogo ainda não foi iniciado!");
            return;
        }

        if (board.gameIsFinished()) {
            System.out.println("🎉 Parabéns, você concluiu o jogo!");
            board = null;
        } else {
            System.out.println(board.hasErrors() ? "❌ O jogo contém erros!" : "⚠ Você ainda precisa preencher alguns espaços.");
        }
    }

    private static int runUntilGetValidNumber(final int min, final int max) {
        int current;
        do {
            System.out.printf("Informe um número entre %d e %d: ", min, max);
            current = scanner.nextInt();
        } while (current < min || current > max);
        return current;
    }
}