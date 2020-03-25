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
        for (int i = 0; i < 9; i++) {
            list1.set(i, new SudokuField(i + 1));
            list2.set(i, new SudokuField(i + 1));
        }
        SudokuObject sudoku1 = new SudokuObject(list1);
        SudokuObject sudoku2 = new SudokuObject(list2);
        assertNotEquals(sudoku1, null);
        assertEquals(sudoku1, sudoku1);
        assertNotEquals(sudoku1, Class.class);
        assertEquals(sudoku1, sudoku2);
        list2.set(4, new SudokuField(9));
        sudoku2 = new SudokuObject(list2);
        assertNotEquals(sudoku1, sudoku2);
    }

    @Test
    void verifyTest() {
        List<SudokuField> list1 = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            list1.set(i, new SudokuField(i + 1));
        }
        SudokuObject sudoku1 = new SudokuObject(list1);
        assertTrue(sudoku1.verify());
        list1.set(4, new SudokuField(9));
        sudoku1 = new SudokuObject(list1);
        assertFalse(sudoku1.verify());
    }
}