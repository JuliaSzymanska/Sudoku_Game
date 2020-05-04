package org.grupa5.dao;

import java.io.IOException;

public interface Dao<T> {
    T read() throws ReadException;

    void write(T t) throws WriteException;
}