package org.grupa5.dao.exception;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

// TODO: 16.05.2020 przetestowac czy ta klasa tez sie poprawnie internacjonalizuje

public class WriteException extends Exception implements Serializable {

    ResourceBundle boundle = ResourceBundle.getBundle("DAOException", new Locale("en", "EN"));

    private static final long serialVersionUID = 1253456;

    public WriteException(String errorMessage, Exception exception) {
        super(errorMessage, exception);
    }

    public WriteException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String getLocalizedMessage() {
        return boundle.getString(getMessage());
    }
}