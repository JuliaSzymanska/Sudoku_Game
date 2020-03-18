package org.grupa5.sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
* Unit test for simple App.
* Testing SudokuSolver interface implementations
* Checking if SudokuSolver generates a valid Sudoku board and whether two generated boards are different from eachother.
*/
public class SudokuSolverTest {

   @Test
   void solveSudokuTest() {
        SudokuBoard Plansza = new SudokuBoard();
        SudokuSolver Wypelniacz = new BacktrackingSudokuSolver();
        Wypelniacz.solve(Plansza);
        boolean check = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                for (int k = 0; k < 9; k++) {
                    if (k != i && Plansza.get(i, j) == Plansza.get(k, j) || (k != j && Plansza.get(i, j) == Plansza.get(i, k))) {
                        check = false;
                        break;
                    }
                }
                int begX = (i / 3) * 3, begY = (j / 3) * 3;
                for (int x = begX; x <= begX + 2; x++) {
                    for (int y = begY; y <= begY + 2; y++) {
                        if (Plansza.get(i, j) == Plansza.get(x, y) && (x != i && y != j)) {
                            check = false;
                            break;
                        }
                    }
                }
            }
            assertTrue(check);
        }
   }

   @Test
   void noRepeatsTest() {
       SudokuBoard sudoku = new SudokuBoard();
       SudokuBoard sudoku2 = new SudokuBoard();
       SudokuSolver Wypelniacz = new BacktrackingSudokuSolver();
       Wypelniacz.solve(sudoku);
       sudoku.resetBoard();
       Wypelniacz.solve(sudoku2);
       assertNotEquals(sudoku, sudoku2);

   }
        
   
}

