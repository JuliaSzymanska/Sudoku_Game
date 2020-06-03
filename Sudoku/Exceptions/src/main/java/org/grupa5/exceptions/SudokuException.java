package org.grupa5.exceptions;

import java.util.ResourceBundle;

public class SudokuException extends RuntimeException {

    static final String defaultMsgKey = "sudokuException";

    private static ResourceBundle bundle = ResourceBundle.getBundle("SudokuExceptions");

    public SudokuException(String s) {
        super(s);
    }

    public SudokuException(String s, Exception e) {
        super(s, e);
    }

    @Override
    public String getLocalizedMessage() {
        return bundle.getString(getMessage());
    }
}
