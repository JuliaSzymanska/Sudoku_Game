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


    // TODO: aktualnie w catch blockach jednynie wyprintowywujemy że doszło do wyjątku
    //  prawdopodobnie znajdzie się tutaj nasz customowy wyjątek który będziemy potem
    //  używać w grze do wykrywania czy nie udało się zapisać / odczytać gry aby poinformować gracza
    @Override
    public void write(SudokuBoard sudokuBoard) {
        try (
                FileOutputStream fileOut = new FileOutputStream(this.fileName);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)
        ) {
            objectOut.writeObject(sudokuBoard);
        } catch (IOException e) {
            System.out.print("Wyjatek IO");
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
            System.out.print("Not found");
        }  catch (IOException e) {
            System.out.print("Wyjatek IO");
        }
        return sudokuBoard;
    }

}
