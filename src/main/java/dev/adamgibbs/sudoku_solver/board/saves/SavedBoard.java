package dev.adamgibbs.sudoku_solver.board.saves;

import java.util.ArrayList;

import lombok.Data;

@Data
public class SavedBoard {
    private ArrayList<SavedSet> saveSets = new ArrayList<>();

    public void add(SavedSet savedSet) {
        saveSets.add(savedSet);
    }
}
