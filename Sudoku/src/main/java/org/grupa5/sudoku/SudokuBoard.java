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

    /*
     * Board is divided into 9 sectors, counting from top left to bottom right. [
     * [0, 1 ,2] [3, 4, 5] [6, 7, 8] ]
     * this method fills a random space in selected sector with a random number
     * usage - loop over all sectors and init them with one random number each in
     * random position
     *
     * @param sectorNr number of sector to set.
     */

    public int get(int x, int y){
        return board[x][y];
    }

    public void set(int x, int y, int value){
        if(checkBoard(x,y,value)){
            this.board[x][y] = value;
        }
    }

    public void resetBoard() {
        this.board = new int[9][9];
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

    private boolean checkBoard(int row, int column, int number) {
        if (row < 0 || row > 8) {
            throw new IndexOutOfBoundsException("Row has to be in range 0 - 8");
        }
        if (column < 0 || column > 8) {
            throw new IndexOutOfBoundsException("Column has to be in range 0 - 8");
        }
        if(number == 0)
            return true;
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

}
