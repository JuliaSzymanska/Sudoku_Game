package org.grupa5.exceptions;

import java.io.Serializable;
import java.util.ResourceBundle;

public class SetException extends SudokuException {

    public SetException(String s) {
        super(s);
    }

    public SetException(String s, Exception e) {
        super(s, e);
    }
}
