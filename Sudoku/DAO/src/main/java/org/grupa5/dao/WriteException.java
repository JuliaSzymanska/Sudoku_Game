package org.grupa5.dao;

import java.io.IOException;

public class WriteException extends Exception {
    public WriteException(String errorMessage, Exception exception) {
        super(errorMessage, exception);
    }

    public WriteException(String errorMessage) {
        super(errorMessage);
    }
}