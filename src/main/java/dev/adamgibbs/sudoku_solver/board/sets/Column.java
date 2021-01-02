package dev.adamgibbs.sudoku_solver.board.sets;

import dev.adamgibbs.sudoku_solver.board.Cell;
import dev.adamgibbs.sudoku_solver.board.CellSet;

public class Column extends CellSet {

    @Override
    public String toString() {
        String topRow = "-----\n";
        String dataRows = "";

        for (Cell cell : cellList) {
            for (int i = 0; i < 3; i++) {
                dataRows += "|";
                dataRows += cell.lineToString();
                dataRows += "|\n"; 
            }
            dataRows += "-----\n";
        }

        return topRow + dataRows;
    }
    
}
