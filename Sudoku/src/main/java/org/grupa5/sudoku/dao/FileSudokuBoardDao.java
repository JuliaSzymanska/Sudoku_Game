package org.grupa5.sudoku.dao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.grupa5.sudoku.SudokuBoard;

public class FileSudokuBoardDao implements Dao<SudokuBoard> {

    private String fileName;

    public FileSudokuBoardDao(String fileName) {
        this.fileName = fileName;
    }

    
    @Override
    public void write(SudokuBoard sudokuBoard) {
        try (
            FileOutputStream fileOut = new FileOutputStream(this.fileName);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)
            ) {
            objectOut.writeObject(sudokuBoard);
        }
        catch (IOException e) {
            // TODO: TO TRZEBA ZMIENIC TEN CATCH
            e.printStackTrace();
        }        
    }

    @Override
    public SudokuBoard read() {
        // TODO Auto-generated method stub
        return null;
    }


}