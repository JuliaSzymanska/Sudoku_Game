package org.grupa5.exceptions;

import java.util.Locale;
import java.util.ResourceBundle;

// TODO: 16.05.2020 przetestowac czy ta klasa tez sie poprawnie internacjonalizuje

public class FileDaoWriteException extends SudokuException {

    public FileDaoWriteException(String s) {
        super(s);
    }

    public FileDaoWriteException(String s, Exception e) {
        super(s, e);
    }
}