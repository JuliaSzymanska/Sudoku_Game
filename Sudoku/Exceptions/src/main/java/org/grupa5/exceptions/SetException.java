package org.grupa5.exceptions;

public class SetException extends SudokuException {


    public SetException(String s) {
        super(s);
    }

    public SetException(String s, Exception e) {
        super(s, e);
    }

    /**
     *  Throws Exception with the message coded by key "from0to8".
     * @param e Previous exception in chain.
     */
    public SetException(Exception e) {
        super(defaultMsgKey, e);
    }

    /**
     * Throws Exception with the message coded by key "from0to8".
     */
    public SetException() {
        super(defaultMsgKey);
    }
}
