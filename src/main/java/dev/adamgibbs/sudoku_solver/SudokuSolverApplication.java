package dev.adamgibbs.sudoku_solver;

import java.util.Scanner;

import dev.adamgibbs.sudoku_solver.board.Board;
import dev.adamgibbs.sudoku_solver.board.BoardMaker;
import dev.adamgibbs.sudoku_solver.solverLogic.SolverLogic;

public class SudokuSolverApplication {

	public static void main(String[] args) {
		SolverLogic solver = new SolverLogic();
		Boolean isContinue = true;

		Scanner scanner = new Scanner(System.in);
		while (isContinue) {
			play(solver, getSudokuInput(scanner));
		}
		scanner.close();
	}

	private static String getSudokuInput(Scanner scanner) {
		System.out.println("Enter sudoku:");
		return scanner.nextLine();
	}

	private static void play(SolverLogic solver, String sudokuInput) {
		Board sudokuBoard = new BoardMaker()
								.addCellValuesFromString(sudokuInput)
								.build();

		System.out.println("Input:\n" + sudokuBoard);
		solver.solveBoard(sudokuBoard);
		System.out.println("\n\n Output: " + solver.getLastBoard());
		System.out.println("Difficulty: " + solver.getLastDifficulty());
	}

}
