package dev.adamgibbs.sudoku_solver.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.Data;

@Data
public class CellSet {
    private ArrayList<Cell> cellList = new ArrayList<>();
    private ArrayList<Integer> valueList;

    public void setValueList() {
        valueList = cellList.stream()
                            .map(cell -> cell.getValue())
                            .collect(Collectors
                            .toCollection(ArrayList::new));                            
    }

    public void addCell(Cell newCell) {
        if (!cellList.contains(newCell)) {
            cellList.add(newCell);
        }        
    }

    public LinkedHashMap<Cell, Integer> getCellTempCounts() {
        Map<Cell, Integer> cellTempCount = new HashMap<>();
        cellList.forEach(cell -> cellTempCount.put(cell, cell.tempValueCount()));

        LinkedHashMap<Cell, Integer> sortedMap = new LinkedHashMap<>();        
        cellTempCount.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue())
            .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));

        return sortedMap;
    }

    public void setOnlyValues() {
        for (Cell cell : cellList) {
            try {
                if (cell.tempValueCount() == 1) {
                    cell.setOnlyValue();
                }
            } catch(IllegalStateException e) {
                System.out.println("Cell set attempting to set only value in cell with error: " + e);
            }
        }
    }

    public void setOnlyValuesInSet() {
        ArrayList<Integer> totalValueList = new ArrayList<>();

        for (Cell cell : cellList) {
            totalValueList.addAll(cell.getTempValues());
        }

        // Remove all but single occurences from the list
        totalValueList.removeIf((number)->totalValueList.indexOf(number)!=totalValueList.lastIndexOf(number));
        
        for (Cell cell : cellList) {
            try {
                cell.setValueFromList(totalValueList);
            } catch(IllegalStateException e) {
                System.out.println("Cell set attempting to set only value in cell with error: " + e);
            }
        }
    }

    public Map<Cell, List<Integer>> getDoubles() {
        Map<Cell, List<Integer>> doubleMap = new HashMap<>();
        ArrayList<List<Integer>> doubleValues = new ArrayList<>();
        LinkedHashMap<Cell, Integer> cellTempCounts = getCellTempCounts();
        Set<Cell> keys = cellTempCounts.keySet();

        for(Cell cell : keys){
            if (cellTempCounts.get(cell) == 2) {
                doubleValues.add(cell.getTempValues());
                doubleMap.put(cell, cell.getTempValues());
            }
        }

        // Get only duplicates
        List<List<Integer>> duplicateList = doubleValues.stream().collect(
                                                Collectors.groupingBy(
                                                    Function.identity(),     
                                                    Collectors.counting()))
                                                        .entrySet().stream()
                                                        .filter(e -> e.getValue() > 1L)
                                                        .map(e -> e.getKey())
                                                        .collect(Collectors.toList());

        doubleMap.values().removeIf(value -> !duplicateList.contains(value));
        return doubleMap;
    }

    public void populateTemp() {
        cellList.forEach(cell -> cell.populateTemp(cellList.size()));
    }

    public void populateTemp(int maxNumber) {
        cellList.forEach(cell -> cell.populateTemp(maxNumber));
    }

    public void calculateSetTemp() {
        cellList.forEach(cell -> cell.removeTemp(valueList));
    }

    public void removeTempFromList(ArrayList<Integer> removeList) {
        cellList.forEach(cell -> cell.removeTemp(removeList));
    }
}
