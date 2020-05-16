package org.grupa5.dao.exception;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;
// TODO: 16.05.2020 PRZETESTOWAC WYJATKI W KAZDEJ KLASIE CZY SIE INTERNACJONALIZUJA 

public class ReadException extends Exception implements Serializable {

    ResourceBundle boundle = ResourceBundle.getBundle("DAOException");

    private static final long serialVersionUID = 654323;

    public ReadException(String errorMessage, Exception exception) {
        super(errorMessage, exception);
    }

    public ReadException(String errorMessage) {
        super(errorMessage);
    }
    
    @Override
    public String getLocalizedMessage() {
        return boundle.getString(getMessage());
    }
}
