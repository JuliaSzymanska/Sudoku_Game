package org.grupa5.sudoku;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for simple App. Testing methods: resetBoard and getters.
 */
public class SudokuBoardTest {

    @Test
    void resetBoardTest() {
        List<List<SudokuField>> board;
        SudokuBoard sudoku = new SudokuBoard();
        sudoku.solveGame();
        board = sudoku.getBoard();
        sudoku.resetBoard(board);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertNotEquals(sudoku.getBoard().get(i).get(j).getFieldValue(), 0);
            }
        }
    }

    @Test
    void getRowTest() {
        List<List<SudokuField>> board;
        SudokuBoard sudoku = new SudokuBoard();
        sudoku.solveGame();
        board = sudoku.getBoard();
        SudokuObject row = new SudokuObject(board.get(0));
        SudokuObject row2 = sudoku.getRow(0);
         assertEquals(row, row2);
    }

    @Test
    void getColumnTest() {
        List<List<SudokuField>> board;
        SudokuBoard sudoku = new SudokuBoard();
        sudoku.solveGame();
        board = sudoku.getBoard();
        List<SudokuField> col = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            col.set(i, board.get(i).get(0));
        }
        SudokuObject column = new SudokuObject(col);
        SudokuObject column2 = sudoku.getColumn(0);
        assertEquals(column, column2);
    }

    @Test
    void getBoxTest() {
        List<List<SudokuField>> board;
        SudokuBoard sudoku = new SudokuBoard();
        sudoku.solveGame();
        board = sudoku.getBoard();
        List<SudokuField> bo = Arrays.asList(new SudokuField[9]);
        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                bo.set(k, board.get(i).get(j));
                k++;
            }
        }
        SudokuObject box = new SudokuObject(bo);
        SudokuObject box2 = sudoku.getBox(0, 0);
        assertEquals(box, box2);
    }

    @Test
    void getterAndSetterTest() {
        SudokuBoard sudoku = new SudokuBoard();
        assertEquals(sudoku.get(2, 2), 0);
        sudoku.set(2, 2, 2);
        assertEquals(sudoku.get(2, 2), 2);
        assertThrows(IndexOutOfBoundsException.class, () -> {
            sudoku.get(9, 9);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            sudoku.set(9, 9, 1337);
        });
    }

//    @Test
//    void getInfoSudokuTest() {
//        ArrayList<ArrayList<SudokuField>> board;
//        SudokuBoard sudoku = new SudokuBoard();
//        SudokuSolver Wypelniacz = new BacktrackingSudokuSolver();
//        Wypelniacz.solve(sudoku);
//        board = sudoku.getBoard();

//        StringBuilder output = new StringBuilder("X ");
//        for (int i = 0; i <= 8; i++) {
//            output.append((char) ('a' + i)).append(" ");
//        }
//        output.append("\n");
//        int counter = 0;
//        for (ArrayList<SudokuField> x : board) {
//            output.append((char) ('a' + counter)).append(" ");
//            for (SudokuField y : x) {
//                output.append(y.getFieldValue()).append(" ");
//            }
//            output.append("\n");
//            counter++;
//        }
//        assertEquals(sudoku.getInfoSudoku(), output.toString());
//    }

}
