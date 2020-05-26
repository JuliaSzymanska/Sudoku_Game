package org.grupa5.gui;

import javafx.util.converter.NumberStringConverter;


// TODO: KONWERTER Z NETA, Z jakiegoś powodu przesuwa nam layout xD
public class SudokuNumberStringConverter extends NumberStringConverter {

    @Override
    public String toString(Number value) {
        if (value != null) {
            if (value.equals(0)) {
                return "";
            }
        }
        return super.toString(value);
    }

    @Override
    public Number fromString(String value) {
        if (value.equals("")) {
            return 0;
        }
        return super.fromString(value);
    }
}
