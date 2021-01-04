package dev.adamgibbs.sudoku_solver.board.saves;

import java.util.Stack;

import dev.adamgibbs.sudoku_solver.board.Board;

public class SaveManager {
    private Stack<SavedBoard> boardList = new Stack<>();

    public void saveCurrent(Board toSave) {
        boardList.push(toSave.saveBoard());
    }

    public void loadLastSave(Board loadTarget) throws IllegalStateException {
        if (boardList.size() < 1) {
            throw new IllegalStateException("No boards to load");
        }
        SavedBoard loadedBoard = boardList.pop();
        loadTarget.loadBoard(loadedBoard);
    }
}
