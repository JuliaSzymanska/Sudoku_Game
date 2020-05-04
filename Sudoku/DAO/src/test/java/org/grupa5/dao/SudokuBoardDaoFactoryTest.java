package org.grupa5.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class SudokuBoardDaoFactoryTest {

    @Test
    void notNullTest () {
        Assertions.assertEquals(SudokuBoardDaoFactory.getFileDao("FILENAME").getClass(), FileSudokuBoardDao.class);
    }


    // TODO: bez tego jacoco się pluje : )
    @Test
    void defaultConstructorTest(){
        new SudokuBoardDaoFactory();
    }
}
