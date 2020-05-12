package org.grupa5.sudoku.exceptions;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

public class GetException extends Exception implements Serializable {

    ResourceBundle boundle = ResourceBundle.getBundle("BoardException", new Locale("en", "EN"));

    private static final long serialVersionUID = 97834245;

    // TODO: 12.05.2020 przekazywanie parametru jezyka

    public GetException(String s) {
        super(s);
    }

    public GetException(String s, Exception e) {
        super(s, e);
    }

    @Override
    public String getLocalizedMessage() {
        return boundle.getString(getMessage());
    }
}
