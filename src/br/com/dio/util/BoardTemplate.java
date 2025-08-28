package br.com.dio.util;

import br.com.dio.model.Board;
import java.util.List;

public final class BoardTemplate {

    private BoardTemplate() {}

    public static String getBoardAsString(final Board board) {
        StringBuilder boardString = new StringBuilder();
        List<List<br.com.dio.model.Space>> spaces = board.getSpaces();

        boardString.append("""
            *************************************************************************************
            *|---0---||---1---||---2---|*|---3---||---4---||---5---|*|---6---||---7---||---8---|*
            """);

        for (int row = 0; row < 9; row++) {
            boardString.append("*|       ||       ||       |*|       ||       ||       |*|       ||       ||       |*\n");
            boardString.append(row).append("|  ");

            for (int col = 0; col < 9; col++) {
                String value = String.valueOf(spaces.get(row).get(col).getActual());
                boardString.append(spaces.get(row).get(col).getActual() == null ? " " : value);
                boardString.append("   ||  ");
            }
            boardString.setLength(boardString.length() - 5); // Remove extra '||  '
            boardString.append("   |*");
            boardString.append(row).append("\n");
            boardString.append("*|       ||       ||       |*|       ||       ||       |*|       ||       ||       |*\n");

            if ((row + 1) % 3 == 0) {
                boardString.append("*|-------||-------||-------|*|-------||-------||-------|*|-------||-------||-------|*\n");
            } else {
                boardString.append("*|-------||-------||-------|*|-------||-------||-------|*|-------||-------||-------|*\n");
            }
        }

        boardString.append("""
            *|---0---||---1---||---2---|*|---3---||---4---||---5---|*|---6---||---7---||---8---|*
            *************************************************************************************
            """);

        return boardString.toString();
    }
}