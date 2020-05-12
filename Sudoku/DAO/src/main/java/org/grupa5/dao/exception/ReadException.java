package org.grupa5.dao.exception;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

// TODO: 12.05.2020 niestety jeszcze internacjonalizacja ehhh
public class ReadException extends Exception implements Serializable {

    ResourceBundle boundle = ResourceBundle.getBundle("DAOException", new Locale("en", "EN"));

    // TODO: 12.05.2020 zrobic serializacje
    private static final long serialVersionUID = 1L;

    public ReadException(String errorMessage, Exception exception) {
        super(errorMessage, exception);
    }

    public ReadException(String errorMessage) {
        super(errorMessage);
    }

    // TODO: 12.05.2020 zrobic to dla wszysktkich klas z wyjatkami
    //  https://stackoverflow.com/a/24989341
    @Override
    public String getLocalizedMessage() {
        return boundle.getString(getMessage());
    }
}
