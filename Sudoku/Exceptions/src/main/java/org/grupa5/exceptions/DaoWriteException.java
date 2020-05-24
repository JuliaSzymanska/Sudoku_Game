package org.grupa5.exceptions;

public class DaoWriteException extends SudokuException{

    public DaoWriteException(String s) {
        super(s);
    }

    public DaoWriteException(String s, Exception e) {
        super(s, e);
    }
}
