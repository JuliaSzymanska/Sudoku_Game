package org.grupa5.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.grupa5.exceptions.DaoReadException;
import org.grupa5.exceptions.DaoWriteException;
import org.grupa5.sudoku.SudokuBoard;


class FileSudokuBoardDao implements Dao<SudokuBoard> {

    private String fileName;
    private static final String fileWrite = "fileWrite";
    private static final String fileRead = "fileRead";

    FileSudokuBoardDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void write(SudokuBoard sudokuBoard) throws DaoWriteException {
        try (
                FileOutputStream fileOut = new FileOutputStream(this.fileName);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)
        ) {
            objectOut.writeObject(sudokuBoard);
        } catch (IOException e) {
            throw new DaoWriteException(fileWrite, e);
        }
    }

    @Override
    public SudokuBoard read() throws DaoReadException {
        try (
                FileInputStream fileIn = new FileInputStream(this.fileName);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn)
        ) {
            return (SudokuBoard) objectIn.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new DaoReadException(fileRead, e);
        }

    }

    @Override
    public void close() {

    }
}
