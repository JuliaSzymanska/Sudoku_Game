package org.grupa5.dao;

import org.grupa5.exceptions.DaoReadException;
import org.grupa5.exceptions.DaoWriteException;

public interface Dao<T> extends AutoCloseable {
    T read() throws DaoReadException, DaoReadException;
    // TODO: 19.05.2020 Ja nie wiem jak legitne jest to że to rzuca ten wyjatek ale niech bedzie xD

    void write(T t) throws DaoWriteException, DaoWriteException;
}