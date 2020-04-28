package org.grupa5.sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BacktrackingSudokuRemover implements SudokuRemover {

    // TODO: nie wiem czy tego wszystkiego potrzebuje
    private List<Integer> allFields = new ArrayList<Integer>();
    private SudokuBoard sudokuBoard;
    private int numOfFieldsToRemove;
    //    private List<List<SudokuField>> copyBoard;
    private SudokuBoard copyBoard;
    private int curreentNumOfPossibleBoards;

    // TODO: nie wiem czy w funkcji remove nie wystarczy tego podać i mądrze to zrobić
    public BacktrackingSudokuRemover(SudokuBoard board, int numOfFieldsToRemove) {
        try {
            this.sudokuBoard = board;
            this.numOfFieldsToRemove = numOfFieldsToRemove;
            this.copyBoard = this.sudokuBoard.clone();
            allFields = IntStream.range(0, 80).boxed().collect(Collectors.toList());
            Collections.shuffle(allFields);
            remove();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

    }

    //TODO : totalny work in progress.
    // zamierzenie jest takie że podajesz ile chcesz usunąć pól tak
    // więc przechodzisz przez listę private List<SudokuField> allFields = new ArrayList<SudokuField>();
    // Lista posortowana
    // no i zerujesz kolejne pola w w liscie
    // sprawdzasz ile jest rozwiązan
    // jesli wiecej niz 1 to cofasz to i przechodzisz do następnego miejsca w liscie
    // az usuniesz tyle pól ile chcesz usunąć
    // Wydaje się już teraz proste pod założeniem że to co tam mam ten 'solve sudoku' to działq
    @Override
    public void remove() {
        int counter = 0;
        for (Integer i : allFields) {
            if(counter >= numOfFieldsToRemove) {
                this.setOriginalBoardToCopy();
                return;
            }
            int row = i / 9;
            int col = i % 9;
            curreentNumOfPossibleBoards = 0;
            int temp = copyBoard.get(row, col);
            copyBoard.resetField(row, col);
            checkNumbOfCombinations(this.copyBoard);
            if (curreentNumOfPossibleBoards > 1) {
                copyBoard.set(row, col, temp);
            } else{
                counter++;
            }
        }
        this.setOriginalBoardToCopy();
    }

    // TODO: to jest kopia z solvera poza miejscem gddzie jest drugie todo
    private boolean checkNumbOfCombinations(SudokuBoard board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board.get(row, col) == 0) {
                    List<Integer> range =
                            IntStream.range(1, 10).boxed().collect(Collectors.toList());
                    Collections.shuffle(range);
                    for (int number : range) {
                        board.set(row, col, number);
                        if (board.get(row, col) == number) {
                            if (this.checkNumbOfCombinations(board)) {
                                return true;
                            } else {
                                board.set(row, col, 0);
                            }
                        }
                    }
                    return false;
                }
            }
        }
        // TODO: tutaj zmieana z true na false. WYDAJE MI SIĘ ŻE DZIALA
        //  NIE WIEM CZY TO NA PRAWDE DZIALA
        this.curreentNumOfPossibleBoards++;
        return false;
    }

    private void setOriginalBoardToCopy() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (this.copyBoard.get(row, col) == 0) {
                    this.sudokuBoard.resetField(row, col);
                }
            }
        }
    }

//    public static void main(String[] args) throws CloneNotSupportedException {
//        SudokuBoard board = new SudokuBoard();
//        board.solveGame();
////        board.resetField(5, 5);
////        board.resetField(5, 4);
////        board.resetField(5, 3);
////        board.resetField(5, 2);
////        board.resetField(5, 1);
////        board.resetField(5, 0);
////        board.resetField(5, 6);
////        board.resetField(5, 7);
////        board.resetField(5, 8);
////        board.resetField(4, 5);
////        board.resetField(4, 4);
////        board.resetField(4, 3);
////        board.resetField(4, 2);
////        board.resetField(4, 1);
////        board.resetField(4, 0);
////        board.resetField(4, 6);
////        board.resetField(4, 7);
////        board.resetField(4, 8);
////        board.resetField(3, 5);
////        board.resetField(3, 4);
////        board.resetField(3, 3);
////        board.resetField(3, 2);
////        board.resetField(3, 1);
////        board.resetField(3, 0);
////        board.resetField(3, 6);
////        board.resetField(3, 7);
////        board.resetField(3, 8);
//
////        board.set(0,0,7);
////        board.set(0,2,5);
////        board.set(1,3,7);
////        board.set(1,5,2);
////        board.set(1,6,5);
////        board.set(1,7,9);
////        board.set(2,1,8);
////        board.set(2,6,3);
////        board.set(2,8,7);
////        board.set(3,0,1);
////        board.set(3,4,5);
////        board.set(3,6,4);
////        board.set(3,7,2);
////        board.set(4,1,6);
////        board.set(4,3,2);
////        board.set(4,4,7);
////        board.set(4,5,8);
////        board.set(4,7,3);
////        board.set(5,1,2);
////        board.set(5,2,9);
////        board.set(5,4,6);
////        board.set(5,8,8);
////        board.set(6,0,8);
////        board.set(6,2,7);
////        board.set(6,7,1);
////        board.set(7,1,4);
////        board.set(7,2,3);
////        board.set(7,3,6);
////        board.set(7,5,5);
////        board.set(8,6,9);
////        board.set(8,8,5);
//        System.out.println(board);
//        SudokuRemover remover = new BacktrackingSudokuRemover(board, 50);
//        System.out.println(board);
//    }
}
