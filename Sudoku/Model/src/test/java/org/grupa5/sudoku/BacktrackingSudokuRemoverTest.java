package org.grupa5.sudoku;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BacktrackingSudokuRemoverTest {

    @Test
    public void removeTest(){
        SudokuBoard board = new SudokuBoard();
        board.solveGame();
        board.removeFields(20);
        int counter = 0;
        List<List<SudokuField>> copy = board.getBoard();
        for(List<SudokuField> i : copy) {
            for(SudokuField j : i) {
                if(j.getValue() == 0) {
                    counter++;
                }
            }
        }
        Assertions.assertEquals(counter, 20);
    }

    @Test
    public void moreVariantsTest(){
        SudokuBoard board = new SudokuBoard();
        board.solveGame();
        board.removeFields(80);
        int counter = 0;
        List<List<SudokuField>> copy = board.getBoard();
        for(List<SudokuField> i : copy) {
            for(SudokuField j : i) {
                if(j.getValue() == 0) {
                    counter++;
                }
            }
        }
        Assertions.assertNotEquals(counter, 80);
    }
}
