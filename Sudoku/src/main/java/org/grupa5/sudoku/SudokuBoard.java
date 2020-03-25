package org.grupa5.sudoku;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SudokuBoard {
    
    private List<List<SudokuField>> board;

    private SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();

    /**
     * Constructor, which variable board ad list with fixed size [9][9].
     */

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
     * This method solve sudoku board using method solve form SudokuSolver interface.
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
        for (int i = 0; i < 9 ; i++) {
            for(int j = 0; j < 9; j++) {
               copy.get(i).get(j).setFieldValue(board.get(i).get(j).getFieldValue());
            }
        }
        // Collections.copy(copy, board);
        // for (int i = 0; i < 9 ; i++) {
        //     for (int j = 0; j < 9 ; j++) {
        //         System.out.print(copy.get(i).get(j).getFieldValue());
        //     }
        //     System.out.println("");
        // }
        return copy;
    }

    /**
     * A simple getter that returns copy of the specific column in the board variable.
     *
     * @return copy of the specific column.
     */

    public SudokuObject getColumn(int column) {
        List<SudokuField> col = Arrays.asList(new SudokuField[9]);
        List<SudokuField> copy = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            col.set(i, board.get(i).get(column));
        }
        Collections.copy(copy, col);
        return new SudokuObject(copy);
    }

    /**
     * A simple getter that returns copy of the specific row in the board variable.
     *
     * @return copy of the specific row.
     */

    public SudokuObject getRow(int row) {
        List<SudokuField> copy = Arrays.asList(new SudokuField[9]);
        Collections.copy(copy, board.get(row));
        return new SudokuObject(copy);
    }

    /**
     * A simple getter that returns copy of the specific sector in the board variable.
     *
     * @return copy of the specific sector.
     */

    public SudokuObject getBox(int row, int column) {
        int sectorNr = this.getSectorNumber(row, column);
        int begX = (sectorNr / 3) * 3;
        int begY = (sectorNr % 3) * 3;
        int k = 0;
        List<SudokuField> box = Arrays.asList(new SudokuField[9]);
        List<SudokuField> copy = Arrays.asList(new SudokuField[9]);
        for (int i = begX; i <= begX + 2; i++) {
            for (int j = begY; j <= begY + 2; j++) {
                box.set(k, board.get(i).get(j));
                k++;
            }
        }
        Collections.copy(copy, box);
        return new SudokuObject(box);
    }

    /**
     * Return the number of sector in which is object at posinion [row][col].
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
        return this.board.get(x).get(y).getFieldValue();
    }

    /**
     * Set value at [x][y] position in board.
     */

    public void set(int x, int y, int value) {
        int temp = this.board.get(x).get(y).getFieldValue();
        this.board.get(x).get(y).setFieldValue(value);
        if (!checkBoard(x, y, value)) {
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
    private boolean checkBoard(int row, int column, int number) {
        if (row < 0 || row > 8) {
            throw new IndexOutOfBoundsException("Row has to be in range 0 - 8");
        }
        if (column < 0 || column > 8) {
            throw new IndexOutOfBoundsException("Column has to be in range 0 - 8");
        }
        if (number == 0) {
            return true;
        }
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

    /*
     * Write all board's numbers to StringBuilder and then converted to String.
     */

       public String getInfoSudoku() {
           StringBuilder output = new StringBuilder("X ");
           for (int i = 0; i <= 8; i++) {
               output.append((char) ('a' + i)).append(" ");
           }
           output.append("\n");
           int counter = 0;
           for (List<SudokuField> x : this.board) {
               output.append((char) ('a' + counter)).append(" ");
               for (SudokuField y : x) {
                   output.append(y.getFieldValue()).append(" ");
               }
               output.append("\n");
               counter++;
           }
           return output.toString();
       }

    public static void main(String[] args) {
        SudokuBoard plansza = new SudokuBoard();
        plansza.solveGame();
        plansza.getBoard().get(1).get(1).setFieldValue(42069);
        System.out.println(plansza.getInfoSudoku());
    }
}
