package org.grupa5.sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import java.util.List;

/**
 * Unit test for simple App. Testing SudokuSolver interface implementations
 * Checking if SudokuSolver generates a valid Sudoku board and whether two
 * generated boards are different from eachother.
 */
public class SudokuSolverTest {

    private boolean checkCol(int col, SudokuBoard board) {
        if (col < 0 || col > 8) {
            throw new IndexOutOfBoundsException("Column has to be in range 0 - 8");
        }
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i <= 8; i++) {
            if (list.contains(board.get(i, col))) {
                return false;
            }
            list.add(board.get(i, col));
        }
        return true;
    }

    private boolean checkRow(int row, SudokuBoard board) {
        if (row < 0 || row > 8) {
            throw new IndexOutOfBoundsException("Row has to be in range 0 - 8");
        }
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i <= 8; i++) {
            if (list.contains(board.get(row, i))) {
                return false;
            }
            list.add(board.get(row, i));
        }
        return true;
    }

    private boolean checkSector(int sectorNr, SudokuBoard board) {
        if (sectorNr < 0 || sectorNr > 8) {
            throw new IndexOutOfBoundsException("Sector sectorNr has to be in range from 0 to 8");
        }
        int begX = (sectorNr / 3) * 3;
        int begY = (sectorNr % 3) * 3;
        List<Integer> list = new ArrayList<Integer>();
        for (int i = begX; i <= begX + 2; i++) {
            for (int j = begY; j <= begY + 2; j++) {
                if (list.contains(board.get(i, j))) {
                    return false;
                }
                list.add(board.get(i, j));
                // System.out.println(list);
            }
        }
        return true;
    }

    private int getSectorNumber(int row, int col) {
        int sectorNr = (row / 3) * 3;
        sectorNr += col / 3;
        return sectorNr;
    }

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
        return this.checkCol(column, board) && this.checkRow(row, board)
                && this.checkSector(getSectorNumber(row, column), board);
    }

    @Test
    void solveSudokuTest() {
        SudokuBoard Plansza = new SudokuBoard();
        SudokuSolver Wypelniacz = new BacktrackingSudokuSolver();
        Wypelniacz.solve(Plansza);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertTrue(checkBoard(i, j, Plansza));
            }
        }
    }

    @Test
    void noRepeatsTest() {
        SudokuBoard sudoku = new SudokuBoard();
        SudokuBoard sudoku2 = new SudokuBoard();
        SudokuSolver Wypelniacz = new BacktrackingSudokuSolver();
        Wypelniacz.solve(sudoku);
        Wypelniacz.solve(sudoku2);
        assertNotEquals(sudoku, sudoku2);

    }

}
