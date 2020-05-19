package org.grupa5.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.grupa5.exceptions.FileDaoReadException;
import org.grupa5.exceptions.FileDaoWriteException;
import org.grupa5.sudoku.SudokuBoard;

class FileSudokuBoardDao implements Dao<SudokuBoard> {

    private String fileName;

    FileSudokuBoardDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void write(SudokuBoard sudokuBoard) throws FileDaoWriteException {
        try (
                FileOutputStream fileOut = new FileOutputStream(this.fileName);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)
        ) {
            objectOut.writeObject(sudokuBoard);
        } catch (IOException e) {
            throw new FileDaoWriteException("fileWrite", e);
        }
    }

    @Override
    public SudokuBoard read() throws FileDaoReadException {
        try (
                FileInputStream fileIn = new FileInputStream(this.fileName);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn)
        ) {
            return (SudokuBoard) objectIn.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new FileDaoReadException("fileRead", e);
        }

    }

    @Override
    public void close() {

    }
}
