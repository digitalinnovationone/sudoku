package br.com.dio.controller;

import br.com.dio.model.Board;
import br.com.dio.model.MenuOptionEnum;
import br.com.dio.model.Space;
import static br.com.dio.util.BoardTemplate.*;
import static br.com.dio.util.Constants.*;

import java.util.*;


import static java.util.Objects.*;

/**
 * Gerencia o ciclo de vida do jogo Sudoku
 * @author Vanessa Regina
 */
public class SudokuController {
    private SudokuController() {
        /* This utility class should not be instantiated */
    }

    private static Board board;
    private static final Scanner scanner = new Scanner(System.in);
    public static final int BOARD_LIMIT = 9;

    private static void startGame(final Map<String, String> positions) {
        if (nonNull(board)){
            System.out.println(JOGO_JA_INICIADO);
            return;
        }

        List<List<Space>> spaces = new ArrayList<>();
        for (int i = 0; i < BOARD_LIMIT; i++) {
            spaces.add(new ArrayList<>());
            for (int j = 0; j < BOARD_LIMIT; j++) {
                String positionKey = "%s,%s".formatted(i, j);
                var positionConfig = positions.get(positionKey);

                int expected = 0;
                boolean fixed = false;

                if (positionConfig != null) {
                    String[] configParts = positionConfig.split(",");
                    expected = Integer.parseInt(configParts[0]);
                    fixed = Boolean.parseBoolean(configParts[1]);
                }
                var currentSpace = new Space(expected, fixed);
                spaces.get(i).add(currentSpace);
            }
        }

        board = new Board(spaces);
        System.out.println(JOGO_PRONTO_COMECAR);
    }


    private static void inputNumber() {
        if (isNull(board)){
            System.out.println(JOGO_NAO_INICIADO);
            return;
        }

        System.out.println(INPUT_COLUNA);
        var col = runUntilGetValidNumber(0, 8);
        System.out.println(INPUT_LINHA);
        var row = runUntilGetValidNumber(0, 8);
        System.out.printf(INPUT_NUMERO, col, row);
        var value = runUntilGetValidNumber(1, 9);
        if (!board.changeValue(col, row, value)){
            System.out.printf(POSITION, col, row);
        }
    }

    private static void removeNumber() {
        if (isNull(board)){
            System.out.println(JOGO_NAO_INICIADO);
            return;
        }

        System.out.println(INPUT_COLUNA);
        var col = runUntilGetValidNumber(0, 8);
        System.out.println(INPUT_LINHA);
        var row = runUntilGetValidNumber(0, 8);
        if (!board.clearValue(col, row)){
            System.out.printf(POSITION, col, row);
        }
    }

    private static void showCurrentGame() {
        if (isNull(board)){
            System.out.println(JOGO_NAO_INICIADO);
            return;
        }

        var args = new Object[81];
        var argPos = 0;
        for (int i = 0; i < BOARD_LIMIT; i++) {
            for (var col: board.getSpaces()){
                args[argPos ++] = " " + ((isNull(col.get(i).getActual())) ? " " : col.get(i).getActual());
            }
        }
        System.out.println(BOARD_STATUS);
        System.out.printf((BOARD_TEMPLATE) + "\n", args);
    }

    private static void showGameStatus() {
        if (isNull(board)){
            System.out.println(JOGO_NAO_INICIADO);
            return;
        }

        System.out.printf(JOGO_STATUS_ATUAL, board.getStatus().getLabel());
        if(board.hasErrors()){
            System.out.println(JOGO_COM_ERROS);
        } else {
            System.out.println(JOGO_SEM_ERROS);
        }
    }

    private static void clearGame() {
        if (isNull(board)){
            System.out.println(JOGO_NAO_INICIADO);
            return;
        }

        System.out.println(CERTEZA_LIMPAR);
        var confirm = scanner.next();
        while (!confirm.equalsIgnoreCase(SIM) && !confirm.equalsIgnoreCase(NAO)){
            System.out.println(INFORME_SIM_NAO);
            confirm = scanner.next();
        }

        if(confirm.equalsIgnoreCase(SIM)){
            board.reset();
        }
    }

    private static void finishGame() {
        if (isNull(board)){
            System.out.println(JOGO_NAO_INICIADO);
            return;
        }

        if (board.gameIsFinished()){
            System.out.println(PARABENS);
            showCurrentGame();
            board = null;
        } else if (board.hasErrors()) {
            System.out.println(VERIFIQUE_SEU_BOARD_E_AJUSTE);
        } else {
            System.out.println(PREENCHER_ESPACO);
        }
    }

    private static int runUntilGetValidNumber(final int min, final int max){
        var current = scanner.nextInt();
        while (current < min || current > max){
            System.out.printf(INPUT_NUM_ENTRE_MIN_MAX, min, max);
            current = scanner.nextInt();
        }
        return current;
    }

    public static boolean handleMenuOption(Map<String, String> positions) {
        MenuOptionEnum option = null;
        try {
            var input = scanner.nextInt();
            option = MenuOptionEnum.fromId(input).orElse(null);
        } catch (InputMismatchException e) {
            scanner.next();
        }

        if (option == null) {
            System.out.println(OPCAO_INVALIDA);
            return true;
        }

        switch (option) {
            case INICIAR    -> startGame(positions);
            case ADICIONAR  -> inputNumber();
            case REMOVER    -> removeNumber();
            case VISUALIZAR -> showCurrentGame();
            case STATUS     -> showGameStatus();
            case LIMPAR     -> clearGame();
            case FINALIZAR  -> finishGame();
            case SAIR       -> {
                System.out.println(ENCERRANDO_O_JOGO);
                return false;
            }
            default -> System.out.println(OPCAO_INVALIDA);
        }
        return true;
    }
}
