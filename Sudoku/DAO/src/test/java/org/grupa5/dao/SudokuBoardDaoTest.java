package org.grupa5.dao;

import org.grupa5.exceptions.DaoReadException;
import org.grupa5.exceptions.DaoWriteException;
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
    void sudokuBoardWriteNotSolvedTest() throws DaoWriteException, DaoReadException {
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
    void sudokuBoardWriteSolvedTest() throws DaoWriteException, DaoReadException {
        board.solveGame();
        dao.write(board);
        board2 = dao.read();
        Assertions.assertEquals(board, board2);
        FileUtils.deleteQuietly(new File(FILE_PATH));
    }

    @Test
    void sudokuBoardWriteExceptionTest() {
        assertThrows(DaoWriteException.class, () -> {
            failureDao.write(board);
        });
    }

    @Test
    void sudokuBoardReadExceptionTest() {
        assertThrows(DaoReadException.class, () -> {
            failureDao.read();
        });
    }

    @Test
    void localizedMessagesTestReadFileDao() {
        Locale.setDefault(new Locale("en", "en"));
        DaoReadException exceptionEN = assertThrows(
                DaoReadException.class,
                () -> {
                    failureDao.read();
                }
        );
        assertEquals(exceptionEN.getLocalizedMessage(), "SudokuBoard FileDao read encountered an Issue");

        Locale.setDefault(new Locale("pl", "pl"));
        DaoReadException exceptionPL = assertThrows(
                DaoReadException.class,
                () -> {
                    failureDao.read();
                }
        );
        assertEquals(exceptionPL.getLocalizedMessage(), "SudokuBoard Dao odczyt z pliku napotkal problem");
    }

    @Test
    void localizedMessagesTestWriteFileDao() {
        Locale.setDefault(new Locale("en", "en"));
        DaoWriteException exceptionEN = assertThrows(
                DaoWriteException.class,
                () -> {
                    failureDao.write(this.board);
                }
        );
        assertEquals(exceptionEN.getLocalizedMessage(), "SudokuBoard FileDao write encountered an Issue");

        Locale.setDefault(new Locale("pl", "pl"));
        DaoWriteException exceptionPL = assertThrows(
                DaoWriteException.class,
                () -> {
                    failureDao.write(this.board);
                }
        );
        assertEquals(exceptionPL.getLocalizedMessage(), "SudokuBoard Dao zapis do pliku napotkal problem");
    }

    @Test
    void localizedMessagesTestReadDBDao() {
        Dao<SudokuBoard> failureDaoDB = SudokuBoardDaoFactory.getJdbcDao("STR'ING");

        Locale.setDefault(new Locale("en", "en"));
        DaoReadException exceptionEN = assertThrows(
                DaoReadException.class,
                () -> {
                        failureDaoDB.read();
                }
        );
        assertEquals(exceptionEN.getLocalizedMessage(), "SudokuBoard DBDao read encountered an Issue");

        Locale.setDefault(new Locale("pl", "pl"));
        DaoReadException exceptionPL = assertThrows(
                DaoReadException.class,
                () -> {
                    failureDaoDB.read();
                }
        );
        assertEquals(exceptionPL.getLocalizedMessage(), "SudokuBoard Dao odczyt z bazy danych napotkal problem");
    }

    // TODO: 20.05.2020 wytworzyc nowa baze danych, w pamieci memroy zeby plik sie nie tworzyl, to wtedy po zakonczeniu testow ona sie usunie
    @Test
    void localizedMessagesTestWriteDBDao() {
        Dao<SudokuBoard> failureDaoDB = SudokuBoardDaoFactory.getJdbcDao("STRING");
        Locale.setDefault(new Locale("en", "en"));
        DaoWriteException exceptionEN = assertThrows(
                DaoWriteException.class,
                () -> {
                    failureDaoDB.write(this.board);
                }
        );
        assertEquals(exceptionEN.getLocalizedMessage(), "SudokuBoard DBDao write encountered an Issue");

        Locale.setDefault(new Locale("pl", "pl"));
        DaoWriteException exceptionPL = assertThrows(
                DaoWriteException.class,
                () -> {
                    failureDaoDB.write(this.board);
                }
        );
        assertEquals(exceptionPL.getLocalizedMessage(), "Blad przy zapisie do Planszy do bazy danych (DBDao)");
    }
}