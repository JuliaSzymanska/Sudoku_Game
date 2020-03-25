package org.grupa5.sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit test for simple App. Testing SudokuSolver interface implementations
 * Checking if SudokuSolver generates a valid Sudoku board and whether two
 * generated boards are different from eachother.
 */
public class SudokuSolverTest {

    private boolean checkBoard(int row, int column, SudokuBoard board) {
        if (board == null) {
            throw new NullPointerException("SudokuBoard can't be null");
        }
        if (row < 0 || row > 8) {
            throw new IndexOutOfBoundsException("Row has to be in range 0 - 8");
        }
        if (column < 0 || column > 8) {
            throw new IndexOutOfBoundsException("Column has to be in range 0 - 8");
        }
        return board.getBox(row, column).verify() && 
        board.getRow(row).verify() && board.getColumn(column).verify();
    }

    @Test
    void solveSudokuTest() {
        SudokuBoard Plansza = new SudokuBoard();
        Plansza.solveGame();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertTrue(checkBoard(i, j, Plansza) && Plansza.get(i, j) != 0);
            }
        }
    }

    @Test
    void noRepeatsTest() {
        SudokuBoard sudoku = new SudokuBoard();
        SudokuBoard sudoku2 = new SudokuBoard();
        sudoku.solveGame();
        sudoku2.solveGame();
        assertNotEquals(sudoku, sudoku2);

    }

    @Test
    void randomFillSectorTest() {
        SudokuBoard sudoku = new SudokuBoard();
        sudoku.solveGame();
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        solver.solve(sudoku);
    }

}
