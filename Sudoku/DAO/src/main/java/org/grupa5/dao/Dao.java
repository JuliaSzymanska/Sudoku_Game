package org.grupa5.dao;

import org.grupa5.dao.exception.ReadException;
import org.grupa5.dao.exception.WriteException;

public interface Dao<T> extends AutoCloseable {
    T read() throws ReadException;

    void write(T t) throws WriteException;
}