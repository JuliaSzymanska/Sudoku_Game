package org.grupa5.dao;

import org.grupa5.exceptions.DaoException;
import org.grupa5.exceptions.DaoReadException;
import org.grupa5.exceptions.DaoWriteException;

public interface Dao<T> extends AutoCloseable {
    T read() throws DaoReadException;

    void write(T t) throws DaoWriteException;

    @Override
    void close() throws DaoException;
}