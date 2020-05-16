package org.grupa5.sudoku.exceptions;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

public class SetException extends Exception implements Serializable {

    ResourceBundle boundle = ResourceBundle.getBundle("BoardException");

    private static final long serialVersionUID = 645235;

    // TODO: 12.05.2020 przekazywanie parametru jezyka

    public SetException(String s) {
        super(s);
    }

    public SetException(String s, Exception e) {
        super(s, e);
    }

    @Override
    public String getLocalizedMessage() {
        return boundle.getString(getMessage());
    }
}
