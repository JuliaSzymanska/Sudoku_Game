package org.grupa5.sudoku.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
        } catch (IOException e) {
            // TODO: tutaj coś madrego
            e.printStackTrace();
        }
    }

    @Override
    public SudokuBoard read() {
        SudokuBoard sudokuBoard = null;
        try (
                FileInputStream fileIn = new FileInputStream(this.fileName);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn)
        ) {
            sudokuBoard = (SudokuBoard) objectIn.readObject();
        } catch (ClassNotFoundException e) {
            // TODO: tutaj tez coś madrego
            e.printStackTrace();
        } catch (IOException e) {
            // TODO: tu też
            e.printStackTrace();
        }


        return sudokuBoard;
    }

    //    //TODO: zrobic zeby bylo dobrze, narazie nie mam pomyslu
    //    @Override
    //    protected void finalize() throws Throwable {
    //        try {
    //            System.out.println("Calling finalize() method of FinalizeMethodTest class");
    //        } catch(Throwable th){
    //            throw th;
    //        } finally {
    //            System.out.println("Calling finalize() method of Object class");
    //            super.finalize();
    //        }
    //    }

    // TODO: USUNAC MAINA!
    //    public static void main(String[] args) {
    //        SudokuBoard board = new SudokuBoard();
    //        Dao<SudokuBoard> dao = new FileSudokuBoardDao("plik.txt");
    //        board.solveGame();
    //        dao.write(board);
    //        SudokuBoard board2 = dao.read();
    //        System.out.println(board.toString());
    //        System.out.println(board2.toString());
    //    }

}