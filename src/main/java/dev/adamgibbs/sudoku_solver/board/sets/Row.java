package dev.adamgibbs.sudoku_solver.board.sets;

import dev.adamgibbs.sudoku_solver.board.Cell;
import dev.adamgibbs.sudoku_solver.board.CellSet;
import dev.adamgibbs.sudoku_solver.board.saves.SavedSet;

public class Row extends CellSet {

    public SavedSet saveSet() {
        SavedSet saveSet = new SavedSet();

        for (Cell cell : cellList) {
            saveSet.add(cell.save());
        }

        return saveSet;
    }

    public void loadSet(SavedSet savedSet) {
        for (int i = 0; i < savedSet.getSavedCellList().size(); i++) {
            cellList.get(i).load(savedSet.getSavedCellList().get(i));
        }
    }

    @Override
    public String toString() {
        String topRow = "-------------------------------------\n";
        String dataRows = "";

        for (int i = 0; i < 3; i++) {
            dataRows += "|"; 
            for (Cell cell : cellList) {
                dataRows += cell.lineToString();
                dataRows += "|"; 
            }
            dataRows += "\n";
        }

        return topRow + dataRows;
    }

}
