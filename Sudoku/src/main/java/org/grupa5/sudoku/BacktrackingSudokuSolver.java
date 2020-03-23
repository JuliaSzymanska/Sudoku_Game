package org.grupa5.sudoku;

import java.util.Random;

public class BacktrackingSudokuSolver implements SudokuSolver {

    /**
     * Fills the board in a random way with every usage. Starts by filling every.
     * sector in the board in a random space with a random number. The board is.
     * divided into 9 sectors 3x3 each. After this randomisation the board is solved.
     * using a backtracking algorithm. Then the board is randomly mixed.
     */

    @Override
    public void solve(SudokuBoard board) {
        for (int i = 0; i <= 8; i++) {
            this.randomFillSector(i, board);
        }
        this.solveSudoku(board);
        this.mixBoard(board);
    }

    /**
     * Board is divided into 9 sectors, counting from top left to bottom right.
     * [0, 1 ,2] [3, 4, 5] [6, 7, 8] ].
     * this method fills a random space in selected sector with a random number.
     * usage - loop over all sectors and init them with one random number each in.
     * random position.
     *
     * @param sectorNr number of sector to set.
     * @param board    is an object of SudokuBoard class
     */

    private void randomFillSector(int sectorNr, SudokuBoard board) {
        if (sectorNr < 0 || sectorNr > 8) {
            throw new IndexOutOfBoundsException(
                    "Sector number (sectorNr) has to be in range from 0 to 8");
        }

        // sectorNr / 3 * 3 - for 0 - 2 : 0; 3 - 5 : 3; 6 - 8 : 6;
        // sectorNr % 3 * 3 - for 0 : 0; 1 : 3; 2 : 6;

        int begX = (sectorNr / 3) * 3;
        int begY = (sectorNr % 3) * 3;
        for (int i = begX; i < begX + 2; i++) {
            for (int j = begY; j < begY + 2; j++) {
                if (board.get(i, j) != 0) {
                    return; // if sector is init, return.
                }
            }
        }
        Random rand = new Random();
        int randomVal = rand.nextInt(9) + 1;
        int randomX = rand.nextInt(3);
        int randomY = rand.nextInt(3);
        board.set(begX + randomX, begY + randomY, randomVal);
        while (board.get(begX + randomX, begY + randomY) != randomVal) {
            randomVal = rand.nextInt(9) + 1;
            board.set(begX + randomX, begY + randomY, randomVal);
        }
    }

    /**
     * Method mix rows and columns.
     */

    private void mixBoard(SudokuBoard board) {
        Random rand = new Random();
        int howManyShuffle = rand.nextInt(100);
        int randomCol;
        int randomCol2;
        int randomRow;
        int randomRow2;
        for (int i = 0; i < howManyShuffle; i++) {
            randomCol = rand.nextInt(9);
            randomRow = rand.nextInt(9);
            do {
                randomCol2 = rand.nextInt(3);
                randomCol2 += (randomCol / 3) * 3;
            } while (randomCol == randomCol2);
            do {
                randomRow2 = rand.nextInt(3);
                randomRow2 += (randomRow / 3) * 3;
            } while (randomRow == randomRow2);
            this.shuffleColumn(randomCol, randomCol2, board);
            this.shuffleRow(randomRow, randomRow2, board);
        }
    }

    /**
     * Method mix columns.
     */

    private void shuffleColumn(int col1, int col2, SudokuBoard board) {
        if (col1 < 0 || col1 > 8) {
            throw new IndexOutOfBoundsException("Column col1 has to be in range 0 - 8");
        }
        if (col2 < 0 || col2 > 8) {
            throw new IndexOutOfBoundsException("Column col2 has to be in range 0 - 8");
        }
        int prevCol;
        for (int x = 0; x <= 8; x++) {
            prevCol = board.get(x, col1);
            board.set(x, col1, board.get(x, col2));
            board.set(x, col2, prevCol);
        }
    }

    /**
     * Method mix rows.
     */

    private void shuffleRow(int row1, int row2, SudokuBoard board) {
        if (row1 < 0 || row1 > 8) {
            throw new IndexOutOfBoundsException("Row row1 has to be in range 0 - 8");
        }
        if (row2 < 0 || row2 > 8) {
            throw new IndexOutOfBoundsException("Row row2 has to be in range 0 - 8");
        }
        int prevRow;
        for (int x = 0; x <= 8; x++) {
            prevRow = board.get(row1, x);
            board.set(row1, x, board.get(row2, x));
            board.set(row2, x, prevRow);
        }
    }

    /**
     * Solve sudoku filled with some numbers.
     */

    private boolean solveSudoku(SudokuBoard board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board.get(row, col) == 0) {
                    for (int number = 1; number <= 9; number++) {
                        board.set(row, col, number);
                        if (board.get(row, col) == number) {
                            if (this.solveSudoku(board)) {
                                return true;
                            } else {
                                board.set(row, col, 0);
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

}
