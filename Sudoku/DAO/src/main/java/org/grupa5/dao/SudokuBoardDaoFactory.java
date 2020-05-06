package org.grupa5.dao;

import org.grupa5.sudoku.SudokuBoard;

public class SudokuBoardDaoFactory {
    public static Dao<SudokuBoard> getFileDao(String fileName) {
        return new FileSudokuBoardDao(fileName);
    }
    // TODO: 06.05.2020 prywatny konstruktor
}
