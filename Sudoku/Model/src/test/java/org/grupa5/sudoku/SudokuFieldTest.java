package org.grupa5.sudoku;

import org.grupa5.exceptions.SetException;
import org.grupa5.exceptions.GetException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SudokuFieldTest {

    @Test
    void setTest() {
        SudokuField field = new SudokuField();
        assertThrows(SetException.class, () -> {
            field.setValue(-1);
        });
        assertThrows(SetException.class, () -> {
            field.setValue(10);
        });
        SudokuField field1 = new SudokuField(field);
        assertEquals(field.getValue(), field1.getValue());
    }

    @Test
    void equalsCodeTest() throws SetException {
        SudokuField sudoku1 = new SudokuField(3);
        SudokuField sudoku2 = new SudokuField(4);

        assertNotEquals(sudoku1, null);
        assertEquals(sudoku1, sudoku1);
        assertNotEquals(sudoku1, Class.class);
        assertNotEquals(sudoku1, sudoku2);
        sudoku2.setValue(3);
        assertTrue(sudoku1.equals(sudoku2) && sudoku2.equals(sudoku1));
    }

    @Test
    void HashCodeTest() {
        SudokuField sudoku1 = new SudokuField(3);
        SudokuField sudoku2 = new SudokuField(4);
        if(sudoku1.hashCode() != sudoku2.hashCode())
            assertNotEquals(sudoku1, sudoku2);
    }

    @Test
    public void testToString() {
        String expectedOut = "1234567890";
        SudokuField field = new SudokuField(1234567890);
        assertEquals(expectedOut, field.toString());
    }

    @Test
    public void cloneTest(){
        SudokuField field1 = new SudokuField(1);
        SudokuField field2 = new SudokuField();
        try {
            field2 = field1.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertEquals(field1, field2);
        assertEquals(field1.hashCode(), field2.hashCode());
        assertNotSame(field1, field2);
    }

    @Test
    public void compareToTest() throws SetException {
        SudokuField field1 = new SudokuField(1);
        assertThrows(NullPointerException.class, () -> {
            field1.compareTo(null);
        });
        SudokuField field2 = new SudokuField(1);
        assertEquals(field1, field2);
        assertEquals(field1.compareTo(field2), 0);
        field2.setValue(3);
        assertTrue(field1.getValue() < field2.getValue());
        assertEquals(field1.compareTo(field2), -1);
        field1.setValue(6);
        assertTrue(field1.getValue() > field2.getValue());
        assertEquals(field1.compareTo(field2), 1);
    }
}