package org.grupa5.sudoku.dao;

import org.apache.commons.lang3.builder.ToStringExclude;
import org.grupa5.sudoku.SudokuBoard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.List;

import com.jparams.verifier.tostring.ToStringVerifier;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoardDaoTest {

    private final String FILE_PATH = "file.txt";

    private SudokuBoardDaoFactory fabryka = new SudokuBoardDaoFactory();
    private Dao<SudokuBoard> dao = fabryka.getFileDao(FILE_PATH);
    private Dao<SudokuBoard> failureDao = fabryka.getFileDao("/");
    private SudokuBoard board = new SudokuBoard();
    private SudokuBoard board2 = null;

    @Test
    void sudokuBoardWriteNotSolvedTest() {
        dao.write(board);
        board2 = dao.read();
        Assertions.assertEquals(board, board2);
    }

    @Test
    void sudokuBoardWriteSolvedTest() {
        board.solveGame();
        dao.write(board);
        board2 = dao.read();
        Assertions.assertEquals(board, board2);
    }
    // TODO: Xd
    // TODO: nie wiem co z tym fantem zrobić.
    //  mamy te catch blocki
    //  które nie bardzo wiem jak ztestować
    //  generalnie to chce do tch catchy jeszcze coś dodac co by moglo pozwolić na ładne ztestowanei tego
    //  myslalem żeby dodać jakiś nasz wyjątek który by był rzucany jak się nie uda np otworzyć pliku
    //  i wtedy w gotowej grze taki wyjątek by się łapało i by był komnikat
    //  "no zły plik wybrałeś byczku, wybierz inny"
    //  ale narazie to nie wiem jeszcze
    //  widzialem cos takiego https://stackoverflow.com/questions/42508323/junit-for-both-try-and-catch-block-coverage
    //  do testowania catch blocków ale mi się to nie podoba.
    //  zostawiam nie zstestowane narazie
//    @Test
//    void sudokuBoardWriteExceptionTest() {
//        failureDao.write(board);
//    }

}