package org.grupa5.sudoku;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import com.jparams.verifier.tostring.ToStringVerifier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for simple App. Testing methods: resetBoard and getters.
 */
public class SudokuBoardTest {

    //TODO: w całej strukturze plików tak myślę, że zmieniłbym żeby
    // było org.grupa5.sudoku.model nie a potem w gui byśmy mieli
    // org.grupa5.sudoku.gui czy coś
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
    void getterTest() {
        SudokuBoard sudoku = new SudokuBoard();
        assertEquals(sudoku.get(2, 2), 0);
        assertThrows(IndexOutOfBoundsException.class, () -> {
            sudoku.get(9, 7);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            sudoku.get(7, 9);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            sudoku.get(-1, 7);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            sudoku.get(7, -1);
        });
    }

    @Test
    void setterTest() {
        SudokuBoard sudoku = new SudokuBoard();
        sudoku.set(2, 2, 2);
        assertEquals(sudoku.get(2, 2), 2);
        assertThrows(IndexOutOfBoundsException.class, () -> {
            sudoku.set(10, 7, 3);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            sudoku.set(7, 10, 3);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            sudoku.set(-1, 7, 3);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            sudoku.set(7, -1, 3);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            sudoku.set(7, 7, -1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            sudoku.set(7, 7, 10);
        });
    }

    // TODO: wykomentowany test odkomentowac jak juz to zrobimy zeby sie dalo testowac
//    @Test
//    public void cloneExceptionTest() {
//        SudokuBoard sudoku = new SudokuBoard();
//        assertThrows(CloneNotSupportedException.class, () -> {
//            sudoku.clone();
//        });
//    }

    @Test
    void equalsTest() {
        SudokuBoard sudoku1 = new SudokuBoard();
        SudokuBoard sudoku2 = new SudokuBoard();

        assertNotEquals(sudoku1, null);
        assertEquals(sudoku1, sudoku1);
        // assert not solved are equal
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
    void HashCodeTest() {
        SudokuBoard sudoku1 = null;
        SudokuBoard sudoku2 = null;
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
    public void testToString() {
        ToStringVerifier.forClass(SudokuBoard.class).verify();
    }



    @Test
    public void cloneTest() {
        SudokuBoard sudoku1 = new SudokuBoard();
        SudokuBoard sudoku2 = null;

        sudoku1.solveGame();
        try {
            sudoku2 = sudoku1.clone();
        } catch (CloneNotSupportedException e) {
            fail("exception thrown");
        }
        assertEquals(sudoku1, sudoku2);
        assertEquals(sudoku1.hashCode(), sudoku2.hashCode());
        assertNotSame(sudoku1, sudoku2);
        assertEquals(sudoku1.getBox(1, 5), sudoku2.getBox(1, 5));
        assertEquals(sudoku1.getBox(1, 5).hashCode(),sudoku2.getBox(1, 5).hashCode());
        assertNotSame(sudoku1.getBox(1, 5), sudoku2.getBox(1, 5));
    }

}