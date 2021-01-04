package dev.adamgibbs.sudoku_solver;

import java.util.ArrayList;
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

	private static ArrayList<Integer> getSudokuInput(Scanner scanner) {
		ArrayList<Integer> cellValues = new ArrayList<>();

		System.out.println("Enter sudoku:");
		String input = scanner.nextLine();

		for(char value : input.toCharArray()) {
			cellValues.add(Character.getNumericValue(value));
		}

		return cellValues;
	}

	private static void play(SolverLogic solver, ArrayList<Integer> sudokuInput) {
		Board sudokuBoard = new BoardMaker()
								.addCellValues(sudokuInput)
								.build();

		System.out.println("Input:\n" + sudokuBoard);
		solver.solveBoard(sudokuBoard);
		System.out.println("\n\n Output:" + solver.getLastBoard());
		System.out.println("Difficulty: " + solver.getLastDifficulty());
	}

}
