package org.grupa5.exceptions;

import java.util.ResourceBundle;

public class DaoException extends Exception {

    ResourceBundle bundle = ResourceBundle.getBundle("SudokuExceptions");

    // TODO: 16.05.2020 przetestowac czy ta klasa tez sie poprawnie internacjonalizuje

    public DaoException(String s) {
        super(s);
    }

    public DaoException(String s, Exception e) {
        super(s, e);
    }

    @Override
    public String getLocalizedMessage() {
        return bundle.getString(getMessage());
    }
}