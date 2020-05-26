package org.grupa5.exceptions;

public class DaoWriteException extends DaoException{

    public DaoWriteException(String s) {
        super(s);
    }

    public DaoWriteException(String s, Exception e) {
        super(s, e);
    }

    public DaoWriteException(Exception e) {
        super(defaultMsgKey, e);
    }

    /**
     * Throws Exception with the message coded by key "from0to8".
     */
    public DaoWriteException() {
        super(defaultMsgKey);
    }
}
