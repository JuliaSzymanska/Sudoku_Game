package org.grupa5.exceptions;

import java.util.ResourceBundle;

// TODO: 20.05.2020 chciales tu dac runtime exception
public class SudokuException extends Exception {
    ResourceBundle bundle = ResourceBundle.getBundle("SudokuExceptions");


    // TODO: 16.05.2020 przetestowac czy ta klasa tez sie poprawnie internacjonalizuje

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
