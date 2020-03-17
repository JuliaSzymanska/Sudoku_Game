package org.grupa5.sudoku;

import java.util.Arrays;
import java.util.Random;

public class SudokuBoard {

    private int[][] board = new int[9][9];

    /**
     * Return copy of the board.
     */
    public int[][] getBoard() {
        int[][] copy = Arrays.stream(this.board).map(int[]::clone).toArray(int[][]::new);
        return copy;
    }

    public void resetBoard() {
        this.board = new int[9][9];
    }

    /**
     * Fills the board in a random way with every usage. Starts by filling every
     * sector in the board in a random space with a random number. The board is.
     * divided into 9 sectors 3x3 each. After this randomisation the board is solved.
     * using a backtracking algorithm. Then the board is randomly mixed.
     */
    public void fillBoard() {
        for (int i = 0; i <= 8; i++) {
            this.randomFillSector(i);
        }
        this.solveSudoku();
        this.mixBoard();
    }

    private void mixBoard() {
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
            this.shuffleColumn(randomCol, randomCol2);
            this.shuffleRow(randomRow, randomRow2);
        }
    }

    private void shuffleColumn(int col1, int col2) {
        if (col1 < 0 || col1 > 8) {
            throw new IndexOutOfBoundsException("Column col1 has to be in range 0 - 8");
        }
        if (col2 < 0 || col2 > 8) {
            throw new IndexOutOfBoundsException("Column col2 has to be in range 0 - 8");
        }
        int prevCol;
        for (int x = 0; x <= 8; x++) {
            prevCol = this.board[x][col1];
            this.board[x][col1] = this.board[x][col2];
            this.board[x][col2] = prevCol;
        }
    }

    private void shuffleRow(int row1, int row2) {
        if (row1 < 0 || row1 > 8) {
            throw new IndexOutOfBoundsException("Row row1 has to be in range 0 - 8");
        }
        if (row2 < 0 || row2 > 8) {
            throw new IndexOutOfBoundsException("Row row2 has to be in range 0 - 8");
        }
        int prevRow;
        for (int x = 0; x <= 8; x++) {
            prevRow = this.board[row1][x];
            this.board[row1][x] = this.board[row2][x];
            this.board[row2][x] = prevRow;
        }
    }

    /**
     * Solves sudoku recursively using the backtracking algorithm.
     */

    private boolean solveSudoku() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (this.board[row][col] == 0) {
                    for (int number = 1; number <= 9; number++) {
                        if (this.check(row, col, number)) {
                            this.board[row][col] = number;
                            if (solveSudoku()) {
                                return true;
                            } else {
                                this.board[row][col] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Board is divided into 9 sectors, counting from top left to bottom right. [
     * [0, 1 ,2] [3, 4, 5] [6, 7, 8] ]
     * this method fills a random space in selected sector with a random number
     * usage - loop over all sectors and init them with one random number each in
     * random position
     *
     * @param sectorNr number of sector to set.
     */
    private void randomFillSector(int sectorNr) {
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
                if (this.board[i][j] != 0) {
                    return; // if sector is init, return.
                }
            }
        }
        Random rand = new Random();
        int randomVal = rand.nextInt(9) + 1;
        int randomX = rand.nextInt(3);
        int randomY = rand.nextInt(3);
        while (!this.check(begX + randomX, begY + randomY, randomVal)) {
            randomVal = rand.nextInt(9) + 1;
        }
        this.board[begX + randomX][begY + randomY] = randomVal;
    }

    private boolean checkCol(int col, int value) {
        if (col < 0 || col > 8) {
            throw new IndexOutOfBoundsException("Column has to be in range 0 - 8");
        }
        for (int i = 0; i <= 8; i++) {
            if (this.board[i][col] == value) {
                return false;
            }
        }
        return true;
    }

    private boolean checkRow(int row, int value) {
        if (row < 0 || row > 8) {
            throw new IndexOutOfBoundsException("Row has to be in range 0 - 8");
        }
        for (int i = 0; i <= 8; i++) {
            if (this.board[row][i] == value) {
                return false;
            }
        }
        return true;
    }

    private boolean checkSector(int sectorNr, int value) {
        if (sectorNr < 0 || sectorNr > 8) {
            throw new IndexOutOfBoundsException("Sector sectorNr has to be in range from 0 to 8");
        }
        int begX = (sectorNr / 3) * 3;
        int begY = (sectorNr % 3) * 3;
        for (int i = begX; i <= begX + 2; i++) {
            for (int j = begY; j <= begY + 2; j++) {
                if (this.board[i][j] == value) {
                    return false;
                }
            }
        }
        return true;
    }

    private int getSectorNumber(int row, int col) {
        int sectorNr = (row / 3) * 3;
        sectorNr += col / 3;
        return sectorNr;
    }

    private boolean check(int row, int column, int number) {
        if (row < 0 || row > 8) {
            throw new IndexOutOfBoundsException("Row has to be in range 0 - 8");
        }
        if (column < 0 || column > 8) {
            throw new IndexOutOfBoundsException("Column has to be in range 0 - 8");
        }
        return this.checkCol(column, number) && this.checkRow(row, number)
                && this.checkSector(getSectorNumber(row, column), number);
    }

    /**
     * Write all board's numbers to StringBuilder and then converted to String.
     */

    public String getInfoSudoku() {
        StringBuilder output = new StringBuilder("X ");
        for (int i = 0; i <= 8; i++) {
            output.append((char) ('a' + i)).append(" ");
        }
        output.append("\n");
        int counter = 0;
        for (int[] x : this.board) {
            output.append((char) ('a' + counter)).append(" ");
            for (int y : x) {
                output.append(y).append(" ");
            }
            output.append("\n");
            counter++;
        }
        return output.toString();
    }

    /**
     * Main method to use class Sudoku.
     */

    public static void main(String[] args) {
        SudokuBoard sudoku = new SudokuBoard();
        sudoku.fillBoard();
        String output = sudoku.getInfoSudoku();
        System.out.println(output);
    }
}
