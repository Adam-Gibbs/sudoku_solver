package dev.adamgibbs.sudoku_solver.board;

import dev.adamgibbs.sudoku_solver.board.saves.SavedBoard;
import dev.adamgibbs.sudoku_solver.board.sets.Row;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class Board implements Cloneable {
    private ArrayList<CellSet> setList = new ArrayList<>();
    private ArrayList<Row> rowList = new ArrayList<>();

    private void removeTempFromCellList(ArrayList<Cell> removeCellList, ArrayList<Integer> removeTempList) {
        removeCellList.forEach(cell -> cell.removeTemp(removeTempList));
    }

    public Map<Cell, Integer> generateRandomChange() {
        Map<Cell, Integer> lastChange = new HashMap<>();

        for (CellSet set : setList) {
            for (Map.Entry<Cell, Integer> entry : set.getCellTempCounts().entrySet()) {
                if (!entry.getKey().hasValue()) {
                    Cell chosenCell = entry.getKey();
                    lastChange.put(chosenCell, chosenCell.getTempValues().get(0));
                    chosenCell.removeTemp(chosenCell.getTempValues().get(0));
                    return lastChange;
                }
            }
        }

        return lastChange;
    }

    public void implementChange(Map<Cell, Integer> change) throws IllegalStateException {
        if (change.size() < 1) {
            throw new IllegalStateException("There is no change to revert from");
        } else if (change.size() > 1) {
            throw new IllegalStateException("There are multiple changes to revert from");
        }

        for (Map.Entry<Cell, Integer> entry : change.entrySet()) {
            entry.getKey().setValue(entry.getValue());
        }
    }

    public SavedBoard saveBoard() {
        SavedBoard saveBoard = new SavedBoard();

        for (Row row : rowList) {
            saveBoard.add(row.saveSet());
        }

        return saveBoard;
    }

    public void loadBoard(SavedBoard saveBoard) {
        for (int i = 0; i < saveBoard.getSaveSets().size(); i++) {
            rowList.get(i).loadSet(saveBoard.getSaveSets().get(i));
        }

        setList.forEach(item -> item.reloadValues());
    }

    public void resetAllChanges() {
        setList.forEach(item -> item.resetChanges());
    }

    public Boolean hasChange() {
        for (CellSet set : setList) {
            if (set.checkChange()) {
                return true;
            }
        }

        return false;
    }

    public Boolean isComplete() {
        for (CellSet set : setList) {
            if (!set.checkComplete()) {
                return false;
            }
        }

        return true;
    }

    public Boolean isSolvable() {
        for (CellSet set : setList) {
            if (!set.checkSolvable()) {
                return false;
            }
        }

        return true;
    }

    public void createTemps() {
        setList.forEach(set -> set.setValueList());
        setList.forEach(set -> set.populateTemp());
        setList.forEach(set -> set.calculateSetTemp());
    }

    public void recheckTemp() {
        setList.forEach(set -> set.setValueList());
        setList.forEach(set -> set.calculateSetTemp());
    }
    
    public void setOnlyTempValues() {
        setList.forEach(set -> set.setOnlyValues());
    }

    public void setTempValuesInSet() {
        for (CellSet set : setList) {
            set.setOnlyValuesInSet();
            if (this.hasChange()) {
                this.recheckTemp();
            }
        }
    }

    public void removeSimilarTempsIfDuplicates() {
    
        for (CellSet set : setList) {
            ArrayList<Integer> valueList = new ArrayList<>();
            ArrayList<Cell> cellList = new ArrayList<>();
            cellList.addAll(set.getCellList());
            for (Map.Entry<Cell, List<Integer>> entry : set.getDoubles().entrySet()) {
                cellList.remove(entry.getKey());
                valueList.addAll(entry.getValue());
            }
            removeTempFromCellList(cellList, valueList);
        }
    }

    @Override
    public String toString() {
        String bottomRow = "-------------------------------------";
        String topRows = "";

        for (Row row : rowList) {
            topRows += row.toString();
        }

        return topRows + bottomRow;
    }
}
