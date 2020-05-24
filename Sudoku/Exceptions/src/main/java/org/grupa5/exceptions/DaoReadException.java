package org.grupa5.exceptions;

public class DaoReadException extends DaoException {

    public DaoReadException(String s) {
        super(s);
    }

    public DaoReadException(String s, Exception e) {
        super(s, e);
    }
}
