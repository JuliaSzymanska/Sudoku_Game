package org.grupa5.dao;

public interface Dao<T> {
    T read();

    void write(T t);
}