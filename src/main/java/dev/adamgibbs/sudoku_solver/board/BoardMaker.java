package dev.adamgibbs.sudoku_solver.board;

import dev.adamgibbs.sudoku_solver.board.sets.*;

import java.util.ArrayList;

public class BoardMaker {
    private ArrayList<Integer> cellValueList;

    public BoardMaker addCellValues(ArrayList<Integer> cellValues) {
        cellValueList = cellValues;

        return this;
    }

    public BoardMaker addCellValue(Integer cellValue) {
        cellValueList.add(cellValue);

        return this;
    }

    public Board build() throws IllegalArgumentException {
        if (cellValueList.size() != 81) {
            throw new IllegalArgumentException("Must Contain 81 cell values in build list");
        }
        ArrayList<Column> columnList = new ArrayList<>();
        ArrayList<Row> rowList = new ArrayList<>();
        ArrayList<Box> boxList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            columnList.add(new Column());
            rowList.add(new Row());
            boxList.add(new Box());
        }

        for (int i = 0; i < 81; i++) {
            Cell newCell = new Cell();
            newCell.setPosition(i);
            if (cellValueList.get(i) != 0) {
                newCell.setValue(cellValueList.get(i));
            }
            columnList.get(i % 9).addCell(newCell);
            rowList.get(Math.floorDiv(i, 9)).addCell(newCell);

            Integer j = Math.floorDiv(i, 3);
            if (j == 0 || j == 3 || j == 6) {
                boxList.get(0).addCell(newCell);
            } else if (j == 1 || j == 4 || j == 7) {
                boxList.get(1).addCell(newCell);
            } else if (j == 2 || j == 5 || j == 8) {
                boxList.get(2).addCell(newCell);
            } else if (j == 9 || j == 12 || j == 15) {
                boxList.get(3).addCell(newCell);
            } else if (j == 10 || j == 13 || j == 16) {
                boxList.get(4).addCell(newCell);
            } else if (j == 11 || j == 14 || j == 17) {
                boxList.get(5).addCell(newCell);
            } else if (j == 18 || j == 21 || j == 24) {
                boxList.get(6).addCell(newCell);
            } else if (j == 19 || j == 22 || j == 25) {
                boxList.get(7).addCell(newCell);
            } else if (j == 20 || j == 23 || j == 26) {
                boxList.get(8).addCell(newCell);
            }
        }

        ArrayList<CellSet> setList = new ArrayList<>();
        setList.addAll(columnList);
        setList.addAll(rowList);
        setList.addAll(boxList);

        Board newBoard = new Board();
        newBoard.setSetList(setList);
        newBoard.setRowList(rowList);

        return newBoard;
    }
}
