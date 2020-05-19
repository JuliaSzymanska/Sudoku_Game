package org.grupa5.dao;

import org.grupa5.exceptions.FileDaoReadException;
import org.grupa5.exceptions.FileDaoWriteException;
import org.grupa5.exceptions.JDBCDaoReadException;
import org.grupa5.exceptions.JDBCDaoWriteException;
import org.grupa5.sudoku.SudokuBoard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Locale;

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
    void sudokuBoardWriteNotSolvedTest() throws FileDaoWriteException, FileDaoReadException, JDBCDaoWriteException, JDBCDaoReadException {
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
    void sudokuBoardWriteSolvedTest() throws FileDaoWriteException, FileDaoReadException, JDBCDaoWriteException, JDBCDaoReadException {
        board.solveGame();
        dao.write(board);
        board2 = dao.read();
        Assertions.assertEquals(board, board2);
        FileUtils.deleteQuietly(new File(FILE_PATH));
    }

    @Test
    void sudokuBoardWriteExceptionTest() {
        assertThrows(FileDaoWriteException.class, () -> {
            failureDao.write(board);
        });
    }

    @Test
    void sudokuBoardReadExceptionTest() {
        assertThrows(FileDaoReadException.class, () -> {
            failureDao.read();
        });
    }

    @Test
    void localizedMessagesTestReadFileDao() {
        Locale.setDefault(new Locale("en", "en"));
        try {
            failureDao.read();
        } catch (FileDaoReadException | JDBCDaoReadException e) {
            assertEquals(e.getLocalizedMessage(), "SudokuBoard FileDao read encountered an Issue");
        }
        Locale.setDefault(new Locale("pl", "pl"));
        try {
            failureDao.read();
        } catch (FileDaoReadException | JDBCDaoReadException e) {
            assertEquals(e.getLocalizedMessage(), "SudokuBoard Dao odczyt z pliku napotkal problem");
        }
    }

    @Test
    void localizedMessagesTestWriteFileDao() {
        Locale.setDefault(new Locale("en", "en"));
        try {
            failureDao.write(this.board);
        } catch (JDBCDaoWriteException | FileDaoWriteException e) {
            assertEquals(e.getLocalizedMessage(), "SudokuBoard FileDao write encountered an Issue");
        }
        Locale.setDefault(new Locale("pl", "pl"));
        try {
            failureDao.write(this.board);
        } catch (JDBCDaoWriteException | FileDaoWriteException e) {
            assertEquals(e.getLocalizedMessage(), "SudokuBoard Dao zapis do pliku napotkal problem");
        }
    }

    @Test
    void localizedMessagesTestReadDBDao() {
        Locale.setDefault(new Locale("en", "en"));
        Dao<SudokuBoard> failureDaoDB = SudokuBoardDaoFactory.getJdbcDao("STRING");
        try {
            failureDaoDB.read();
        } catch (FileDaoReadException | JDBCDaoReadException e) {
            assertEquals(e.getLocalizedMessage(),"SudokuBoard DBDao read encountered an Issue");
        }
        Locale.setDefault(new Locale("pl", "pl"));
        try {
            failureDaoDB.read();
        } catch (FileDaoReadException | JDBCDaoReadException e) {
            assertEquals(e.getLocalizedMessage(), "SudokuBoard Dao odczyt z bazy danych napotkal problem");
        }
    }

    @Test
    void localizedMessagesTestWriteDBDao() {
        Locale.setDefault(new Locale("en", "en"));
        Dao<SudokuBoard> failureDaoDB = SudokuBoardDaoFactory.getJdbcDao("STRING");
        try {
            failureDaoDB.write(this.board);
        } catch (JDBCDaoWriteException | FileDaoWriteException e) {
            assertEquals(e.getLocalizedMessage(), "SudokuBoard DBDao write encountered an Issue");
        }
        Locale.setDefault(new Locale("pl", "pl"));
        try {
            failureDaoDB.write(this.board);
        } catch (JDBCDaoWriteException | FileDaoWriteException e) {
            assertEquals(e.getLocalizedMessage(), "Blad przy zapisie do Planszy do bazy danych (DBDao)");
        }
    }
}