package org.grupa5.sudoku.dao;

import org.grupa5.sudoku.SudokuBoard;
import org.grupa5.sudoku.SudokuField;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoardDaoTest {

    private final String FILE_PATH = "file.txt";
    private final String FAILURE_FILE_PATH = "/";
    private SudokuBoardDaoFactory fabryka = new SudokuBoardDaoFactory();
    private Dao<SudokuBoard> dao = fabryka.getFileDao(FILE_PATH);
    private Dao<SudokuBoard> failureDao = fabryka.getFileDao(FAILURE_FILE_PATH);
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
        FileUtils.deleteQuietly(new File(FILE_PATH));
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
    @Test
    void sudokuBoardWriteExceptionTest() {
//        File f = new File(FAILURE_FILE_PATH);
//        if(!f.exists() || f.isDirectory()) {
//            try {
//                failureDao.write(board);
//            } catch (Exception e) {
//                fail("Should not have thrown any exception");
//            }
//        }
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));
        failureDao.write(board);
        final String standardOutput = myOut.toString();
        assertEquals("Wyjatek IO", standardOutput);
    }

    @Test
    void sudokuBoardReadExceptionTest() {
//        File f = new File(FAILURE_FILE_PATH);
//        if(!f.exists() || f.isDirectory()) {
//            try {
//                failureDao.read();
//            } catch (Exception e) {
//                fail("Should not have thrown any exception");
//            }
//        }
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));
        failureDao.read();
        final String standardOutput = myOut.toString();
        assertEquals("Wyjatek IO", standardOutput);
    }


    // TODO: Zostawił bym to bo im więcej czytam o tym wyjątku tym mniej wiem jak to sprawdzić
    @Test
    void sudokuBoardReadClassNotFoundTest() {
//        try (
//                FileOutputStream fileOut = new FileOutputStream(FILE_PATH);
//                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)
//        ) {
////            List<SudokuField> list = Arrays.asList(new SudokuField[9]);
////            for (int i = 0; i < 9; i++) {
////                list.set(i, new SudokuField(i));
////            }
////            int l = list.size();
////            SudokuObject obj = new SudokuObject(list);
//            SudokuField field = new SudokuField(9);
//            objectOut.writeObject(field);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        dao.read();
    }
}