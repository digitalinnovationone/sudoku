package br.com.dio.model;

import java.util.Collection;
import java.util.List;

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

    public GameStatusEnum getStatus() {
        if (spaces.stream().flatMap(Collection::stream).noneMatch(s -> !s.isFixed() && nonNull(s.getActual()))) {
            return NON_STARTED;
        }

        return spaces.stream().flatMap(Collection::stream).anyMatch(s -> isNull(s.getActual())) ? INCOMPLETE : COMPLETE;
    }

    public boolean hasErrors() {
        if (getStatus() == NON_STARTED) {
            return false;
        }

        return spaces.stream().flatMap(Collection::stream)
                .anyMatch(s -> nonNull(s.getActual()) && !s.getActual().equals(s.getExpected()));
    }

    public MoveResultEnum changeValue(final int col, final int row, final int value) {
        var space = spaces.get(col).get(row);
        if (space.isFixed()) {
            return MoveResultEnum.FIXED_SPACE;
        }

        if (nonNull(space.getActual()) && space.getActual().equals(value)) {
            return MoveResultEnum.SUCCESS;
        }

        for (int i = 0; i < 9; i++) {
            Space spaceInRow = spaces.get(i).get(row);
            if (nonNull(spaceInRow.getActual()) && spaceInRow.getActual().equals(value)) {
                return MoveResultEnum.INVALID_MOVE;
            }
        }

        for (int i = 0; i < 9; i++) {
            Space spaceInCol = spaces.get(col).get(i);
            if (nonNull(spaceInCol.getActual()) && spaceInCol.getActual().equals(value)) {
                return MoveResultEnum.INVALID_MOVE;
            }
        }

        int startCol = col - col % 3;
        int startRow = row - row % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Space spaceInBox = spaces.get(j + startCol).get(i + startRow);
                if (nonNull(spaceInBox.getActual()) && spaceInBox.getActual().equals(value)) {
                    return MoveResultEnum.INVALID_MOVE;
                }
            }
        }

        space.setActual(value);
        return MoveResultEnum.SUCCESS;
    }

    public boolean clearValue(final int col, final int row) {
        var space = spaces.get(col).get(row);
        if (space.isFixed()) {
            return false;
        }

        space.clearSpace();
        return true;
    }

    public void reset() {
        spaces.forEach(c -> c.forEach(Space::clearSpace));
    }

    public boolean gameIsFinished() {
        return !hasErrors() && getStatus().equals(COMPLETE);
    }
}