package org.grupa5.sudoku.dao;

// TODO: kwapi ma te dao w osobnym projekcie jak model czy gui u nas

public interface Dao<T> {
    T read();

    void write(T t);
}