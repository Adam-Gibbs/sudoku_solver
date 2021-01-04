package dev.adamgibbs.sudoku_solver.board.saves;

import java.util.ArrayList;

import lombok.Data;

@Data
public class SavedSet {
    ArrayList<SavedCell> savedCellList = new ArrayList<>();

    public void add(SavedCell savedCell) {
        savedCellList.add(savedCell);
    }
}
