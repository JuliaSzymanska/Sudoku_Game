package org.grupa5.sudoku;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SudokuField {

    private int value = 0;

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

//    /**
//     * Constructor
//     * init the field 'value' with value od sudokuField param.
//     */
//
//    public SudokuField(SudokuField sudokuField) {
//        this.value = sudokuField.value;
//    }

    /**
     * A simple getter.
     *
     * @return Value of member variable 'value'
     */

    public int getFieldValue() {
        return this.value;
    }

    /**
     * A simple setter.
     *
     * @param value - Set the field 'value' to this param
     */

    public void setFieldValue(int value) {
        if (value < 0 || value > 9) {
            throw new IllegalArgumentException("Value has to be in range 0 - 9");
        }
        this.value = value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!(other instanceof SudokuField)) {
            return false;
        }
        SudokuField liczba = (SudokuField) other;
        return new EqualsBuilder().append(liczba.getFieldValue(), this.value).isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(value).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(value).toHashCode();
    }
}