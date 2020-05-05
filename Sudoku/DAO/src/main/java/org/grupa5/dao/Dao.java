package org.grupa5.dao;

public interface Dao<T> extends AutoCloseable {
    T read() throws ReadException;

    void write(T t) throws WriteException;
}