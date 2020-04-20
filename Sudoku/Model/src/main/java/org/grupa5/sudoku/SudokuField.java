package org.grupa5.sudoku;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SudokuField implements Externalizable {

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
        return new EqualsBuilder().append(that.getFieldValue(), this.value).isEquals();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(value).toHashCode();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(value);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        value = in.readInt();
    }
}