package org.grupa5.sudoku;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SudokuObjectTest {

    @Test
    void equalsTest() {
        List<SudokuField> list1 = Arrays.asList(new SudokuField[9]);
        List<SudokuField> list2 = Arrays.asList(new SudokuField[9]);
        List<SudokuField> list3 = Arrays.asList(new SudokuField[9]);
        List<List<SudokuField>> board;
        SudokuBoard sudoku = new SudokuBoard();
        sudoku.solveGame();
        board = sudoku.getBoard();
        for (int i = 0; i < 9; i++) {
                list1.set(i, board.get(i).get(0));
                list2.set(i, board.get(i).get(0));
                list3.set(i, board.get(i).get(0));
        }
        list3.set(4, board.get(4).get(4));
        SudokuObject sudoku1 = new SudokuObject(list1);
        SudokuObject sudoku2 = new SudokuObject(list2);
        SudokuObject sudoku3 = new SudokuObject(list3);
        assertNotEquals(sudoku1, null);
        assertEquals(sudoku1, sudoku1);
        assertNotEquals(sudoku1, 1);
        assertEquals(sudoku1, sudoku2);
        list2.set(4, board.get(4).get(4));
        assertNotEquals(sudoku1, sudoku3);

    }
}