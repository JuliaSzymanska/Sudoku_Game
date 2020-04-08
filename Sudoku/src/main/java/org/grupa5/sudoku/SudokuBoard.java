package org.grupa5.sudoku;

import java.util.Arrays;
import java.util.List;

public class SudokuBoard {
    
    // TODO: Niby mówił żeby nie robić 2d listy, ale już zrobiliśmy to chyba nie jest problem?
    private List<List<SudokuField>> board;
    
    // TODO: on chce to w konstrukotrze
    private SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
    

    /**
     * Fills the 'board' variable with a 2d fixed size dim [9][9] list.
     */

    // TODO: Magiczne numerki w rozmiarze tablicy.
    // jakiś private final int BoardSize = rozmiar;
    // Powiedział że stałe się pisze Dużymi literami które rozdziela się podkreślnikami
    // Przy okazji można tego boarda zamienić na inicjalizacje w loopie jak się nam bardzo chce
    // Mi się nie chce :p
    public SudokuBoard() {
        board = Arrays.asList(
                Arrays.asList(new SudokuField[9]),
                Arrays.asList(new SudokuField[9]),
                Arrays.asList(new SudokuField[9]),
                Arrays.asList(new SudokuField[9]),
                Arrays.asList(new SudokuField[9]),
                Arrays.asList(new SudokuField[9]),
                Arrays.asList(new SudokuField[9]),
                Arrays.asList(new SudokuField[9]),
                Arrays.asList(new SudokuField[9]));
        this.resetBoard(this.board);
    }

    /**
     * Calls sudokuSolver.solve() on this object, solving the board.
     */

    public void solveGame() {
        sudokuSolver.solve(this);
    }

    /**
     * A simple getter that returns copy of the 'board' variable.
     *
     * @return copy of the board.
     */

    public List<List<SudokuField>> getBoard() {
        List<List<SudokuField>> copy = Arrays.asList(
                Arrays.asList(new SudokuField[9]),
                Arrays.asList(new SudokuField[9]),
                Arrays.asList(new SudokuField[9]),
                Arrays.asList(new SudokuField[9]),
                Arrays.asList(new SudokuField[9]),
                Arrays.asList(new SudokuField[9]),
                Arrays.asList(new SudokuField[9]),
                Arrays.asList(new SudokuField[9]),
                Arrays.asList(new SudokuField[9]));
        resetBoard(copy);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                copy.get(i).get(j).setFieldValue(board.get(i).get(j).getFieldValue());
            }
        }
        return copy;
    }

    /**
     * A simple getter that returns a column from 'board' List by index.
     * @param column Specified column.
     * @return column
     */


    public SudokuObject getColumn(int column) {
        SudokuField[] copyArray = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            copyArray[i] = board.get(i).get(column);
        }
        return new SudokuObject(Arrays.asList(copyArray));
    }

    /**
     * A simple getter that returns a row from 'board' List by index.
     * @param row Specified row.
     * @return Row.
     */

    // TODO: todo żeby się rzucało
    // tutaj w tych get row col box dałem że się przepisują indeksy a nie tworzą się kopie
    // bo tak mówił 1.04.2020
    public SudokuObject getRow(int row) {
        SudokuField[] copyArray = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            copyArray[i] = board.get(row).get(i);
        }
        return new SudokuObject(Arrays.asList(copyArray));
    }

    /**
     * A simple getter that returns a sector from 'board' List by index of row and column
     * @param row Specified row.
     * @param column Specified column.
     * @return Sector.
     */

    public SudokuObject getBox(int row, int column) {
        int sectorNr = this.getSectorNumber(row, column);
        int begX = (sectorNr / 3) * 3;
        int begY = (sectorNr % 3) * 3;
        int k = 0;
        SudokuField[] copyArray = new SudokuField[9];
        for (int i = begX; i <= begX + 2; i++) {
            for (int j = begY; j <= begY + 2; j++) {
                copyArray[k] = board.get(i).get(j);
                k++;
            }
        }
        return new SudokuObject(Arrays.asList(copyArray));
    }

    /**
     * Gets the number of sector that contains object of positions row, col.
     *
     * @param row first coordinate
     * @param col second coordinate
     * @return the number of sector that contains [row][col].
     */

    private int getSectorNumber(int row, int col) {
        int sectorNr = (row / 3) * 3;
        sectorNr += col / 3;
        return sectorNr;
    }

    /**
     * Return int from board at [x][y] position.
     */

    public int get(int x, int y) {
        if (x < 0 || x > 8) {
            throw new IndexOutOfBoundsException("Row has to be in range 0 - 8");
        }
        if (y < 0 || y > 8) {
            throw new IndexOutOfBoundsException("Column has to be in range 0 - 8");
        }
        return this.board.get(x).get(y).getFieldValue();
    }

    /**
     * Set value at [x][y] position in board.
     */

    public void set(int x, int y, int value) {
        if (x < 0 || x > 8) {
            throw new IndexOutOfBoundsException("Row has to be in range 0 - 8");
        }
        if (y < 0 || y > 8) {
            throw new IndexOutOfBoundsException("Column has to be in range 0 - 8");
        }
        if (value < 0 || value > 9) {
            throw new IllegalArgumentException("Number has to be in range 0 - 9");
        }
        int temp = this.board.get(x).get(y).getFieldValue();
        this.board.get(x).get(y).setFieldValue(value);
        if (!checkBoard(x, y)) {
            this.board.get(x).get(y).setFieldValue(temp);
        }
    }

    /**
     * Reset board, fill with 0.
     */

    public void resetBoard(List<List<SudokuField>> plansza) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                plansza.get(i).set(j, new SudokuField());
            }
        }
    }

    /**
     * Check if number can be set at position [row][column].
     */

    private boolean checkBoard(int row, int column) {
        return getRow(row).verify() && getColumn(column).verify() && getBox(row, column).verify();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!(other instanceof SudokuBoard)) {
            return false;
        }
        SudokuBoard plansza = (SudokuBoard) other;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (plansza.get(i, j) != this.get(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    // /**
    //  * Write all board's numbers to StringBuilder and then converted to String.
    //  */

    //    public String getInfoSudoku() {
    //        StringBuilder output = new StringBuilder("X ");
    //        for (int i = 0; i <= 8; i++) {
    //            output.append((char) ('a' + i)).append(" ");
    //        }
    //        output.append("\n");
    //        int counter = 0;
    //        for (List<SudokuField> x : this.board) {
    //            output.append((char) ('a' + counter)).append(" ");
    //            for (SudokuField y : x) {
    //                output.append(y.getFieldValue()).append(" ");
    //            }
    //            output.append("\n");
    //            counter++;
    //        }
    //        return output.toString();
    //    }
    //
    //    public static void main(String[] args) {
    //        SudokuBoard plansza = new SudokuBoard();
    //        plansza.solveGame();
    //        System.out.println(plansza.getInfoSudoku());
    //    }

    //    public static void main(String[] args) {
    //        SudokuBoard plansza = new SudokuBoard();
    //        plansza.getRow(1);
    //        plansza.getColumn(1);
    //    }
}
