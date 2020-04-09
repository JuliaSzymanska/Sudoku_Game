package org.grupa5.sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SudokuFieldTest {

    // TODO: Zrob test do toString()

    @Test
    void setTest() {
        SudokuField field = new SudokuField();
        assertThrows(IllegalArgumentException.class, () -> {
            field.setFieldValue(-1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            field.setFieldValue(10);
        });
    }

    @Test
    void equalsTest() {
        SudokuField sudoku1 = new SudokuField();
        sudoku1.setFieldValue(3);
        SudokuField sudoku2 = new SudokuField();
        assertNotEquals(sudoku1, null);
        assertEquals(sudoku1, sudoku1);
        assertNotEquals(sudoku1, Class.class);
        assertNotEquals(sudoku1, sudoku2);
        sudoku2.setFieldValue(3);
        assertEquals(sudoku1, sudoku2);
    }
}


