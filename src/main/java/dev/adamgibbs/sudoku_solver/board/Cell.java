package dev.adamgibbs.sudoku_solver.board;

import java.util.ArrayList;
import java.util.Collections;

import lombok.Data;

@Data
public class Cell {
    private Integer value;
    private ArrayList<Integer> tempValues = new ArrayList<>();
    private Integer stringPosition = 1;

    public void setValue(Integer newValue) throws IllegalStateException {
        if (value != null) {
            throw new IllegalStateException("Cell already has a value");
        }

        value = newValue;
        tempValues.clear();
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
        if (value != null) {
            for (Integer i = 1; i < maxNumber; i++) {
                tempValues.add(i);
            }
        }
    }

    public void removeTemp(Integer currentValue) {
        if (tempValues.contains(currentValue)) {
            tempValues.remove(currentValue);
        }
    }

    public void removeTemp(ArrayList<Integer> currentValues) {
        for (Integer value : currentValues) {
            if (tempValues.contains(value)) {
                tempValues.remove(value);
            }
        }
    }

    private String nextLine() {
        if (hasValue()) {
            return value.toString() + value.toString() + value.toString();
        } else {
            String newString = "";
            for (Integer i = stringPosition; i == stringPosition*3; i++) {
                newString += i.toString();
            }

            if (stringPosition > 3) {
                stringPosition = 1;
            } else {
                stringPosition++;
            }

            return newString;
        }
    }

    public String lineToString() {
        return nextLine() + "\n";
    }
}