package org.grupa5.sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SudokuFieldTest {

    @Test
    void setTest() {
        SudokuField field = new SudokuField();
        assertThrows(IllegalArgumentException.class, () -> {
            field.setFieldValue(-1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            field.setFieldValue(10);
        });
        SudokuField field1 = new SudokuField(field);
        assertEquals(field.getFieldValue(), field1.getFieldValue());
    }

    @Test
    void equalsAndHashCodeTest() {
        SudokuField sudoku1 = new SudokuField(3);
        SudokuField sudoku2 = new SudokuField(4);

        assertNotEquals(sudoku1, null);
        assertEquals(sudoku1, sudoku1);
        assertNotEquals(sudoku1, Class.class);
        assertNotEquals(sudoku1, sudoku2);

        sudoku2.setFieldValue(3);
        assertTrue(sudoku1.equals(sudoku2) && sudoku2.equals(sudoku1));
        assertEquals(sudoku1.hashCode(), sudoku2.hashCode());
    }

    @Test
    public void testToString() {
        String expectedOut = "1234567890";
        SudokuField field = new SudokuField(1234567890);
        assertEquals(expectedOut, field.toString());
    }
}