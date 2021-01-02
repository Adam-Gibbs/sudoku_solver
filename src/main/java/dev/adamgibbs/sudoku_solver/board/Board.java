package dev.adamgibbs.sudoku_solver.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class Board {
    private ArrayList<CellSet> setList = new ArrayList<>();

    private void removeTempFromCellList(ArrayList<Cell> removeCellList, ArrayList<Integer> removeTempList) {
        removeCellList.forEach(cell -> cell.removeTemp(removeTempList));
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
}
