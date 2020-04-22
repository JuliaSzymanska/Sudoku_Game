package org.grupa5.sudoku.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SudokuBoardDaoFactoryTest {

    // TODO: TO ma być statyczne
    //  więc testy też musimy zmienić
    @Test
    void notNullTest () {
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        Assertions.assertTrue(factory.getFileDao("FILENAME").getClass().equals(FileSudokuBoardDao.class));
    }
}
