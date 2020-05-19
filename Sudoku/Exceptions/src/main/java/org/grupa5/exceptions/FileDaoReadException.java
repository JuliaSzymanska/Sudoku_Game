package org.grupa5.exceptions;

import java.util.ResourceBundle;


public class FileDaoReadException extends SudokuException {

    public FileDaoReadException(String s) {
        super(s);
    }

    public FileDaoReadException(String s, Exception e) {
        super(s, e);
    }
}
