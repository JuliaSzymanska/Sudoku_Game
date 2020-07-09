package org.grupa5.sudoku;

import com.jparams.verifier.tostring.ToStringVerifier;
import org.grupa5.exceptions.SetException;
import org.grupa5.exceptions.GetException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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
                assertNotEquals(sudoku.getBoard().get(i).get(j).getValue(), 0);
            }
        }
//        Assertions.fail();
    }

    @Test
    void getRowTest() throws SetException {
        List<List<SudokuField>> board;
        SudokuBoard sudoku = new SudokuBoard();
        sudoku.solveGame();
        board = sudoku.getBoard();
        SudokuObject row = new SudokuObject(board.get(0));
        SudokuObject row2 = sudoku.getRow(0);
        assertEquals(row, row2);
    }

    @Test
    void getColumnTest() throws SetException {
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
    void getBoxTest() throws SetException {
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
    void getterAndSetterTest() throws SetException, GetException {
        SudokuBoard sudoku = new SudokuBoard();
        sudoku.set(2, 2, 2);
        assertEquals(sudoku.get(2, 2), 2);
    }

    @Test
    void getterTestException() {
        SudokuBoard sudoku = new SudokuBoard();
        assertThrows(GetException.class, () -> sudoku.get(9, 7));
        assertThrows(GetException.class, () -> sudoku.get(7, 9));
        assertThrows(GetException.class, () -> sudoku.get(-1, 7));
        assertThrows(GetException.class, () -> sudoku.get(7, -1));
    }

    @Test
    void setterTestException() {
        SudokuBoard sudoku = new SudokuBoard();
        assertThrows(SetException.class, () -> sudoku.set(10, 7, 3));
        assertThrows(SetException.class, () -> sudoku.set(7, 10, 3));
        assertThrows(SetException.class, () -> sudoku.set(-1, 7, 3));
        assertThrows(SetException.class, () -> sudoku.set(7, -1, 3));
        assertThrows(SetException.class, () -> sudoku.set(7, 7, -1));
        assertThrows(SetException.class, () -> sudoku.set(7, 7, 10));
    }

    @Test
    void getFieldTet() throws GetException {
        SudokuBoard board = new SudokuBoard();
        board.solveGame();
        assertEquals(board.get(0, 0), board.getField(0, 0).getValue());
    }


    @Test
    void equalsTest() throws GetException, SetException {
        SudokuBoard sudoku1 = new SudokuBoard();
        SudokuBoard sudoku2 = new SudokuBoard();

        assertNotEquals(sudoku1, null);
        assertEquals(sudoku1, sudoku1);
        assertEquals(sudoku1, sudoku2);
        assertNotEquals(sudoku1, Object.class);

        sudoku1.solveGame();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudoku2.set(i, j, sudoku1.get(i, j));
            }
        }

        assertTrue(sudoku1.equals(sudoku2) && sudoku2.equals(sudoku1));
        sudoku2.set(3, 3, 0);
        assertNotEquals(sudoku1, sudoku2);
    }

    @Test
    void validBoardTest() throws SetException {
        SudokuBoard board = new SudokuBoard();
        board.solveGame();
        assertTrue(board.isWholeBoardValid());
    }

    @Test
    void HashCodeTest() {
        SudokuBoard sudoku1;
        SudokuBoard sudoku2;
        do {
            sudoku1 = new SudokuBoard();
            sudoku2 = new SudokuBoard();
            sudoku1.solveGame();
            sudoku2.solveGame();
        } while (sudoku1.hashCode() == sudoku2.hashCode());
        assertNotEquals(sudoku1.hashCode(), sudoku2.hashCode());
        assertNotEquals(sudoku1, sudoku2);
    }

    @Test
    void testToString() {
        ToStringVerifier.forClass(SudokuBoard.class).verify();
    }

    @Test
    void cloneTest() throws SetException {
        SudokuBoard sudoku1 = new SudokuBoard();
        SudokuBoard sudoku2;

        sudoku1.solveGame();
        sudoku2 = sudoku1.clone();
        assertEquals(sudoku1, sudoku2);
        assertEquals(sudoku1.hashCode(), sudoku2.hashCode());
        assertNotSame(sudoku1, sudoku2);
        assertEquals(sudoku1.getBox(1, 5), sudoku2.getBox(1, 5));
        assertEquals(sudoku1.getBox(1, 5).hashCode(), sudoku2.getBox(1, 5).hashCode());
        assertNotSame(sudoku1.getBox(1, 5), sudoku2.getBox(1, 5));
    }

    @Test
    void internationalizedtSetExceptionTest() {
        SudokuBoard sudoku1 = new SudokuBoard();

        Locale.setDefault(new Locale("en", "en"));
        SetException exception1 = assertThrows(
                SetException.class,
                () -> sudoku1.set(0, 0, -1)
        );
        assertEquals("Sudoku Field Value Provided has to be in range <0, 9>", exception1.getLocalizedMessage());

        SetException exception2 = assertThrows(
                SetException.class,
                () -> sudoku1.set(-1, -1, 1)
        );
        assertEquals("Value has to be in range 0 - 8", exception2.getLocalizedMessage());

        Locale.setDefault(new Locale("pl", "pl"));
        SetException exception3 = assertThrows(
                SetException.class,
                () -> sudoku1.set(0, 0, -1)
        );
        assertEquals("Wartosc Dla Sudoku Field Musi byc od 0 do 9", exception3.getLocalizedMessage());

        SetException exception4 = assertThrows(
                SetException.class,
                () -> sudoku1.set(-1, -1, 1)
        );
        assertEquals("Wartosc musi byc w zasiegu od 0 do 8", exception4.getLocalizedMessage());
    }


    @Test
    void internationalizedtGetExceptionTest() {
        SudokuBoard sudoku1 = new SudokuBoard();

        Locale.setDefault(new Locale("en", "en"));
        GetException exceptionEN = assertThrows(
                GetException.class,
                () -> sudoku1.get(-1, -1)
        );
        assertEquals("Value has to be in range 0 - 8", exceptionEN.getLocalizedMessage());

        Locale.setDefault(new Locale("pl", "pl"));
        GetException exceptionPL = assertThrows(
                GetException.class,
                () -> sudoku1.get(-1, -1)
        );
        assertEquals("Wartosc musi byc w zasiegu od 0 do 8", exceptionPL.getLocalizedMessage());
    }
}