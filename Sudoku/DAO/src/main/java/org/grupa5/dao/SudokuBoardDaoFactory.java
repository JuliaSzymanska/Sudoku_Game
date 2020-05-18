package org.grupa5.dao;

import org.grupa5.sudoku.SudokuBoard;

public class SudokuBoardDaoFactory {
    public static Dao<SudokuBoard> getFileDao(String fileName) {
        return new FileSudokuBoardDao(fileName);
    }

    public static Dao<SudokuBoard> getJdbcDao(String fileName) {
        return new JdbcSudokuBoardDao(fileName);
    }

    private SudokuBoardDaoFactory() {

    }
}
