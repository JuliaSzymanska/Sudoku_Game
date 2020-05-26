package org.grupa5.exceptions;

public class DaoReadException extends DaoException {

    public DaoReadException(String s) {
        super(s);
    }

    public DaoReadException(String s, Exception e) {
        super(s, e);
    }

    public DaoReadException(Exception e) {
        super(defaultMsgKey, e);
    }

    /**
     * Throws Exception with the message coded by key "from0to8".
     */
    public DaoReadException() {
        super(defaultMsgKey);
    }
}
