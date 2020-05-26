package org.grupa5.sudoku;

import java.io.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.grupa5.exceptions.SetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SudokuField implements Serializable, Cloneable, Comparable<SudokuField> {

    private static final Logger logger = LoggerFactory.getLogger(SudokuField.class);

    private static final long serialVersionUID = 756545346;

    private int value;

    /**
     * Default Constructor.
     */

    public SudokuField() {
        this.value = 0;
    }

    /**
     * Constructor
     * init the field 'value' to this param.
     */

    public SudokuField(int value) {
        this.value = value;
    }

    /**
     * Copy constructor.
     */

    public SudokuField(SudokuField sudokuField) {
        this.value = sudokuField.value;
    }

    /**
     * A simple getter.
     *
     * @return Value of member variable 'value'
     */

    public int getValue() {
        return this.value;
    }

    /**
     * A simple setter.
     *
     * @param value - Set the field 'value' to this param
     */

    public void setValue(int value) {
        if (value < 0 || value > 9) {
            // TODO: 26.05.2020 pamietac 
            if (logger.isDebugEnabled()) {
                logger.debug("Invalid Index Provided to set");
            }
            throw new SetException("Value has to be in range 0 - 9");
        }
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof SudokuField)) {
            return false;
        }
        SudokuField that = (SudokuField) o;
        return new EqualsBuilder().append(that.getValue(), this.value).isEquals();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(value).toHashCode();
    }

    /**
     * Clone objects.
     * @return Cloned SudokuField
     * @throws CloneNotSupportedException when thrown by super implementation.
     */
    public SudokuField clone() throws CloneNotSupportedException {
        return (SudokuField) super.clone();
    }

    @Override
    public int compareTo(SudokuField o) {
        if (o == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Comparing to null");
            }
            throw new NullPointerException("comparing to null");
        }
        if (this.equals(o)) {
            return 0;
        } else if (this.value < o.value) {
            return -1;
        } else {
            return 1;
        }
    }
}