package org.grupa5.sudoku;

import java.util.Random;

public class SudokuBoard {

    private int[][] board = new int[9][9];

    /**
     * @return the board
     */
    public int[][] getBoard() {
        return board;
    }

    public void resetBoard() {
        this.board = new int[9][9];
    }

    /*
     * Fills the board in a random way with every usage. Starts by filling every
     * sector in the board in a random space with a random number. The board is
     * divided into 9 sectors 3x3 each. After this randomisation the board is solved
     * using a backtracking algorithm.
     * Then the board is randomly mixed.
     */
    public void fillBoard() {
        for (int i = 0; i <= 8; i++) {
            this.randomFillSector(i);
        }
        this.solveSudoku();
        this.mixBoard();
    }

    public void mixBoard() {
        Random rand = new Random();
        int howManyColumnShuffle = rand.nextInt(100);
        int randomCol;
        int randomCol2;
        for (int i = 0; i < howManyColumnShuffle; i++) {
            randomCol = rand.nextInt(9);
            do {
                randomCol2 = rand.nextInt(3);
                // TODO: zrobić to w jakiś sposób który nie zabija moich oczu :p
                if (randomCol > 2 && randomCol <= 5) {
                    randomCol2 += 3;
                }
                else if (randomCol > 5 && randomCol <= 8) {
                    randomCol2 +=6;
                }
            } while (randomCol == randomCol2);
            this.shuffleColumn(randomCol, randomCol2);
        }
        int howManyRowShuffle = rand.nextInt(100);
        int randomRow;
        int randomRow2;
        for (int i = 0; i < howManyRowShuffle; i++) {
            randomRow = rand.nextInt(9);
            do {
                randomRow2 = rand.nextInt(3);
                if (randomRow > 2 && randomRow <= 5) {
                    randomRow2 += 3;
                }
                else if (randomRow > 5 && randomRow <= 8) {
                    randomRow2 += 6;
                }
        } while (randomRow == randomRow2);
        this.shuffleRow(randomRow, randomRow2);
    }
}

    public void shuffleColumn(int col1, int col2) {
        if (col1 < 0 || col1 > 8) {
            throw new IndexOutOfBoundsException("col1 has to be in range 0 - 8");
        }
        if (col2 < 0 || col2 > 8) {
            throw new IndexOutOfBoundsException("col2 has to be in range 0 - 8");
        }
        int prevCol;
        for (int x = 0; x <= 8; x++) {
            prevCol = this.board[x][col1];
            this.board[x][col1] = this.board[x][col2];
            this.board[x][col2] = prevCol;
        }
    }

    public void shuffleRow(int row1, int row2) {
        if (row1 < 0 || row1 > 8) {
            throw new IndexOutOfBoundsException("row1 has to be in range 0 - 8");
        }
        if (row2 < 0 || row2 > 8) {
            throw new IndexOutOfBoundsException("row2 has to be in range 0 - 8");
        }
        int prevRow;
        for (int x = 0; x <= 8; x++) {
            prevRow = this.board[row1][x];
            this.board[row1][x] = this.board[row2][x];
            this.board[row2][x] = prevRow;
        }
    }

    /**
     * solves sudoku recursively using the backtracking algorithm
     */

    public boolean solveSudoku() { // TODO: usun link, zmien alogrytm, dodaj ladny opis
                                   // https://codepumpkin.com/sudoku-solver-using-backtracking/
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
     * Board is divided into 9 sectors, counting from top left to bottom right. 
     * [
     * [0, 1 ,2] 
     * [3, 4, 5]
     * [6, 7, 8] 
     * ]
     * 
     * this method fills a random space in selected sector with a random number
     * usage - loop over all sectors and init them with one random number each in
     * random position
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
            throw new IndexOutOfBoundsException("row has to be in range 0 - 8");
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
            throw new IndexOutOfBoundsException("row has to be in range 0 - 8");
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
            throw new IndexOutOfBoundsException("sectorNr has to be in range from 0 to 8");
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
        if (col <= 2){

        }
        else if (col <= 5){
            sectorNr += 1;
        }
        else {
            sectorNr += 2;
        }

        return sectorNr;
    }

    // TODO: to można zoptymalizować jakoś ładnie, bo akutalnie to jakbyśmy chcieli
    // sprawdzić całego boarda to dużo byśmy operacji powtarzali
    private boolean check(int row, int column, int number) {
        if (row < 0 || row > 8) {
            throw new IndexOutOfBoundsException("col has to be in range 0 - 8");
        }
        if (column < 0 || column > 8) {
            throw new IndexOutOfBoundsException("col has to be in range 0 - 8");
        }
        if (!this.checkCol(column, number)) {
            return false;
        }
        ;
        if (!this.checkRow(row, number)) {
            return false;
        }
        if (!this.checkSector(getSectorNumber(row, column), number)) {
            return false;
        }
        return true;
    }

    public void printSudoku() {
        System.out.printf("X ");
        for (int i = 0; i <= 8; i++) {
            System.out.printf((char) ('a' + i) + " ");
        }
        System.out.println();
        int counter = 0;
        for (int[] x : this.board) {
            System.out.printf((char) ('a' + counter) + " ");
            for (int y : x) {
                System.out.print(y + " ");
            }
            System.out.println();
            counter++;
        }

    }


    public static void main(String[] args) {
        SudokuBoard plansza = new SudokuBoard();
        plansza.fillBoard();
        plansza.printSudoku();
    }
}
