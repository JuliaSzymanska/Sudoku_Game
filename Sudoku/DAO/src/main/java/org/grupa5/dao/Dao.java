package org.grupa5.dao;

import org.grupa5.exceptions.ReadException;
import org.grupa5.exceptions.WriteException;

public interface Dao<T> extends AutoCloseable {
    T read() throws ReadException;

    void write(T t) throws WriteException;
}