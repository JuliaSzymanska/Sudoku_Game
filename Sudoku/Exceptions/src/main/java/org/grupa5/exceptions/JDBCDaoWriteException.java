package org.grupa5.exceptions;

public class JDBCDaoWriteException extends SudokuException{

    public JDBCDaoWriteException(String s) {
        super(s);
    }

    public JDBCDaoWriteException(String s, Exception e) {
        super(s, e);
    }
}
