package br.com.dio;

import br.com.dio.model.Board;
import br.com.dio.model.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static br.com.dio.util.BoardTemplate.BOARD_TEMPLATE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Main {

    // Scanner e Board como membros estaticos da classe
    private static final Scanner scanner = new Scanner(System.in);
    private static Board board;

    // Constantes para evitar "numeros magicos" no codigo
    private static final int BOARD_LIMIT = 9;
    private static final int MAX_COORDINATE = 8;
    private static final int MIN_COORDINATE = 0;
    private static final int MAX_VALUE = 9;
    private static final int MIN_VALUE = 1;

    public static void main(String[] args) {
        // Loop principal do jogo
        while (true) {
            exibirMenu();
            var option = scanner.nextInt();
            processarOpcao(option);
        }
    }

    private static void exibirMenu() {
        System.out.println("\nSelecione uma das opcoes a seguir");
        System.out.println("1 - Iniciar um novo Jogo");
        System.out.println("2 - Colocar um novo numero");
        System.out.println("3 - Remover um numero");
        System.out.println("4 - Visualizar jogo atual");
        System.out.println("5 - Verificar status do jogo");
        System.out.println("6 - Limpar jogo");
        System.out.println("7 - Finalizar jogo");
        System.out.println("8 - Sair");
    }

    private static void processarOpcao(int option) {
        switch (option) {
            case 1 -> startGame();
            case 2 -> inputNumber();
            case 3 -> removeNumber();
            case 4 -> showCurrentGame();
            case 5 -> showGameStatus();
            case 6 -> clearGame();
            case 7 -> finishGame();
            case 8 -> System.exit(0);
            default -> System.out.println("Opcao invalida, selecione uma das opcoes do menu");
        }
    }

    // Metodo unico para verificar se o jogo nao foi iniciado, evitando repeticao
    private static boolean jogoNaoIniciado() {
        if (isNull(board)) {
            System.out.println("O jogo ainda nao foi iniciado");
            return true;
        }
        return false;
    }

    private static void startGame() {
        if (nonNull(board)) {
            System.out.println("O jogo ja foi iniciado. Limpe o jogo atual para comecar um novo.");
            return;
        }

        // Simplificado para criar um tabuleiro padrao sem depender de argumentos de linha de comando
        List<List<Space>> spaces = new ArrayList<>();
        for (int i = 0; i < BOARD_LIMIT; i++) {
            spaces.add(new ArrayList<>());
            for (int j = 0; j < BOARD_LIMIT; j++) {
                // Aqui voce poderia carregar um tabuleiro padrao ou deixar todos os espacos em branco
                // Para este exemplo, todos comecam vazios e editaveis
                spaces.get(i).add(new Space(0, false));
            }
        }

        board = new Board(spaces);
        System.out.println("O jogo esta pronto para comecar");
    }

    private static void inputNumber() {
        if (jogoNaoIniciado()) return;

        System.out.println("Informe a linha em que o numero sera inserido (0-8)");
        var row = promptForValidNumber(MIN_COORDINATE, MAX_COORDINATE);
        System.out.println("Informe a coluna em que o numero sera inserido (0-8)");
        var col = promptForValidNumber(MIN_COORDINATE, MAX_COORDINATE);
        System.out.printf("Informe o numero que vai entrar na posicao [%s,%s] (1-9)\n", row, col);
        var value = promptForValidNumber(MIN_VALUE, MAX_VALUE);

        if (!board.changeValue(row, col, value)) {
            System.out.printf("A posicao [%s,%s] tem um valor fixo e nao pode ser alterada\n", row, col);
        }
    }

    private static void removeNumber() {
        if (jogoNaoIniciado()) return;

        System.out.println("Informe a linha de onde o numero sera removido (0-8)");
        var row = promptForValidNumber(MIN_COORDINATE, MAX_COORDINATE);
        System.out.println("Informe a coluna de onde o numero sera removido (0-8)");
        var col = promptForValidNumber(MIN_COORDINATE, MAX_COORDINATE);

        if (!board.clearValue(row, col)) {
            System.out.printf("A posicao [%s,%s] tem um valor fixo e nao pode ser alterada\n", row, col);
        }
    }

    private static void showCurrentGame() {
        if (jogoNaoIniciado()) return;

        // Logica de preenchimento dos argumentos do template reescrita para maior clareza
        Object[] args = new Object[BOARD_LIMIT * BOARD_LIMIT];
        int argPos = 0;
        List<List<Space>> spaces = board.getSpaces();

        for (int i = 0; i < BOARD_LIMIT; i++) {
            for (int j = 0; j < BOARD_LIMIT; j++) {
                Integer actualValue = spaces.get(i).get(j).getActual();
                args[argPos++] = isNull(actualValue) || actualValue == 0 ? " " : actualValue;
            }
        }

        System.out.println("Seu jogo se encontra da seguinte forma");
        System.out.printf((BOARD_TEMPLATE) + "\n", args);
    }


    private static void showGameStatus() {
        if (jogoNaoIniciado()) return;

        System.out.printf("O jogo atualmente se encontra no status %s\n", board.getStatus().getLabel());
        if (board.hasErrors()) {
            System.out.println("O jogo contem erros");
        } else {
            System.out.println("O jogo nao contem erros");
        }
    }

    private static void clearGame() {
        if (jogoNaoIniciado()) return;

        System.out.println("Tem certeza que deseja limpar seu jogo e perder todo seu progresso? (sim/nao)");
        var confirm = scanner.next();
        while (!confirm.equalsIgnoreCase("sim") && !confirm.equalsIgnoreCase("nao")) {
            System.out.println("Entrada invalida. Informe 'sim' ou 'nao'");
            confirm = scanner.next();
        }

        if (confirm.equalsIgnoreCase("sim")) {
            board.reset();
            System.out.println("Jogo limpo. Valores editaveis foram removidos.");
        }
    }

    private static void finishGame() {
        if (jogoNaoIniciado()) return;

        if (board.gameIsFinished()) {
            System.out.println("Parabens, voce concluiu o jogo!");
            showCurrentGame();
            board = null; // Reinicia o board para um proximo jogo
        } else if (board.hasErrors()) {
            System.out.println("Seu jogo contem erros, verifique seu tabuleiro e ajuste-o");
        } else {
            System.out.println("Voce ainda precisa preencher algum espaco em branco");
        }
    }

    private static int promptForValidNumber(final int min, final int max) {
        var current = scanner.nextInt();
        while (current < min || current > max) {
            System.out.printf("Entrada invalida. Informe um numero entre %s e %s\n", min, max);
            current = scanner.nextInt();
        }
        return current;
    }
}