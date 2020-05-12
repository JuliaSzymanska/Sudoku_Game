package org.grupa5.sudoku;

import static org.grupa5.sudoku.SudokuBoard.SUDOKU_DIMENSIONS;

import java.io.Serializable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.grupa5.sudoku.exceptions.SetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class can represent three types of objects in sudoku:
 * Row,
 * Column,
 * Box.
 */

public class SudokuObject implements Serializable, Cloneable {

    private static final long serialVersionUID = 547874675;

    private List<SudokuField> object;

    /**
     * Constructor
     * init the field 'object' to this param.
     */

    public SudokuObject(List<SudokuField> table) throws SetException {
        if (table.size() != SUDOKU_DIMENSIONS) {
            Logger logger = LoggerFactory.getLogger(SudokuObject.class);
            if (logger.isDebugEnabled()) {
                logger.debug("Invalid size of list in constructor");
            }
            throw new SetException("listSize");
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
            if (!setNumbers.add(x.getValue()) && x.getValue() != 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof SudokuObject)) {
            return false;
        }
        SudokuObject that = (SudokuObject) o;
        return new EqualsBuilder().append(this.object, that.object).isEquals();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(object).toHashCode();
    }

    /**
     * Clone objects.
     * @return Cloned SudokuObject
     * @throws CloneNotSupportedException when super throws.
     */

    public SudokuObject clone() throws CloneNotSupportedException {
        return (SudokuObject) super.clone();
    }
}