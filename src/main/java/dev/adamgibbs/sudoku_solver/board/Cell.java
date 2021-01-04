package dev.adamgibbs.sudoku_solver.board;

import java.util.ArrayList;
import java.util.Collections;

import dev.adamgibbs.sudoku_solver.board.saves.SavedCell;
import lombok.Data;

@Data
public class Cell {
    private Integer value;
    private Integer position;
    private ArrayList<Integer> tempValues = new ArrayList<>();
    private Integer stringPosition = 1;
    private Boolean change = false;

    public void setValue(Integer newValue) throws IllegalStateException {
        if (value != null) {
            throw new IllegalStateException("Cell already has a value");
        }

        change = true;
        value = newValue;
        tempValues = new ArrayList<>();
    }

    public void setValueFromList(ArrayList<Integer> valueList) throws IllegalStateException {
        for (Integer value : valueList) {
            if (tempValues.contains(value)) {
                setValue(value);
            }
        }
    }

    public void addTempValue(Integer newTempValue) throws IllegalStateException {
        if (value != null) {
            throw new IllegalStateException("Cell already has a value");
        }

        tempValues.add(newTempValue);
        Collections.sort(tempValues);
    }

    public Boolean hasValue() {
        if (value == null) {
            return false;
        } else {
            return true;
        }
    }
    
    public Integer tempValueCount() {
        return tempValues.size();
    }

    public void setOnlyValue() throws IllegalStateException {
        if (tempValues.size() != 1) {
            throw new IllegalStateException("Cell must have only 1 temporary value");
        }

        setValue(tempValues.get(0));
    }

    public void populateTemp(int maxNumber) {
        maxNumber++;
        if (!hasValue() && tempValues.size() == 0) {
            for (Integer i = 1; i < maxNumber; i++) {
                tempValues.add(i);
            }
        }
    }

    public void removeTemp(Integer currentValue) {
        if (!hasValue()) {
            if (tempValues.contains(currentValue)) {
                tempValues.remove(currentValue);
                change = true;
            }
        }
    }

    public void removeTemp(ArrayList<Integer> currentValues) {
        if (!hasValue()) {
            for (Integer value : currentValues) {
                if (tempValues.contains(value)) {
                    tempValues.remove(value);
                    change = true;
                }
            }
        }
    }

    public SavedCell save() {
        return new SavedCell(value, position, new ArrayList<>(tempValues));
    }

    public void load(SavedCell save) {
        value = save.getValue();
        position = save.getPosition();
        tempValues = save.getTempValues();
        change = false;
    }

    public void resetChange() {
        change = false;
    }

    public String lineToString() {
        if (hasValue()) {
            return value.toString() + value.toString() + value.toString();
        } else {
            String newString = "";
            for (Integer i = stringPosition; i < (stringPosition+3); i++) {
                if (tempValues.contains(i)){
                    newString += i.toString();
                } else {
                    newString += " ";
                }
            }

            if (stringPosition == 7) {
                stringPosition = 1;
            } else {
                stringPosition += 3;
            }

            return newString;
        }
    }
}