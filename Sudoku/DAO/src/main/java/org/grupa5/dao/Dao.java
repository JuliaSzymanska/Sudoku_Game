package org.grupa5.dao;

import org.grupa5.exceptions.FileDaoReadException;
import org.grupa5.exceptions.FileDaoWriteException;
import org.grupa5.exceptions.JDBCDaoReadException;
import org.grupa5.exceptions.JDBCDaoWriteException;

public interface Dao<T> extends AutoCloseable {
    T read() throws JDBCDaoReadException, FileDaoReadException;
    // TODO: 19.05.2020 Ja nie wiem jak legitne jest to że to rzuca ten wyjatek ale niech bedzie xD

    void write(T t) throws FileDaoWriteException, JDBCDaoWriteException;
}