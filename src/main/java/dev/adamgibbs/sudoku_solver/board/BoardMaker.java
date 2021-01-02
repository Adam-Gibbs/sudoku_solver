package dev.adamgibbs.sudoku_solver.board;

import dev.adamgibbs.sudoku_solver.board.sets.*;
import java.util.ArrayList;

public class BoardMaker {

    public Board createBoard(ArrayList<Integer> cellValueList) throws IllegalArgumentException {
        if (cellValueList.size() != 81) {
            throw new IllegalArgumentException("Must Contain 81 cell values in build list");
        }
        ArrayList<Column> columnList = new ArrayList<>();
        ArrayList<Row> rowList = new ArrayList<>();
        ArrayList<Box> boxList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            columnList.add(new Column());
            rowList.add(new Row());
            boxList.add(new Box());
        }

        for (int i = 0; i < 81; i++) {
            Cell newCell = new Cell();
            if (cellValueList.get(i) != 0) {
                newCell.setValue(cellValueList.get(i));
            }
            columnList.get(i % 9).addCell(newCell);
            rowList.get(Math.floorDiv(i, 9)).addCell(newCell);
            boxList.get((Math.floorDiv(i, 3)) % 3);
        }

        ArrayList<CellSet> setList = new ArrayList<>();
        setList.addAll(columnList);
        setList.addAll(rowList);
        setList.addAll(boxList);

        Board newBoard = new Board();
        newBoard.setSetList(setList);

        return newBoard;
    }
}
