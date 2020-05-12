package org.grupa5.dao.exception;

// TODO: 12.05.2020 niestety jeszcze internacjonalizacja ehhh
public class WriteException extends Exception {
    public WriteException(String errorMessage, Exception exception) {
        super(errorMessage, exception);
    }

    public WriteException(String errorMessage) {
        super(errorMessage);
    }

    // TODO: 12.05.2020 zrobic to dla wszysktkich klas z wyjatkami
    //  https://stackoverflow.com/a/24989341
    @Override
    public String getLocalizedMessage() {
        // to jest inaczej ale tak zostawiam narazie
        return super.getLocalizedMessage();
    }
}