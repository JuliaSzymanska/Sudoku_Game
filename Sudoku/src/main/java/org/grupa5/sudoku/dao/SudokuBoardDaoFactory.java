package org.grupa5.sudoku.dao;

import org.grupa5.sudoku.SudokuBoard;

public class SudokuBoardDaoFactory {
    public Dao<SudokuBoard> getFileDao(String fileName) {
        return new FileSudokuBoardDao(fileName);
    }
}
