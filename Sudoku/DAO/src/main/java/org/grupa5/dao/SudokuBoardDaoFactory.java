package org.grupa5.dao;

import org.grupa5.exceptions.DaoException;
import org.grupa5.sudoku.SudokuBoard;

public class SudokuBoardDaoFactory implements AutoCloseable {
    public static Dao<SudokuBoard> getFileDao(String fileName) {
        return new FileSudokuBoardDao(fileName);
    }

    public static Dao<SudokuBoard> getJdbcDao(String fileName, String url) throws DaoException {
        return new JdbcSudokuBoardDao(fileName, url);
    }

    public static Dao<SudokuBoard> getJdbcDao(String fileName) throws DaoException {
        return new JdbcSudokuBoardDao(fileName);
    }

    private SudokuBoardDaoFactory() {

    }

    @Override
    public void close() {

    }
}
