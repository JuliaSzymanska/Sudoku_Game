package org.grupa5.exceptions;

public class GetException extends SudokuException {


    public GetException(String s) {
        super(s);
    }

    public GetException(String s, Exception e) {
        super(s, e);
    }

    /**
     *  Throws Exception with the message coded by key "from0to8".
     * @param e Previous exception in chain.
     */
    public GetException(Exception e) {
        super(defaultMsgKey, e);
    }

    /**
     *  Throws Exception with the message coded by key "from0to8".
     */
    public GetException() {
        super(defaultMsgKey);
    }
}
