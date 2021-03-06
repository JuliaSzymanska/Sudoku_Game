package org.grupa5.dao;

import org.grupa5.exceptions.DaoReadException;
import org.grupa5.exceptions.DaoWriteException;
import org.grupa5.sudoku.SudokuBoard;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JdbcDaoTest {

    private SudokuBoard board = new SudokuBoard();
    private static final String url = "jdbc:derby:memory:myDb;create=true";

    @Test
    void localizedMessagesTestReadDBDao() {
        try (Dao<SudokuBoard> failureDaoDB = SudokuBoardDaoFactory.getJdbcDao("STR'ING", url)) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void localizedMessagesTestWriteDBDao() {
        try (Dao<SudokuBoard> failureDaoDB = SudokuBoardDaoFactory.getJdbcDao("STRI'NG", url)) {
            Locale.setDefault(new Locale("en", "en"));
            DaoWriteException exceptionEN = assertThrows(
                    DaoWriteException.class,
                    () -> failureDaoDB.write(this.board)
            );
            assertEquals(exceptionEN.getLocalizedMessage(), "SudokuBoard DBDao write encountered an Issue");

            Locale.setDefault(new Locale("pl", "pl"));
            DaoWriteException exceptionPL = assertThrows(
                    DaoWriteException.class,
                    () -> failureDaoDB.write(this.board)
            );
            assertEquals(exceptionPL.getLocalizedMessage(), "Blad przy zapisie do Planszy do bazy danych (DBDao)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
