package org.grupa5.sudoku;


public class SudokuField {

    private int value = 0;

    /**
     * A simple getter.
     *
     * @return Value of member variable 'value'
     */

    public int getFieldValue() {
        return this.value;
    }
    /**
     * Constructor
     * init the field 'value' to this param.
     */

    public SudokuField(int value) {
        this.value = value;
    }

    /**
     * Default Constructor.
     */

    public SudokuField() {
        this.value = 0;
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
        SudokuField plansza = (SudokuField) other;
        return this.getFieldValue() == plansza.getFieldValue();
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
}