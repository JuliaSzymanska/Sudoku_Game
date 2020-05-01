package org.grupa5.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class SudokuBoardDaoFactoryTest {

    @Test
    void notNullTest () {
        Assertions.assertEquals(SudokuBoardDaoFactory.getFileDao("FILENAME").getClass(), FileSudokuBoardDao.class);
    }


    // TODO: znowu się pluje o te testy
//    @Test
//    void defaultConstructorTest(){
//        new SudokuBoardDaoFactory();
//    }
}
