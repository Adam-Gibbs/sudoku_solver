package dev.adamgibbs.sudoku_solver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dev.adamgibbs.sudoku_solver.board.Board;
import dev.adamgibbs.sudoku_solver.board.BoardMaker;
import dev.adamgibbs.sudoku_solver.board.Cell;
import dev.adamgibbs.sudoku_solver.board.sets.Row;
import dev.adamgibbs.sudoku_solver.solverLogic.SolverLogic;

class SudokuSolverApplicationTests {

	@Test
	void displayingSolvedBoard() {
		SolverLogic solver = new SolverLogic();
		Board sudokuBoard = new BoardMaker()
						.addCellValuesFromString("000000680000073009309000045490000000803050902000000036960000308700680000028000000")
						.build();

		solver.solveBoard(sudokuBoard);
		String solvedBoard = 
		"-------------------------------------\n" +
		"|111|777|222|555|444|999|666|888|333|\n" +
		"|111|777|222|555|444|999|666|888|333|\n" +
		"|111|777|222|555|444|999|666|888|333|\n" +
		"-------------------------------------\n" +
		"|666|444|555|888|777|333|222|111|999|\n" +
		"|666|444|555|888|777|333|222|111|999|\n" +
		"|666|444|555|888|777|333|222|111|999|\n" +
		"-------------------------------------\n" +
		"|333|888|999|222|666|111|777|444|555|\n" +
		"|333|888|999|222|666|111|777|444|555|\n" +
		"|333|888|999|222|666|111|777|444|555|\n" +
		"-------------------------------------\n" +
		"|444|999|666|333|222|777|888|555|111|\n" +
		"|444|999|666|333|222|777|888|555|111|\n" +
		"|444|999|666|333|222|777|888|555|111|\n" +
		"-------------------------------------\n" +
		"|888|111|333|444|555|666|999|777|222|\n" +
		"|888|111|333|444|555|666|999|777|222|\n" +
		"|888|111|333|444|555|666|999|777|222|\n" +
		"-------------------------------------\n" +
		"|222|555|777|111|999|888|444|333|666|\n" +
		"|222|555|777|111|999|888|444|333|666|\n" +
		"|222|555|777|111|999|888|444|333|666|\n" +
		"-------------------------------------\n" +
		"|999|666|444|777|111|555|333|222|888|\n" +
		"|999|666|444|777|111|555|333|222|888|\n" +
		"|999|666|444|777|111|555|333|222|888|\n" +
		"-------------------------------------\n" +
		"|777|333|111|666|888|222|555|999|444|\n" +
		"|777|333|111|666|888|222|555|999|444|\n" +
		"|777|333|111|666|888|222|555|999|444|\n" +
		"-------------------------------------\n" +
		"|555|222|888|999|333|444|111|666|777|\n" +
		"|555|222|888|999|333|444|111|666|777|\n" +
		"|555|222|888|999|333|444|111|666|777|\n" +
		"-------------------------------------";

		assertEquals(solvedBoard, solver.getLastBoard().toString());
	}

	private Boolean compareTwoBoards(Board board1, Board board2) {
		ArrayList<Row> board1RowList = board1.getRowList();
		ArrayList<Row> board2RowList = board2.getRowList();
		for (int i = 0; i < board1RowList.size(); i++) {
			ArrayList<Cell> board1SolvedRow = board1RowList.get(i).getCellList();
			ArrayList<Cell> board2ExpectedRow = board2RowList.get(i).getCellList();
			for (int j = 0; j < board1SolvedRow.size(); j++) {
				if(board2ExpectedRow.get(i).getValue() != board1SolvedRow.get(i).getValue()) {
					return false;
				}
			}
		}

		return true;
	}

	@Test
	void solvingFullBoard() {
		SolverLogic solver = new SolverLogic();
		Board sudokuBoard = new BoardMaker()
						.addCellValuesFromString("000000680000073009309000045490000000803050902000000036960000308700680000028000000")
						.build();
		Board expectedBoard = new BoardMaker()
						.addCellValuesFromString("172549683645873219389261745496327851813456972257198436964715328731682594528934167")
						.build();

		solver.solveBoard(sudokuBoard);
		assertTrue(compareTwoBoards(expectedBoard, sudokuBoard));
	}

	@Test
	void difficultyReturningAsExpected() {
		SolverLogic solver = new SolverLogic();
		Board sudokuBoard = new BoardMaker()
						.addCellValuesFromString("000000680000073009309000045490000000803050902000000036960000308700680000028000000")
						.build();

		solver.solveBoard(sudokuBoard);
		assertEquals(Integer.valueOf(17), solver.getLastDifficulty());
	}

	@Test
	void multipleDifferentBoards() {
		SolverLogic solver = new SolverLogic();
		Board sudokuBoard1 = new BoardMaker()
						.addCellValuesFromString("509400000000000000000010600000001030030005002092000045005000000000906020700003806")
						.build();
		Board expectedBoard1 = new BoardMaker()
						.addCellValuesFromString("519467283276389451843512679657241938438795162192638745965824317381976524724153896")
						.build();

		solver.solveBoard(sudokuBoard1);
		assertTrue(sudokuBoard1.isComplete());
		assertTrue(compareTwoBoards(expectedBoard1, sudokuBoard1));

		Board sudokuBoard2 = new BoardMaker()
						.addCellValuesFromString("000076200000000900029000310000009700000800052307000000040002000700003000008510000")
						.build();
		Board expectedBoard2 = new BoardMaker()
						.addCellValuesFromString("183976245475321986629458317852149763964837152317265498546792831791683524238514679")
						.build();

		solver.solveBoard(sudokuBoard2);
		assertTrue(sudokuBoard2.isComplete());
		assertTrue(compareTwoBoards(expectedBoard2, sudokuBoard2));

		Board sudokuBoard3 = new BoardMaker()
						.addCellValuesFromString("004060001010200080200030000098000276007000000000010000000040000600000350000803090")
						.build();
		Board expectedBoard3 = new BoardMaker()
						.addCellValuesFromString("374968521916275483285431967198354276427689135563712849839546712642197358751823694")
						.build();

		solver.solveBoard(sudokuBoard3);
		assertTrue(sudokuBoard3.isComplete());
		assertTrue(compareTwoBoards(expectedBoard3, sudokuBoard3));

		Board sudokuBoard4 = new BoardMaker()
						.addCellValuesFromString("504108000000000062300600000002360000000089405400000000007000000000007809000200006")
						.build();
		Board expectedBoard4 = new BoardMaker()
						.addCellValuesFromString("564128937781943562329675148952364781613789425478512693197836254236457819845291376")
						.build();

		solver.solveBoard(sudokuBoard4);
		assertTrue(sudokuBoard4.isComplete());
		assertTrue(compareTwoBoards(expectedBoard4, sudokuBoard4));

		Board sudokuBoard5 = new BoardMaker()
						.addCellValuesFromString("890006030070109000604370001209400003710203048500001902100035704000608050020700086")
						.build();
		Board expectedBoard5 = new BoardMaker()
						.addCellValuesFromString("891546237372189465654372891289457613716293548543861972168935724437628159925714386")
						.build();

		solver.solveBoard(sudokuBoard5);
		assertTrue(sudokuBoard5.isComplete());
		assertTrue(compareTwoBoards(expectedBoard5, sudokuBoard5));
	}

	@Test
	void checkImpossibleBoards() {
		SolverLogic solver = new SolverLogic();
		Board sudokuBoard1 = new BoardMaker()
						.addCellValuesFromString("529400000000000000000010600000001030030005002092000045005000000000906020700003806")
						.build();

		solver.solveBoard(sudokuBoard1);
		assertFalse(sudokuBoard1.isComplete());

		Board sudokuBoard2 = new BoardMaker()
						.addCellValuesFromString("300076200000000900029000310000009700000800052307000000040002000700003000008510000")
						.build();

		solver.solveBoard(sudokuBoard2);
		assertFalse(sudokuBoard2.isComplete());

		Board sudokuBoard3 = new BoardMaker()
						.addCellValuesFromString("004060001010200080200030000098000276007000000000010900000040000600000350000803090")
						.build();

		solver.solveBoard(sudokuBoard3);
		assertFalse(sudokuBoard3.isComplete());
	}
}
