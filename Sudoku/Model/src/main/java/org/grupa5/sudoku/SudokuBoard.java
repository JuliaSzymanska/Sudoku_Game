package org.grupa5.sudoku;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;

// TODO: 10.05.2020 https://stackoverflow.com/questions/21881846/where-does-the-slf4j-log-file-get-saved

import org.apache.commons.lang3.builder.*;
import org.grupa5.sudoku.exceptions.GetException;
import org.grupa5.sudoku.exceptions.SetException;
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

    public void resetField(int row, int column) throws SetException {
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

    public SudokuObject getColumn(int column) throws SetException {
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

    public SudokuObject getRow(int row) throws SetException {
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

    public SudokuObject getBox(int row, int column) throws SetException {
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

    public int get(int x, int y) throws GetException {
        if (x < 0 || x > SUDOKU_DIMENSIONS - 1) {
            if (logger.isDebugEnabled()) {
                logger.debug("Invalid Index Provided to get");
            }
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

    public void set(int x, int y, int value) throws SetException {
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
            throw new SetException("from0to8");
        }
        if (value < 0 || value > 9) {
            if (logger.isDebugEnabled()) {
                logger.debug("Invalid Value Provided to set");
            }
            throw new SetException("from0to8");
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
        try {
        return !getRow(row).verify()
                || !getColumn(column).verify()
                || !getBox(row, column).verify();
        } catch (SetException e) {
            // TODO: 12.05.2020 zajac sie tym wyjatkiem
            if (logger.isDebugEnabled()) {
                logger.debug("Invalid Value Provided to get", e);
            }
        }
        return false;
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
                try {
                    cloneBoard.set(i, j, this.get(i, j));
                } catch (GetException | SetException e) {
                    if (logger.isErrorEnabled()) {
                        // TODO: czy to powinno tak być że tutaj łapiemy i tylko logujemy?
                        logger.error("Exception thrown by clone", e);
                    }
                }
            }
        }
        return cloneBoard;
    }
    // TODO: 16.05.2020 tutaj locale dziala ehh 
//    public static void main(String[] args) {
//        SudokuBoard board = new SudokuBoard();
//        System.out.println(board.toString());
//        Locale.setDefault(new Locale("en_en"));
//        try {
//            board.set(0, 0, 12);
//        } catch (SetException e) {
//            System.out.println(e);
//        }
//        Locale.setDefault(new Locale("pl_pl"));
//        try {
//            board.set(0, 0, 12);
//        } catch (SetException e) {
//            System.out.println(e);
//        }
//    }

}