package org.grupa5.dao;

public class WriteException extends Exception {
    public WriteException(String errorMessage) {
        super(errorMessage);
    }
}