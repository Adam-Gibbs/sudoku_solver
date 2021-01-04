package dev.adamgibbs.sudoku_solver.solverLogic;

import java.util.Map;
import java.util.Stack;

import dev.adamgibbs.sudoku_solver.board.Board;
import dev.adamgibbs.sudoku_solver.board.Cell;
import dev.adamgibbs.sudoku_solver.board.saves.SaveManager;

public class SolverLogic {
	private Stack<SolvedStats> statsList = new Stack<>();
    
    public Board solveBoard(Board inputBoard) {
        inputBoard.createTemps();

		SaveManager saves = new SaveManager();
		int loops = 0;

		while (!inputBoard.isComplete()) {
			while (inputBoard.isSolvable()) {
				if (inputBoard.isComplete()) {
					break;
				}
				loops++;
				inputBoard.resetAllChanges();
				inputBoard.recheckTemp();
				inputBoard.setOnlyTempValues();
				if (inputBoard.hasChange()) {
					continue;
				}
				inputBoard.setTempValuesInSet();
				if (inputBoard.hasChange()) {
					continue;
				}
				inputBoard.removeSimilarTempsIfDuplicates();
				if (inputBoard.hasChange()) {
					continue;
				}
				Map<Cell, Integer> temporaryChange = inputBoard.generateRandomChange();
				saves.saveCurrent(inputBoard);
				try {
					inputBoard.implementChange(temporaryChange);
				} catch (IllegalStateException e) {
					System.out.println("Cannot implement last change because: " + e);
					break;
				}
			}

			if (inputBoard.isComplete()) {
				break;
			}
	
			try {
				saves.loadLastSave(inputBoard);
			} catch (IllegalStateException e) {
				System.out.println("This cannot be solved");
				break;
			}
		}
		
		statsList.push(new SolvedStats(loops, inputBoard, inputBoard.isComplete()));
		return inputBoard;
	}

	public SolvedStats getLastSolvedStats() {
		return statsList.peek();
	}

	public Board getLastBoard() {
		return statsList.peek().getBoard();
	}
	
	public Integer getLastDifficulty() {
		return statsList.peek().getDifficulty();
	}
}
