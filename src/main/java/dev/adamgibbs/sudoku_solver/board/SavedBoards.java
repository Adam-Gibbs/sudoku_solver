package dev.adamgibbs.sudoku_solver.board;

import java.util.Stack;

public class SavedBoards {
    private Stack<Board> boardList = new Stack<>();

    public void saveCurrent(Board toSave) {
        try {
            toSave.implementRandomChange();
            boardList.push(toSave.getClone());
        } catch (CloneNotSupportedException e) {
            System.out.println("Cannot save board because: " + e);
        } catch (IllegalStateException e) {
            System.out.println("Cannot implement last change because: " + e);
        }
    }

    public Board loadLastSave() throws IllegalStateException {
        if (boardList.size() < 1) {
            throw new IllegalStateException("No boards to load from");
        }
        Board loadedBoard = boardList.pop();
        try {
            loadedBoard.removeLastChange();
        } catch (IllegalStateException e) {
            System.out.println("Cannot revert last change because: " + e);
        }

        return loadedBoard;
    }
}
