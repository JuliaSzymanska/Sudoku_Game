package org.grupa5.sudoku.dao;

public interface Dao <T> {
    T read();
    void write(T t);
}