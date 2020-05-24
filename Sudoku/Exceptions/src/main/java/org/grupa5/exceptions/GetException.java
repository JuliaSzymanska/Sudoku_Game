package org.grupa5.exceptions;

public class GetException extends SudokuException {

    public GetException(String s) {
        super(s);
    }

    public GetException(String s, Exception e) {
        super(s, e);
    }
}
