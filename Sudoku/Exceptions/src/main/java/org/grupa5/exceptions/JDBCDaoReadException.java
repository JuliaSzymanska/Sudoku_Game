package org.grupa5.exceptions;

public class JDBCDaoReadException extends SudokuException {
    public JDBCDaoReadException(String s) {
        super(s);
    }

    public JDBCDaoReadException(String s, Exception e) {
        super(s, e);
    }
}
