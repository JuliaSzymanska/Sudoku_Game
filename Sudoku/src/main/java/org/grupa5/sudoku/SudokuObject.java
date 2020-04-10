package org.grupa5.sudoku;

import static org.grupa5.sudoku.SudokuBoard.SUDOKU_DIMENSIONS;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * This class can represent three types of objects in sudoku:
 * Row,
 * Column,
 * Box.
 */

public class SudokuObject {

    private List<SudokuField> object;

    SudokuObject(List<SudokuField> table) {
        if (table.size() != SUDOKU_DIMENSIONS) {
            throw new IllegalArgumentException("List size has to be equal to " 
            + Integer.toString(SUDOKU_DIMENSIONS));
        }
        this.object = Arrays.asList(new SudokuField[SUDOKU_DIMENSIONS]);
        for (int i = 0; i < SUDOKU_DIMENSIONS; i++) {
            this.object.set(i, table.get(i));
        }
    }

    /**
     * Verifies whether the structure is valid.
     *
     * @return False if the structure has duplicate numbers otherwise return True.
     */

    public boolean verify() {
        Set<Integer> setNumbers = new HashSet<Integer>();
        for (SudokuField x : object) {
            if (!setNumbers.add(x.getFieldValue()) && x.getFieldValue() != 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!(other instanceof SudokuObject)) {
            return false;
        }
        SudokuObject sudokuObject = (SudokuObject) other;
        return new EqualsBuilder().append(this.object, sudokuObject.object).isEquals();
    }

    @Override
    public String toString() {
        ToStringStyle style = ToStringStyle.SHORT_PREFIX_STYLE;
        return ToStringBuilder.reflectionToString(this, style);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(object).toHashCode();
    }
}