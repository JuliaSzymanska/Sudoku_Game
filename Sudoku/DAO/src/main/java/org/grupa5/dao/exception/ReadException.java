package org.grupa5.dao.exception;

import java.util.ResourceBundle;


public class ReadException extends Exception {

    ResourceBundle bundle = ResourceBundle.getBundle("DAOException");

    public ReadException(String errorMessage, Exception exception) {
        super(errorMessage, exception);
    }

    public ReadException(String errorMessage) {
        super(errorMessage);
    }
    
    @Override
    public String getLocalizedMessage() {
        return bundle.getString(getMessage());
    }
}
