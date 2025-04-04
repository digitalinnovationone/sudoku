package br.com.dio;

import br.com.dio.model.Board;
import br.com.dio.model.Space;

import java.util.*;
import java.util.stream.Stream;

import static br.com.dio.util.BoardTemplate.BOARD_TEMPLATE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;

public class Main {

    private final static Scanner scanner = new Scanner(System.in);
    private static Optional<Board> board = Optional.empty();

    private final static int BOARD_LIMIT = 9;

    public static void main(String[] args) {
        final var positions = Stream.of(args)
                .collect(toMap(
                        k -> k.split(";")[0],
                        v -> v.split(";")[1]
                ));
        var option = -1;
        while (true){
            System.out.println("Selecione uma das opções a seguir");
            System.out.println("1 - Iniciar um novo Jogo");
            System.out.println("2 - Colocar um novo número");
            System.out.println("3 - Remover um número");
            System.out.println("4 - Visualizar jogo atual");
            System.out.println("5 - Verificar status do jogo");
            System.out.println("6 - limpar jogo");
            System.out.println("7 - Finalizar jogo");
            System.out.println("8 - Sair");

            option = scanner.nextInt();

            switch (option){
                case 1 -> startGame(positions);
                case 2 -> inputNumber();
                case 3 -> removeNumber();
                case 4 -> showCurrentGame();
                case 5 -> showGameStatus();
                case 6 -> clearGame();
                case 7 -> finishGame();
                case 8 -> System.exit(0);
                default -> System.out.println("Opção inválida, selecione uma das opções do menu");
            }
        }
    }


    private static void startGame(final Map<String, String> positions) {
        if (board.isPresent()){
            System.out.println("O jogo já foi iniciado");
            return;
        }

        List<List<Space>> spaces = new ArrayList<>();
        for (int i = 0; i < BOARD_LIMIT; i++) {
            spaces.add(new ArrayList<>());
            for (int j = 0; j < BOARD_LIMIT; j++) {
                var positionConfig = positions.get("%s,%s".formatted(i, j));
                var expected = Integer.parseInt(positionConfig.split(",")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
                var currentSpace = new Space(expected, fixed);
                spaces.get(i).add(currentSpace);
            }
        }

        board = Optional.of(new Board(spaces));
        System.out.println("O jogo está pronto para começar");
    }


    private static void inputNumber() {
        if (!board.isPresent()){
            System.out.println("O jogo ainda não foi iniciado iniciado");
            return;
        }

        System.out.println("Informe a coluna que em que o número será inserido");
        var col = runUntilGetValidNumber(0, 8);
        System.out.println("Informe a linha que em que o número será inserido");
        var row = runUntilGetValidNumber(0, 8);
        System.out.printf("Informe o número que vai entrar na posição [%s,%s]\n", col, row);
        var value = runUntilGetValidNumber(1, 9);
        if (!board.map(Board::hasErrors).orElse(false)){
            System.out.printf("A posição [%s,%s] tem um valor fixo\n", col, row);
        }
    }

    private static void removeNumber() {
        if (!board.isPresent()){
            System.out.println("O jogo ainda não foi iniciado iniciado");
            return;
        }
        var col = getBoardPosition("coluna");
        var row = getBoardPosition("linha");
        if (!board.map(Board::hasErrors).orElse(false)){
            System.out.printf("A posição [%s,%s] tem um valor fixo\n", col, row);
        }
    }

    private static void showCurrentGame() {
        if (!board.isPresent()){
            System.out.println("O jogo ainda não foi iniciado iniciado");
            return;
        }

        Board currentBoard = board.get();

        var args = new Object[81];
        var argPos = 0;
        for (int i = 0; i < BOARD_LIMIT; i++) {
            for (var col: currentBoard.getSpaces()){
                args[argPos ++] = " " + ((isNull(col.get(i).getActual())) ? " " : col.get(i).getActual());
            }
        }
        System.out.println("Seu jogo se encontra da seguinte forma");
        System.out.printf((BOARD_TEMPLATE) + "\n", args);
    }

    private static void showGameStatus() {
        if (!board.isPresent()){
            System.out.println("O jogo ainda não foi iniciado iniciado");
            return;
        }
        Board currentBoard = board.get();  // Obtém o Board dentro do Optional

        System.out.printf("O jogo atualmente se encontra no status %s\n", currentBoard.getStatus().getLabel());
        if(board.map(Board::hasErrors).orElse(false)){
            System.out.println("O jogo contém erros");
        } else {
            System.out.println("O jogo não contém erros");
        }
    }

    private static void clearGame() {
        if (!board.isPresent()){
            System.out.println("O jogo ainda não foi iniciado iniciado");
            return;
        }

        System.out.println("Tem certeza que deseja limpar seu jogo e perder todo seu progresso?");
        var confirm = scanner.next();
        while (!confirm.equalsIgnoreCase("sim") && !confirm.equalsIgnoreCase("não")){
            System.out.println("Informe 'sim' ou 'não'");
            confirm = scanner.next();
        }

        if(confirm.equalsIgnoreCase("sim")){
            board.ifPresent(Board::reset);
        }
    }

    private static void finishGame() {
        if (!board.isPresent()){
            System.out.println("O jogo ainda não foi iniciado iniciado");
            return;
        }

        if (board.map(Board::gameIsFinished).orElse(false)){
            System.out.println("Parabéns você concluiu o jogo");
            showCurrentGame();
            board = null;
        } else if (board.map(Board::hasErrors).orElse(false)) {
            System.out.println("Seu jogo conté, erros, verifique seu board e ajuste-o");
        } else {
            System.out.println("Você ainda precisa preenhcer algum espaço");
        }
    }


    private static int getBoardPosition(String dimension) {
        System.out.printf("Informe a %s (0 a 8):\n", dimension);
        return runUntilGetValidNumber(0, 8);
    }


    private static int runUntilGetValidNumber(final int min, final int max) {
        int current;
        while (true) {
            try {
                current = scanner.nextInt();
                if (current >= min && current <= max) {
                    return current;
                } else {
                    System.out.printf("Informe um número entre %s e %s\n", min, max);
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Por favor, informe um número.");
                scanner.next();
            }
        }
    }

}
