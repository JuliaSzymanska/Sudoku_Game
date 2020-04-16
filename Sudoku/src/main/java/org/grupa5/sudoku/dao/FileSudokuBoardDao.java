package org.grupa5.sudoku.dao;

import org.grupa5.sudoku.SudokuBoard;

public class FileSudokuBoardDao implements Dao<SudokuBoard> {

    private String fileName;

    public FileSudokuBoardDao(String fileName) {
        this.fileName = fileName;
    }

    
    @Override
    public void write(SudokuBoard t) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public SudokuBoard read() {
        // TODO Auto-generated method stub
        return null;
    }


}