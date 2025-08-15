package br.com.dio;

import br.com.dio.model.Board;
import br.com.dio.model.MoveResultEnum;
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
        final Map<String, String> positions;
        positions = new java.util.HashMap<>();
        // Preenche todas as posições com 0,false
        for (int i = 0; i < BOARD_LIMIT; i++) {
            for (int j = 0; j < BOARD_LIMIT; j++) {
                positions.put(j + "," + i, "0,false");
            }
        }
        // Se houver argumentos, sobrescreve as posições informadas
        if (args.length > 0) {
            for (String arg : args) {
                String[] parts = arg.split(";");
                String[] pos = parts[0].split(",");
                // formato linha,coluna;valor,fixed -> pos[0]=linha, pos[1]=coluna
                String key = pos[1] + "," + pos[0]; // coluna,linha
                positions.put(key, parts[1]);
            }
        }
        var option = -1;
        while (true) {
            System.out.println("\n================ SUDOKU ================");
            System.out.println("Selecione uma das opções a seguir:");
            System.out.println("1 - Iniciar um novo Jogo");
            System.out.println("2 - Fazer uma jogada");
            System.out.println("3 - Remover um número");
            System.out.println("4 - Visualizar jogo atual");
            System.out.println("5 - Verificar status do jogo");
            System.out.println("6 - Resetar jogo");
            System.out.println("7 - Finalizar jogo");
            System.out.println("8 - Sair");
            System.out.println("========================================");


            option = runUntilGetValidNumber(1, 8);

            switch (option) {
                case 1 -> startGame(positions);
                case 2 -> inputNumber();
                case 3 -> removeNumber();
                case 4 -> showCurrentGame();
                case 5 -> showGameStatus();
                case 6 -> clearGame();
                case 7 -> finishGame();
                case 8 -> {
                    System.out.println("...Saindo do jogo. Até a próxima!");
                    System.exit(0);
                }
                default -> System.out.println("Opção inválida, selecione uma das opções do menu");
            }
        }
    }

    private static void startGame(final Map<String, String> positions) {
        if (nonNull(board)) {
            System.out.println(">> Jogo já iniciado. Para começar um novo, finalize ou resete o atual. <<");
            return;
        }

        List<List<Space>> spaces = new ArrayList<>();
        for (int i = 0; i < BOARD_LIMIT; i++) {
            spaces.add(new ArrayList<>());
            for (int j = 0; j < BOARD_LIMIT; j++) {
                // Acesso correto: chave coluna,linha
                var positionConfig = positions.get(j + "," + i);
                var expected = Integer.parseInt(positionConfig.split(",")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
                var currentSpace = new Space(expected, fixed);
                spaces.get(i).add(currentSpace);
            }
        }

        board = new Board(spaces);
        System.out.println("O jogo está pronto para começar!");
        showCurrentGame();
    }

    private static void inputNumber() {
        if (isNull(board)) {
            System.out.println(">> O jogo ainda não foi iniciado. Selecione a opção 1. <<");
            return;
        }

        System.out.println("Informe a coluna (0-8) em que o número será inserido:");
        var col = runUntilGetValidNumber(0, 8);
        System.out.println("Informe a linha (0-8) em que o número será inserido:");
        var row = runUntilGetValidNumber(0, 8);
        System.out.printf("Informe o número (1-9) que vai entrar na posição [%s,%s]:\n", col, row);
        var value = runUntilGetValidNumber(1, 9);

        Integer atual = board.getSpaces().get(col).get(row).getActual();
        MoveResultEnum result = board.changeValue(col, row, value);

        switch (result) {
            case SUCCESS -> {
                if (atual != null && atual.equals(value)) {
                    System.out.printf(">> Jogada inválida! O número %d já está nesta posição [%d,%d]. <<\n", value, col, row);
                } else {
                    System.out.println("   ...Número inserido com sucesso!");
                }
            }
            case FIXED_SPACE -> System.out.printf(">> Jogada Inválida! A posição [%d,%d] é fixa e não pode ser alterada. <<\n", col, row);
            case INVALID_MOVE -> System.out.printf(">> Jogada Inválida! O número %d já existe na linha, coluna ou bloco 3x3. <<\n", value);
        }
    }

    private static void removeNumber() {
        if (isNull(board)) {
            System.out.println(">> O jogo ainda não foi iniciado. Selecione a opção 1. <<");
            return;
        }

        System.out.println("Informe a coluna (0-8) do número a ser removido:");
        var col = runUntilGetValidNumber(0, 8);
        System.out.println("Informe a linha (0-8) do número a ser removido:");
        var row = runUntilGetValidNumber(0, 8);
        if (!board.clearValue(col, row)) {
            System.out.printf(">> A posição [%s,%s] tem um valor fixo e não pode ser limpa <<\n", col, row);
        } else {
            System.out.println("...Valor removido com sucesso!");
        }
    }

    private static void showCurrentGame() {
        if (isNull(board)) {
            System.out.println(">> O jogo ainda não foi iniciado. Selecione a opção 1. <<");
            return;
        }

        var args = new Object[81];
        var argPos = 0;
        for (int row = 0; row < BOARD_LIMIT; row++) {
            for (int col = 0; col < BOARD_LIMIT; col++) {
                Integer value = board.getSpaces().get(col).get(row).getActual();
                args[argPos++] = " " + (isNull(value) ? " " : value);
            }
        }
        System.out.println("Seu jogo se encontra da seguinte forma:");
        System.out.printf((BOARD_TEMPLATE) + "\n", args);
    }

    private static void showGameStatus() {
        if (isNull(board)) {
            System.out.println(">> O jogo ainda não foi iniciado. Selecione a opção 1. <<");
            return;
        }

        System.out.printf("O jogo atualmente se encontra no status: %s\n", board.getStatus().getLabel());
        if (board.hasErrors()) {
            System.out.println("Atenção: O jogo contém erros (números em posições incorretas).");
        } else {
            System.out.println("O jogo não contém erros.");
        }
    }

    private static void clearGame() {
        if (isNull(board)) {
            System.out.println(">> O jogo ainda não foi iniciado. Selecione a opção 1. <<");
            return;
        }

        System.out.println("Tem certeza que deseja limpar seu jogo e perder todo seu progresso? (sim/não)");
        var confirm = scanner.next();
        while (!confirm.equalsIgnoreCase("sim") && !confirm.equalsIgnoreCase("não")) {
            System.out.println("Entrada inválida. Informe 'sim' ou 'não'");
            confirm = scanner.next();
        }

        if (confirm.equalsIgnoreCase("sim")) {
            board.reset();
            System.out.println("...Jogo resetado para o estado inicial!");
        }
    }

    private static void finishGame() {
        if (isNull(board)) {
            System.out.println(">> O jogo ainda não foi iniciado. Selecione a opção 1. <<");
            return;
        }

        if (board.gameIsFinished()) {
            System.out.println("Parabéns, você concluiu o jogo com sucesso!");
            showCurrentGame();
            board = null;
        } else if (board.hasErrors()) {
            System.out.println("Não foi possível finalizar. Seu jogo contém erros, verifique o tabuleiro e ajuste-o.");
        } else {
            System.out.println("Não foi possível finalizar. Você ainda precisa preencher todos os espaços.");
        }
    }

    private static int runUntilGetValidNumber(final int min, final int max) {
        var current = -1;
        while(true) {
            try {
                current = Integer.parseInt(scanner.next());
                if (current >= min && current <= max) {
                    break;
                } else {
                    System.out.printf("Entrada inválida. Informe um número entre %s e %s\n", min, max);
                }
            } catch (NumberFormatException e) {
                System.out.printf("Entrada inválida. Por favor, digite um número.\n");
            }
        }
        return current;
    }
}