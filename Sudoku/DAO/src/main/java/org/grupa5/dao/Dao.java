package org.grupa5.dao;

import org.grupa5.exceptions.FileDaoReadException;
import org.grupa5.exceptions.FileDaoWriteException;

public interface Dao<T> extends AutoCloseable {
    T read() throws FileDaoReadException;

    void write(T t) throws FileDaoWriteException;
}