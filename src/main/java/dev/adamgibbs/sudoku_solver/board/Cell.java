package dev.adamgibbs.sudoku_solver.board;

import java.util.ArrayList;
import java.util.Collections;

import lombok.Data;

@Data
public class Cell {
    private Integer value;
    private ArrayList<Integer> tempValues = new ArrayList<>();

    public void setValue(Integer newValue) throws IllegalStateException {
        if (value != null) {
            throw new IllegalStateException("Cell already has a value");
        }

        value = newValue;
        tempValues.clear();
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
}
