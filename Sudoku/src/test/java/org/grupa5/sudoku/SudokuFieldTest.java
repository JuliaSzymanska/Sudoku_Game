package org.grupa5.sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SudokuFieldTest {

    @Test
    void getterAndSetterTest() {
        SudokuBoard sudoku = new SudokuBoard();
        assertEquals(sudoku.getBoard().get(0).get(0).getFieldValue(), 0);
        sudoku.getBoard().get(0).get(0).setFieldValue(3);
        assertEquals(sudoku.getBoard().get(0).get(0).getFieldValue(), 3);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            sudoku.getBoard().get(0).get(0).setFieldValue(10);
        });
    }

}
