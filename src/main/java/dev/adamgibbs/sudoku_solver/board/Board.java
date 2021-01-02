package dev.adamgibbs.sudoku_solver.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class Board implements Cloneable {
    private ArrayList<CellSet> setList = new ArrayList<>();
    private Map<Cell, Integer> lastChange;

    private void removeTempFromCellList(ArrayList<Cell> removeCellList, ArrayList<Integer> removeTempList) {
        removeCellList.forEach(cell -> cell.removeTemp(removeTempList));
    }

    public void removeLastChange() throws IllegalStateException{
        if (lastChange.size() < 1) {
            throw new IllegalStateException("There is no change to revert from");
        } else if (lastChange.size() > 1) {
            throw new IllegalStateException("There are multiple changes to revert from");
        }

        for (Map.Entry<Cell, Integer> entry : lastChange.entrySet()) {
            entry.getKey().removeTemp(entry.getValue());
        }

        lastChange.clear();
    }

    public void generateRandomChange() {
        for (CellSet set : setList) {
            for (Map.Entry<Cell, Integer> entry : set.getCellTempCounts().entrySet()) {
                lastChange.put(entry.getKey(), entry.getKey().getTempValues().get(0));
            }
        }
    }

    public void implementRandomChange() throws IllegalStateException {
        if (lastChange.size() < 1) {
            throw new IllegalStateException("There is no change to revert from");
        } else if (lastChange.size() > 1) {
            throw new IllegalStateException("There are multiple changes to revert from");
        }

        for (Map.Entry<Cell, Integer> entry : lastChange.entrySet()) {
            entry.getKey().setValue(entry.getValue());
        }
    }

    public void createTemps() {
        setList.forEach(set -> set.populateTemp());
        setList.forEach(set -> set.calculateSetTemp());
    }

    public void recheckTemp() {
        setList.forEach(set -> set.calculateSetTemp());
    }
    
    public void setOnlyTempValues() {
        setList.forEach(set -> set.setOnlyValues());
    }

    public void setTempValuesInSet() {
        setList.forEach(set -> set.setOnlyValuesInSet());
    }

    public void removeSimilarTempsIfDuplicates() {
        ArrayList<Cell> cellList = new ArrayList<>();
        ArrayList<Integer> valueList = new ArrayList<>();
    
        for (CellSet set : setList) {
            for (Map.Entry<Cell, List<Integer>> entry : set.getDoubles().entrySet()) {
                cellList.add(entry.getKey());
                valueList.addAll(entry.getValue());
            }
        }

        removeTempFromCellList(cellList, valueList);
    }

    public Board getClone() throws CloneNotSupportedException {
        return (Board) this.clone();
    }
}
