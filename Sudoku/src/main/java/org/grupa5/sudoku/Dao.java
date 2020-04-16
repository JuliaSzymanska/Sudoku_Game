package org.grupa5.sudoku;

public interface Dao <T> {
    T read();
    void write(T t);
}