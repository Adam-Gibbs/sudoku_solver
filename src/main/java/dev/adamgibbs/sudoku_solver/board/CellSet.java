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
public abstract class CellSet {
    protected ArrayList<Cell> cellList = new ArrayList<>();
    protected ArrayList<Integer> valueList = new ArrayList<>();

    public void setValueList() {
        for (Cell cell : cellList) {
            if (cell.hasValue()) {
                valueList.add(cell.getValue());
            }
        }      
    }

    public void addCell(Cell newCell) {
        cellList.add(newCell);  
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

    public void resetChanges() {
        cellList.forEach(cell -> cell.resetChange());
    }

    public Boolean checkChange() {
        for (Cell cell : cellList) {
            if (cell.getChange()) {
                return true;
            }
        }

        return false;
    }

    public Boolean checkComplete() {
        for (Cell cell : cellList) {
            if (!cell.hasValue()) {
                return false;
            }
        }

        return true;
    }

    public void reloadValues() {
        valueList.clear();
        setValueList();
    }
    private Boolean checkAllPossibleNumsInSet() {
        ArrayList<Integer> valueList = new ArrayList<>();

        for (Cell cell : cellList) {
            if (cell.hasValue()) {
                valueList.add(cell.getValue());
            } else {
                valueList.addAll(cell.getTempValues());
            }
        }

        return valueList.containsAll(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
    }

    private Boolean checkMultipleDoubles() {
        ArrayList<List<Integer>> doublesValueList = new ArrayList<>();
        for (List<Integer> valueList : this.getDoubles().values()) {
            doublesValueList.add(valueList);
        }

        Map<List<Integer>, Long> frequencyMap =
                    doublesValueList.stream().collect(Collectors.groupingBy(Function.identity(),
                                                                            Collectors.counting()));
 
        for (Map.Entry<List<Integer>, Long> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > 2) {
                return false;
            }
        }

        return true;
    }

    public Boolean checkSolvable() {
        return (checkMultipleDoubles() && checkAllPossibleNumsInSet());
    }

    @Override
    public abstract String toString();
}
