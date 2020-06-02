package org.grupa5.sudoku;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.builder.*;
import org.apache.commons.lang3.exception.CloneFailedException;
import org.grupa5.exceptions.GetException;
import org.grupa5.exceptions.SetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SudokuBoard implements Cloneable, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(SudokuBoard.class);

    private static final long serialVersionUID = 1735345345;

    private List<List<SudokuField>> board;

    /**
     * Size of Sudoku row / column / box.
     */

    public static final int SUDOKU_DIMENSIONS = 9;

    /**
     * Fills the 'board' variable with a 2d fixed size dim [9][9] list.
     */

    public SudokuBoard() {
        this.board = createUninitializedBoard();
        this.resetBoard(this.board);
    }

    public void resetField(int row, int column) {
        this.board.get(row).get(column).setValue(0);
    }

    private List<List<SudokuField>> createUninitializedBoard() {
        return Arrays.asList(
                Arrays.asList(new SudokuField[SUDOKU_DIMENSIONS]),
                Arrays.asList(new SudokuField[SUDOKU_DIMENSIONS]),
                Arrays.asList(new SudokuField[SUDOKU_DIMENSIONS]),
                Arrays.asList(new SudokuField[SUDOKU_DIMENSIONS]),
                Arrays.asList(new SudokuField[SUDOKU_DIMENSIONS]),
                Arrays.asList(new SudokuField[SUDOKU_DIMENSIONS]),
                Arrays.asList(new SudokuField[SUDOKU_DIMENSIONS]),
                Arrays.asList(new SudokuField[SUDOKU_DIMENSIONS]),
                Arrays.asList(new SudokuField[SUDOKU_DIMENSIONS]));
    }

    public SudokuField getField(int x, int y) {
        return this.board.get(x).get(y);
    }

    /**
     * Calls sudokuSolver.solve() on this object, solving the board.
     */

    public void solveGame() {
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        for (List<SudokuField> i : this.board) {
            for (SudokuField j : i) {
                j.setEditable(false);
            }
        }
        sudokuSolver.solve(this);
    }

    public void removeFields(int numberOfFields) {
        new BacktrackingSudokuRemover(this, numberOfFields);
    }

    /**
     * A simple getter that returns copy of the 'board' variable.
     *
     * @return copy of the board.
     */

    public List<List<SudokuField>> getBoard() {
        SudokuBoard copyBoard = this.clone();
        return copyBoard.board;
    }

    /**
     * A simple getter that returns a column from 'board' List by index.
     *
     * @param column Specified column.
     * @return column
     */

    public SudokuObject getColumn(int column) {
        SudokuField[] copyArray = new SudokuField[9];
        for (int i = 0; i < SUDOKU_DIMENSIONS; i++) {
            copyArray[i] = board.get(i).get(column);
        }

        return new SudokuObject(Arrays.asList(copyArray));
    }

    /**
     * A simple getter that returns a row from 'board' List by index.
     *
     * @param row Specified row.
     * @return Row.
     */

    public SudokuObject getRow(int row) {
        SudokuField[] copyArray = new SudokuField[SUDOKU_DIMENSIONS];
        for (int i = 0; i < SUDOKU_DIMENSIONS; i++) {
            copyArray[i] = board.get(row).get(i);
        }

        return new SudokuObject(Arrays.asList(copyArray));
    }

    /**
     * A simple getter that returns a sector from 'board' List by index of row and column.
     *
     * @param row    Specified row.
     * @param column Specified column.
     * @return Sector.
     */

    public SudokuObject getBox(int row, int column) {
        int sectorNr = this.getSectorNumber(row, column);
        int begX = (sectorNr / 3) * 3;
        int begY = (sectorNr % 3) * 3;
        int k = 0;
        SudokuField[] copyArray = new SudokuField[SUDOKU_DIMENSIONS];
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
        if (x < 0 || x > SUDOKU_DIMENSIONS - 1) {
            if (logger.isDebugEnabled()) {
                logger.debug("Invalid Index Provided to get");
            }
            // TODO: 26.05.2020 pamietac 
            throw new GetException("from0to8");
        }
        if (y < 0 || y > SUDOKU_DIMENSIONS - 1) {
            if (logger.isDebugEnabled()) {
                logger.debug("Invalid Index Provided to get");
            }
            throw new GetException("from0to8");
        }
        return this.board.get(x).get(y).getValue();
    }

    /**
     * Set value at [x][y] position in board.
     */

    public void set(int x, int y, int value)  {
        if (x < 0 || x > SUDOKU_DIMENSIONS - 1) {
            if (logger.isDebugEnabled()) {
                logger.debug("Invalid Index Provided to set");
            }
            throw new SetException("from0to8");
        }
        if (y < 0 || y > SUDOKU_DIMENSIONS - 1) {
            if (logger.isDebugEnabled()) {
                logger.debug("Invalid Index Provided to set");
            }
            // TODO: 26.05.2020 pamietac 
            throw new SetException("from0to8");
        }
        if (value < 0 || value > 9) {
            if (logger.isDebugEnabled()) {
                logger.debug("Invalid Value Provided to set");
            }
            throw new SetException("val0to9");
        }
        int temp = this.board.get(x).get(y).getValue();
        this.board.get(x).get(y).setValue(value);
        if (checkBoard(x, y)) {
            this.board.get(x).get(y).setValue(temp);
        }
    }

    /**
     * Reset board, fill with 0.
     */

    public void resetBoard(List<List<SudokuField>> plansza) {
        for (int i = 0; i < SUDOKU_DIMENSIONS; i++) {
            for (int j = 0; j < SUDOKU_DIMENSIONS; j++) {
                plansza.get(i).set(j, new SudokuField());
            }
        }
    }

    /**
     * Check if number can be set at position [row][column].
     */

    private boolean checkBoard(int row, int column) {
        return !getRow(row).verify()
                || !getColumn(column).verify()
                || !getBox(row, column).verify();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof SudokuBoard)) {
            return false;
        }
        SudokuBoard that = (SudokuBoard) o;
        return new EqualsBuilder().append(that.board, this.board).isEquals();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.board).toHashCode();
    }

    /**
     * Tests validity of the board.
     *
     * @return True if board is valid
     */
    public boolean isWholeBoardValid() {
        for (int i = 0; i < SUDOKU_DIMENSIONS; i++) {
            for (int j = 0; j < SUDOKU_DIMENSIONS; j++) {
                if (this.checkBoard(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Clone objects.
     *
     * @return Cloned SudokuBoard.
     */
    @Override
    public SudokuBoard clone() {
        SudokuBoard cloneBoard = new SudokuBoard();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                    cloneBoard.set(i, j, this.get(i, j));
            }
        }
        return cloneBoard;
    }

}