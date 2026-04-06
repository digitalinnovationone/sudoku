package br.com.dio.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static br.com.dio.model.GameStatusEnum.COMPLETE;
import static br.com.dio.model.GameStatusEnum.INCOMPLETE;
import static br.com.dio.model.GameStatusEnum.NON_STARTED;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Board {

    private final List<List<Space>> spaces;

    public Board(final List<List<Space>> spaces) {
        this.spaces = spaces;
    }

    public List<List<Space>> getSpaces() {
        return spaces;
    }

    // 🔹 Centraliza o acesso aos espaços como stream
    private Stream<Space> streamSpaces() {
        return spaces.stream().flatMap(Collection::stream);
    }

    // 🔹 Método seguro para acessar posição
    private Space getSpace(int col, int row) {
        if (col < 0 || col >= spaces.size() ||
                row < 0 || row >= spaces.get(col).size()) {
            throw new IllegalArgumentException("Posição inválida");
        }
        return spaces.get(col).get(row);
    }

    public GameStatusEnum getStatus() {
        boolean hasValue = false;
        boolean hasEmpty = false;

        for (Space space : streamSpaces().toList()) {
            if (!space.isFixed() && nonNull(space.getActual())) {
                hasValue = true;
            }
            if (isNull(space.getActual())) {
                hasEmpty = true;
            }
        }

        if (!hasValue) return NON_STARTED;
        return hasEmpty ? INCOMPLETE : COMPLETE;
    }

    public boolean hasErrors() {
        if (getStatus() == NON_STARTED) {
            return false;
        }

        return streamSpaces()
                .anyMatch(s -> nonNull(s.getActual()) && !s.getActual().equals(s.getExpected()));
    }

    public boolean changeValue(final int col, final int row, final int value) {
        var space = getSpace(col, row);

        if (space.isFixed()) {
            return false;
        }

        space.setActual(value);
        return true;
    }

    public boolean clearValue(final int col, final int row) {
        var space = getSpace(col, row);

        if (space.isFixed()) {
            return false;
        }

        space.clearSpace();
        return true;
    }

    public void reset() {
        spaces.forEach(column ->
                column.forEach(space -> {
                    if (!space.isFixed()) {
                        space.clearSpace();
                    }
                })
        );
    }

    public boolean gameIsFinished() {
        return getStatus() == COMPLETE && !hasErrors();
    }
}
