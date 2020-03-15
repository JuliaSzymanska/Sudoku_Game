package org.grupa5.sudoku;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for simple App.
 */
public class SudokuBoardTest {

    @Test
    void fillBoardTest() {
        int[][] board;
        SudokuBoard sudoku = new SudokuBoard();
        sudoku.fillBoard();
        board = sudoku.getBoard();
        boolean check = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                for (int k = 0; k < 9; k++) {
                    if ((k != i && board[i][j] == board[k][j]) || (k != j && board[i][j] == board[i][k])) {
                        check = false;
                        break;
                    }
                }
                int begX = (i / 3) * 3, begY = (j / 3) * 3;
                for (int x = begX; x <= begX + 2; x++) {
                    for (int y = begY; y <= begY + 2; y++) {
                        if (board[i][j] == board[x][y] && (x != i && y != j)) {
                            check = false;
                            break;
//                        }
                        }
                    }
                }
            }
            assertTrue(check);
        }
    }

    @Test
    void chekcEqualityTest() {
        int[][] board1, board2;
        SudokuBoard sudoku = new SudokuBoard();
        sudoku.fillBoard();
        board1 = sudoku.getBoard();
        sudoku.resetBoard();
        sudoku.fillBoard();
        board2 = sudoku.getBoard();
        assertNotEquals(board1, board2);
    }

    @Test
    void resetBoardTest() {
        int[][] board;
        SudokuBoard sudoku = new SudokuBoard();
        sudoku.fillBoard();
        sudoku.resetBoard();
        board = sudoku.getBoard();
        boolean check = false;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != 0) {
                    check = true;
                }
            }
        }
        assertFalse(check);
    }

    @Test
    void getInfoSudokuTest() {
        int[][] board;
        SudokuBoard sudoku = new SudokuBoard();
        sudoku.fillBoard();
        board = sudoku.getBoard();
        String boardPrint = sudoku.getInfoSudoku();

        String output = "X ";
        for (int i = 0; i <= 8; i++) {
            output += (char) ('a' + i) + " ";
        }
        output += "\n";
        int counter = 0;
        for (int[] x : board) {
            output += (char) ('a' + counter) + " ";
            for (int y : x) {
                output += y + " ";
            }
            output += "\n";
            counter++;
        }

        assertEquals(sudoku.getInfoSudoku(), boardPrint );
    }

}
