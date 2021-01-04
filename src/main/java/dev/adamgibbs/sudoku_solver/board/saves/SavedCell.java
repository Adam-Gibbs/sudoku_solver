package dev.adamgibbs.sudoku_solver.board.saves;

import lombok.Data;

import java.util.ArrayList;

import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class SavedCell {
    private Integer value;
    private Integer position;
    private ArrayList<Integer> tempValues; 
}
