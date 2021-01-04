package dev.adamgibbs.sudoku_solver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.adamgibbs.sudoku_solver.board.Board;
import dev.adamgibbs.sudoku_solver.board.BoardMaker;
import dev.adamgibbs.sudoku_solver.board.Cell;
import dev.adamgibbs.sudoku_solver.board.saves.SaveManager;

public class SudokuSolverApplication {

	public static void main(String[] args) {
		ArrayList<Integer> cellValues = new ArrayList<>(
			List.of(0, 0, 0, 0, 0, 0, 6, 8, 0, 0, 0, 0, 0, 7, 3, 0, 0, 9, 3, 0, 9, 0, 0, 0, 0, 4, 5,
					4, 9, 0, 0, 0, 0, 0, 0, 0, 8, 0, 3, 0, 5, 0, 9, 0, 2, 0, 0, 0, 0, 0, 0, 0, 3, 6,
					9, 6, 0, 0, 0, 0, 3, 0, 8, 7, 0, 0, 6, 8, 0, 0, 0, 0, 0, 2, 8, 0, 0, 0, 0, 0, 0)
		);
		Board myBoard = new BoardMaker()
							.addCellValues(cellValues)
							.build();

		myBoard.createTemps();
		System.out.println("Input:\n" + myBoard + "\n\n Output:");
		SaveManager saves = new SaveManager();

		while (!myBoard.isComplete()) {
			while (myBoard.isSolvable()) {
				if (myBoard.isComplete()) {
					break;
				}
				myBoard.resetAllChanges();
				myBoard.recheckTemp();
				myBoard.setOnlyTempValues();
				if (myBoard.hasChange()) {
					continue;
				}
				myBoard.setTempValuesInSet();
				if (myBoard.hasChange()) {
					continue;
				}
				myBoard.removeSimilarTempsIfDuplicates();
				if (myBoard.hasChange()) {
					continue;
				}
				Map<Cell, Integer> temporaryChange = myBoard.generateRandomChange();
				saves.saveCurrent(myBoard);
				try {
					myBoard.implementChange(temporaryChange);
				} catch (IllegalStateException e) {
					System.out.println("Cannot implement last change because: " + e);
					break;
				}
			}

			if (myBoard.isComplete()) {
				break;
			}
	
			try {
				saves.loadLastSave(myBoard);
			} catch (IllegalStateException e) {
				System.out.println("This cannot be solved");
				break;
			}
		}
		
		System.out.println(myBoard);
	}

}
