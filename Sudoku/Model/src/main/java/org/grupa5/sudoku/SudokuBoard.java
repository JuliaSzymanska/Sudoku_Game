package org.grupa5.sudoku;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SudokuBoard implements Externalizable, Cloneable {

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
        try {
            SudokuBoard copyBoard = this.clone();
            return copyBoard.board;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("was unable to copy Board");
        }
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
            throw new IndexOutOfBoundsException("Row has to be in range 0 - 8");
        }
        if (y < 0 || y > SUDOKU_DIMENSIONS - 1) {
            throw new IndexOutOfBoundsException("Column has to be in range 0 - 8");
        }
        return this.board.get(x).get(y).getValue();
    }

    /**
     * Set value at [x][y] position in board.
     */

    public void set(int x, int y, int value) {
        if (x < 0 || x > SUDOKU_DIMENSIONS - 1) {
            throw new IndexOutOfBoundsException("Row has to be in range 0 - 8");
        }
        if (y < 0 || y > SUDOKU_DIMENSIONS - 1) {
            throw new IndexOutOfBoundsException("Column has to be in range 0 - 8");
        }
        if (value < 0 || value > 9) {
            throw new IllegalArgumentException("Number has to be in range 0 - 9");
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

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(board);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        board = (List<List<SudokuField>>) in.readObject();
    }

    private boolean isWholeBoardValid() {
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
     * @throws CloneNotSupportedException when the Board in invalid or when either
     *      IOException or  ClassNotFoundException are caught.
     */
    // TODO: chyba jednak clone override
    // TODO: 'ta implementacja jest nie dobra'
    // TODO: zmienic to na to jak kwapi mówił
    public SudokuBoard clone() throws CloneNotSupportedException {
        if (!isWholeBoardValid()) {
            throw new CloneNotSupportedException(
                    "SudokuBoard doesn't allow cloning non valid boards");
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(this);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            return (SudokuBoard) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new CloneNotSupportedException("Was unable to generate a clone of SudokuBoard");
        }
    }

}