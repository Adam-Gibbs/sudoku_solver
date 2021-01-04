package dev.adamgibbs.sudoku_solver.solverLogic;

import dev.adamgibbs.sudoku_solver.board.Board;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SolvedStats {
    private Integer difficulty;
    private Board board;
    private Boolean isSolved;
}
