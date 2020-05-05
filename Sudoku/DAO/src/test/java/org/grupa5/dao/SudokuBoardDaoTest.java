package org.grupa5.dao;

import org.grupa5.sudoku.SudokuBoard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoardDaoTest {

    private final String FILE_PATH = "file.txt";
    private final String FAILURE_FILE_PATH = "/";
    private Dao<SudokuBoard> dao = SudokuBoardDaoFactory.getFileDao(FILE_PATH);
    private Dao<SudokuBoard> failureDao = SudokuBoardDaoFactory.getFileDao(FAILURE_FILE_PATH);
    private SudokuBoard board = new SudokuBoard();
    private SudokuBoard board2 = null;

    @Test
    void failureFileTest() {
        File f = new File(FAILURE_FILE_PATH);
        Assertions.assertFalse((f.exists() && !f.isDirectory()));
    }

    @Test
    void sudokuBoardWriteNotSolvedTest() throws WriteException, ReadException {
        dao.write(board);
        board2 = dao.read();
        Assertions.assertEquals(board, board2);
        FileUtils.deleteQuietly(new File(FILE_PATH));
    }

    @Test
    void tryWithResourceTest() {
        try (
                Dao<SudokuBoard> dao2 = SudokuBoardDaoFactory.getFileDao(FILE_PATH);) {
            dao2.read();
        } catch (Exception ignore) {
        }
    }

    @Test
    void sudokuBoardWriteSolvedTest() throws WriteException, ReadException {
        board.solveGame();
        dao.write(board);
        board2 = dao.read();
        Assertions.assertEquals(board, board2);
        FileUtils.deleteQuietly(new File(FILE_PATH));
    }

    @Test
    void sudokuBoardWriteExceptionTest() {
        assertThrows(WriteException.class, () -> {
            failureDao.write(board);
        });
    }

    @Test
    void sudokuBoardReadExceptionTest() {
        assertThrows(ReadException.class, () -> {
            failureDao.read();
        });
    }
}