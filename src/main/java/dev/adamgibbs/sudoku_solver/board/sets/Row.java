package dev.adamgibbs.sudoku_solver.board.sets;

import dev.adamgibbs.sudoku_solver.board.Cell;
import dev.adamgibbs.sudoku_solver.board.CellSet;

public class Row extends CellSet {

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
