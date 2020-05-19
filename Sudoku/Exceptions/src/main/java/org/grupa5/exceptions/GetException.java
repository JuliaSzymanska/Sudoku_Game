package org.grupa5.exceptions;

import java.io.Serializable;
import java.util.ResourceBundle;

public class GetException extends Exception implements Serializable {

    ResourceBundle bundle = ResourceBundle.getBundle("BoardException");

    private static final long serialVersionUID = 97834245;

    // TODO: 16.05.2020 przetestowac czy ta klasa tez sie poprawnie internacjonalizuje

    public GetException(String s) {
        super(s);
    }

    public GetException(String s, Exception e) {
        super(s, e);
    }

    @Override
    public String getLocalizedMessage() {
        return bundle.getString(getMessage());
    }
}
