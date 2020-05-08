package org.grupa5.dao;

public class ReadException extends Exception {
    public ReadException(String errorMessage, Exception exception) {
        super(errorMessage, exception);
    }

    public ReadException(String errorMessage) {
        super(errorMessage);
    }
}
