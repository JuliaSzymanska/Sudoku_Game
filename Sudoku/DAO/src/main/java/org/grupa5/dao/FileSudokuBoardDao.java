package org.grupa5.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.grupa5.sudoku.SudokuBoard;

// TODO: ma implementowac AutoCloseable
public class FileSudokuBoardDao implements Dao<SudokuBoard> {

    private String fileName;

    public FileSudokuBoardDao(String fileName) {
        this.fileName = fileName;
    }


    // TODO: aktualnie w catch blockach jednynie wyprintowywujemy że doszło do wyjątku
    //  prawdopodobnie znajdzie się tutaj nasz customowy wyjątek który będziemy potem
    //  używać w grze do wykrywania czy nie udało się zapisać / odczytać gry aby poinformować gracza
    @Override
    public void write(SudokuBoard sudokuBoard) throws WriteException {
        try (
                FileOutputStream fileOut = new FileOutputStream(this.fileName);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)
        ) {
            objectOut.writeObject(sudokuBoard);
        } catch (IOException e) {
            throw new WriteException("SudokuBoard Dao write encountered an Issue");
        }
    }

    @Override
    public SudokuBoard read() throws ReadException {
        SudokuBoard sudokuBoard = null;
        try (
                FileInputStream fileIn = new FileInputStream(this.fileName);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn)
        ) {
            sudokuBoard = (SudokuBoard) objectIn.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new ReadException("SudokuBoard Dao Read encountered an Issue");
        }
        return sudokuBoard;
    }

}
