package dev.adamgibbs.sudoku_solver;

import java.util.ArrayList;
import java.util.List;

import dev.adamgibbs.sudoku_solver.board.Board;
import dev.adamgibbs.sudoku_solver.board.BoardMaker;
import dev.adamgibbs.sudoku_solver.solverLogic.SolverLogic;


public class SudokuSolverApplication {

	public static void main(String[] args) {
		SolverLogic solver = new SolverLogic();
		ArrayList<Integer> cellValues = new ArrayList<>(
			List.of(0, 0, 0, 0, 0, 0, 6, 8, 0, 0, 0, 0, 0, 7, 3, 0, 0, 9, 3, 0, 9, 0, 0, 0, 0, 4, 5,
					4, 9, 0, 0, 0, 0, 0, 0, 0, 8, 0, 3, 0, 5, 0, 9, 0, 2, 0, 0, 0, 0, 0, 0, 0, 3, 6,
					9, 6, 0, 0, 0, 0, 3, 0, 8, 7, 0, 0, 6, 8, 0, 0, 0, 0, 0, 2, 8, 0, 0, 0, 0, 0, 0)
		);
		Board sudokuBoard = new BoardMaker()
							.addCellValues(cellValues)
							.build();

		System.out.println("Input:\n" + sudokuBoard + "\n\n Output:");
		System.out.println(solver.solveBoard(sudokuBoard));
	}

}
