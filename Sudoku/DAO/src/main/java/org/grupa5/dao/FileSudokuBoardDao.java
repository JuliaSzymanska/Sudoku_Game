package org.grupa5.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.grupa5.dao.exception.ReadException;
import org.grupa5.dao.exception.WriteException;
import org.grupa5.sudoku.SudokuBoard;

class FileSudokuBoardDao implements Dao<SudokuBoard> {

    private String fileName;

    FileSudokuBoardDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void write(SudokuBoard sudokuBoard) throws WriteException {
        try (
                FileOutputStream fileOut = new FileOutputStream(this.fileName);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)
        ) {
            objectOut.writeObject(sudokuBoard);
        } catch (IOException e) {
            throw new WriteException("SudokuBoard Dao write encountered an Issue", e);
        }
    }

    @Override
    public SudokuBoard read() throws ReadException {
        try (
                FileInputStream fileIn = new FileInputStream(this.fileName);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn)
        ) {
            return (SudokuBoard) objectIn.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new ReadException("SudokuBoard Dao Read encountered an Issue", e);
        }

    }

    @Override
    public void close() {

    }
}
