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
    private static int moveCount = 0;
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
            System.out.println("9 - Salvar jogo");
            System.out.println("10 - Carregar jogo");

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
                case 9 -> saveGame("sudoku_save.txt");
                case 10 -> loadGame("sudoku_save.txt");
                default -> System.out.println("\u001B[31mOpcao invalida, selecione uma das opcoes do menu\u001B[0m");
            }
        }
    }
    
    private static void saveGame(String filename) {
        if (isNull(board)) {
            System.out.println("\u001B[31mNenhum jogo iniciado para salvar.\u001B[0m");
            return;
        }
        try (java.io.PrintWriter writer = new java.io.PrintWriter(filename)) {
            for (int i = 0; i < BOARD_LIMIT; i++) {
                for (int j = 0; j < BOARD_LIMIT; j++) {
                    Space space = board.getSpaces().get(j).get(i);
                    int value = (space.getActual() == null ? 0 : space.getActual());
                    writer.printf("%d,%d;%d,%b\n", i, j, value, space.isFixed());
                }
            }
            writer.printf("moves;%d\n", moveCount);
            System.out.println("\u001B[32mProgresso salvo com sucesso!\u001B[0m");
        } catch (Exception e) {
            System.out.println("\u001B[31mErro ao salvar jogo: " + e.getMessage() + "\u001B[0m");
        }
    }         

    private static void loadGame(String filename) {
        try (java.util.Scanner fileScanner = new java.util.Scanner(new java.io.File(filename))) {
            List<List<Space>> spaces = new ArrayList<>();
            for (int i = 0; i < BOARD_LIMIT; i++) {
                spaces.add(new ArrayList<>());
            }

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.startsWith("moves;")) {
                    moveCount = Integer.parseInt(line.split(";")[1]);
                    continue;
                }
                String[] parts = line.split(";");
                String[] pos = parts[0].split(",");
                int row = Integer.parseInt(pos[0]);
                int col = Integer.parseInt(pos[1]);
                int value = Integer.parseInt(parts[1].split(",")[0]);
                boolean fixed = Boolean.parseBoolean(parts[1].split(",")[1]);
                spaces.get(row).add(new Space(value, fixed));
            }

            board = new Board(spaces);
            System.out.println("\u001B[32mProgresso carregado com sucesso!\u001B[0m");
        } catch (Exception e) {
            System.out.println("\u001B[31mErro ao carregar jogo: " + e.getMessage() + "\u001B[0m");
        }
    }
     
    private static void startGame(final Map<String, String> positions) {
        if (nonNull(board)){
            System.out.println("\u001B[31mO jogo ja foi iniciado\u001B[0m");
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
    
        board = new Board(spaces);
        System.out.println("\u001B[32mO jogo esta pronto para comecar\u001B[0m");
    }

    private static void inputNumber() {
        if (isNull(board)){
            System.out.println("\u001B[31mO jogo ainda nao foi iniciado\u001B[0m");
            return;
        }
        
        System.out.println("Informe a coluna que em que o numero sera inserido");
        var col = runUntilGetValidNumber(0, 8);
        System.out.println("Informe a linha que em que o numero sera inserido");
        var row = runUntilGetValidNumber(0, 8);
        System.out.printf("Informe o numero que vai entrar na posicao [%s,%s]\n", col, row);
        var value = runUntilGetValidNumber(1, 9);

        moveCount++;

        if (!isValidMove(board, col, row, value)) {
            System.out.println("\u001B[31mJogada invalida! Numero ja existe na linha, coluna ou bloco.\u001B[0m");
            return;
        }

        if (!board.changeValue(col, row, value)){
            System.out.printf("\u001B[31mA posicao [%s,%s] tem um valor fixo\u001B[0m\n", col, row);
        } else {
            System.out.println("\u001B[32mJogada aceita!\u001B[0m");
        }
         moveCount++;
    }

    private static void removeNumber() {
        if (isNull(board)){
            System.out.println("\u001B[31mO jogo ainda nao foi iniciado\u001B[0m");
            return;
        }

        System.out.println("Informe a coluna que em que o numero sera inserido");
        var col = runUntilGetValidNumber(0, 8);
        System.out.println("Informe a linha que em que o numero sera inserido");
        var row = runUntilGetValidNumber(0, 8);

        moveCount++;

        if (!board.clearValue(col, row)){
            System.out.printf("\u001B[31mA posicao [%s,%s] tem um valor fixo\u001B[0m\n", col, row);
        }else{
            System.out.println("\u001B[32mNumero removido com sucesso\u001B[0m");
             
        }
    }

    private static void showCurrentGame() {
        if (isNull(board)){
            System.out.println("\u001B[31mO jogo ainda nao foi iniciado\u001B[0m");
            return;
        }

        var args = new Object[81];
        var argPos = 0;
        for (int i = 0; i < BOARD_LIMIT; i++) {
            for (var col: board.getSpaces()){
                var space = col.get(i);
                if (isNull(space.getActual())) {
                    args[argPos++] = " " + "\u001B[37m.\u001B[0m";
                } else if (space.isFixed()) {
                    args[argPos++] = " " + "\u001B[34m" + space.getActual() + "\u001B[0m";
                } else {
                    args[argPos++] = " " + "\u001B[32m" + space.getActual() + "\u001B[0m";
                }
            }
        }
        System.out.println("Seu jogo se encontra da seguinte forma");
        System.out.printf((BOARD_TEMPLATE) + "\n", args);
    }

    private static void showGameStatus() {
        if (isNull(board)){
            System.out.println("\u001B[31mO jogo ainda nao foi iniciado\u001B[0m");
            return;
        }

        System.out.printf("O jogo atualmente se encontra no status %s\n", board.getStatus().getLabel());
        System.out.printf("Quantidade de jogadas realizadas: %d\n", moveCount);

        if(board.hasErrors()){
            System.out.println("\u001B[31mO jogo contem erros\u001B[0m");
        } else {
            System.out.println("\u001B[32mO jogo nao contem erros\u001B[0m");
        }
    } 

    private static void clearGame() {
        if (isNull(board)){
            System.out.println("\u001B[31mO jogo ainda nao foi iniciado\u001B[0m");
            return;
        }

        System.out.println("Tem certeza que deseja limpar seu jogo e perder todo seu progresso?");
        var confirm = scanner.next();
        while (!confirm.equalsIgnoreCase("sim") && !confirm.equalsIgnoreCase("nao")){
            System.out.println("Informe 'sim' ou 'nao'");
            confirm = scanner.next();
        }

        if(confirm.equalsIgnoreCase("sim")){
            board.reset();
            System.out.println("\u001B[32mJogo limpo com sucesso!\u001B[0m");
        }
    }

    private static void finishGame() {
        if (isNull(board)){
            System.out.println("\u001B[31mO jogo ainda nao foi iniciado\u001B[0m");
            return;
        }

        if (board.gameIsFinished()){
            System.out.println("\u001B[32mParabens voce concluiu o jogo\u001B[0m");
            showCurrentGame();
            board = null;
        } else if (board.hasErrors()) {
            System.out.println("\u001B[31mSeu jogo contem erros, verifique seu board e ajuste-o\u001B[0m");
        } else {
            System.out.println("\u001B[33mVoce ainda precisa preencher algum espaco\u001B[0m");
        }
    } 

    private static int runUntilGetValidNumber(final int min, final int max){
        var current = scanner.nextInt();
        while (current < min || current > max){
            System.out.printf("Informe um numero entre %s e %s\n", min, max);
            current = scanner.nextInt();
        }
        return current; 
    }

    private static boolean isValidMove(Board board, int col, int row, int value) {
        // Verifica linha
        for (int c = 0; c < BOARD_LIMIT; c++) {
            var space = board.getSpaces().get(c).get(row);
            if (space.getActual() != null && space.getActual() == value) {
                return false;
            }
        }
    
        // Verifica coluna
        for (int r = 0; r < BOARD_LIMIT; r++) {
            var space = board.getSpaces().get(col).get(r);
            if (space.getActual() != null && space.getActual() == value) {
                return false;
            }
        }

        // Verifica bloco 3x3
        int startCol = col - col % 3;
        int startRow = row - row % 3;
        for (int c = startCol; c < startCol + 3; c++) {
            for (int r = startRow; r < startRow + 3; r++) {
                var space = board.getSpaces().get(c).get(r);
                if (space.getActual() != null && space.getActual() == value) {
                    return false;
                }
            }
        }

        return true;
    }
}