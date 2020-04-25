package org.grupa5.sudoku.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class SudokuBoardDaoFactoryTest {

    @Test
    void notNullTest () {
        Assertions.assertTrue(SudokuBoardDaoFactory.getFileDao("FILENAME").getClass().equals(FileSudokuBoardDao.class));
    }
}
