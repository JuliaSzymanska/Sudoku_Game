package org.grupa5.exceptions;

import java.io.Serializable;
import java.util.ResourceBundle;

public class GetException extends SudokuException {

    public GetException(String s) {
        super(s);
    }

    public GetException(String s, Exception e) {
        super(s, e);
    }
}
