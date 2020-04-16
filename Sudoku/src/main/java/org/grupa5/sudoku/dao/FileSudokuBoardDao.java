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
        }
        catch (IOException e) {
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
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            // TODO: tutaj tez coś madrego
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: tu też
        }
        return sudokuBoard;
    }

    
    // TODO: USUNAC MAINA!
    public static void main(String[] args) {
        SudokuBoard board = new SudokuBoard();
        Dao dao = new FileSudokuBoardDao("plik.txt");
        board.solveGame();
        dao.write(board);
        // TODO: to chyba nie tak powinno działać!
        // Tak w zasadzie to nie wiem jak to powinno dzialac
        // Mądrych rzeczy jest ograniczona liczba 
        // Może ten factory który jest w UML w zadaniu robi że to jest fajne i śliczne?
        // Nie wiem ale się dowiem
        // Kiedyś bo narazie mi się nie chce
        SudokuBoard board2 = (SudokuBoard) dao.read(); 
        System.out.println(board.toString());
        System.out.println(board2.toString());
    }

}