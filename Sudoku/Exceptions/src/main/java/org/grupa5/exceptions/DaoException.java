package org.grupa5.exceptions;

import java.util.ResourceBundle;

public class DaoException extends Exception {

    static final String defaultMsgKey = "daoException";

    private ResourceBundle bundle = ResourceBundle.getBundle("SudokuExceptions");

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