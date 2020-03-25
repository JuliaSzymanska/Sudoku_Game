package org.grupa5.sudoku;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SudokuFieldTest {

    @Test
    void getterAndSetterTest() {
        SudokuBoard sudoku = new SudokuBoard();
        assertEquals(sudoku.getBoard().get(0).get(0).getFieldValue(), 0);
        List<List<SudokuField>> lista = sudoku.getBoard();
        lista.get(0).get(0).setFieldValue(3);
        assertEquals(sudoku.getBoard().get(0).get(0).getFieldValue(), 0);
        assertEquals(3, lista.get(0).get(0).getFieldValue());
        assertThrows(IllegalArgumentException.class, () -> {
            sudoku.getBoard().get(0).get(0).setFieldValue(10);
        });
    }

}
