package org.grupa5.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class SudokuBoard {

    private int[][] board = new int[9][9];

    /**
     * @return the board
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * @param board the board to set
     */
    public void setBoard(int[][] board) {
        this.board = board;
    }

    public void fillBoard() {
        int row = 0, column = 0;
        boolean numberFounded = false;
        Random rand = new Random();
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        for (int i = 1; i < 10; i++) {
            numbers.add(i);
        }
        while (!numbers.isEmpty() || !numberFounded) {
            int number = numbers.get(rand.nextInt(numbers.size()));
            if (check(number, row, column)) {
                board[row][column] = number;
                numberFounded = true;
                break;
            } else {
                numbers.remove(number);
            }

        }
    }

    /**
     * Board is divided into 9 sectors, counting from top left to bottom right.
     * [
     * [0, 1 ,2]
     * [3, 4, 5]
     * [6, 7, 8]
     * ]
     * 
     * this method fills a random space in selected sector with a random number
     * usage - init 9 sectors with one random number each in random position
     * 
     * @param sectorNr number of sector to set.
     * 
     */
    private void randomFillSector(int sectorNr) {
        if (sectorNr < 0 || sectorNr > 8) {
            throw new IndexOutOfBoundsException("sectorNr has to be in range from 0 to 8");
        }

        // sectorNr / 3 * 3 - for 0 - 2 : 0; 3 - 5 : 3; 6 - 8 : 6;
        // sectorNr % 3 * 3 - for 0 : 0; 1 : 3; 2 : 6;

        int begX = (sectorNr / 3) * 3;
        int begY = (sectorNr % 3) * 3;
        for(int i = begX; i < begX + 2; i++) {
            for(int j = begY; j < begY + 2; j++) {
                if (this.board[i][j]!=0) {
                    return; // if part of board is init, return.
                }
            }
        }
        Random rand = new Random();
        int random_value =  rand.nextInt(9) + 1;
        int randomX = rand.nextInt(3);
        int randomY = rand.nextInt(3);

        this.board[begX + randomX][begY + randomY] = random_value;

    }

    private boolean checkCol(int col, int value) {
        if (col < 0 || col > 8){
            throw new IndexOutOfBoundsException("col has to be in range 0 - 8");
        }
        for(int i = 0; i <= 8; i++){
            if (this.board[i][col] == value) {
                return false;
            }
        }
        return true;
    }

    private boolean checkRow(int row, int value) {
        if (row < 0 || row > 8){
            throw new IndexOutOfBoundsException("col has to be in range 0 - 8");
        }
        for(int i = 0; i <= 8; i++){
            if (this.board[row][i] == value) {
                return false;
            }
        }
        return true;
    }

    private boolean checkSector(int sectorNr, int value) {
        if (sectorNr < 0 || sectorNr > 8) {
            throw new IndexOutOfBoundsException("sectorNr has to be in range from 0 to 8");
        }
        int begX = (sectorNr / 3) * 3;
        int begY = (sectorNr % 3) * 3;
        for(int i = begX; i < begX + 2; i++) {
            for(int j = begY; j < begY + 2; j++) {
                if (this.board[i][j] == value) {
                    return false;
                }
            }
        }
        return true;
    }

    private int getSectorNumber(int row, int col) {
        return (row / 3) * 3 + col % 3;
    }

    // TODO: to można zoptymalizować jakoś ładnie, bo akutalnie to jakbyśmy chcieli sprawdzić całego boarda to dużo byśmy operacji powtarzali 
    private boolean check(int row, int column, int number) {
        if (row < 0 || row > 8) {
            throw new IndexOutOfBoundsException("col has to be in range 0 - 8");
        }
        if (column < 0 || column > 8) {
            throw new IndexOutOfBoundsException("col has to be in range 0 - 8");
        }
        if (!this.checkCol(column, number)) {
            return false;
        };
        if (!this.checkRow(row, number)) {
            return false;
        }
        int sectorNr = getSectorNumber(row, column);
        if (!this.checkSector(sectorNr, number)) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        SudokuBoard plansza = new SudokuBoard();
        for (int i = 0; i <= 8; i++) {
            plansza.randomFillSector(i);
        }
        plansza.board[1][6] = 123;
        for (int[] x : plansza.board) {
            for (int y : x) {
                System.out.print(y + " ");
            }
            System.out.println();
        }
    }
}
