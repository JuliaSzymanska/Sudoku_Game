package org.grupa5.sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BacktrackingSudokuRemover implements SudokuRemover{

    // TODO: nie wiem czy tego wszystkiego potrzebuje
    private List<SudokuField> allFields = new ArrayList<SudokuField>();
    private SudokuBoard sudokuBoard;
    private int numOfFieldsToRemove;
    private List<List<SudokuField>> copyBoard;
    private int curreentNumOfPossibleBoards;

    // TODO: nie wiem czy w funkcji remove nie wystarczy tego podać i mądrze to zrobić
    public BacktrackingSudokuRemover(SudokuBoard board, int numOfFieldsToRemove) {
        this.sudokuBoard = board;
        this.numOfFieldsToRemove = numOfFieldsToRemove;
        this.copyBoard = board.getBoard();
        for(List<SudokuField> var : this.copyBoard) {
            allFields.addAll(var);
        }
        Collections.shuffle(this.allFields);
        remove();

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
        solveSudoku(this.sudokuBoard);
        System.out.println(curreentNumOfPossibleBoards);
    }

    // TODO: to jest kopia z solvera poza miejscem gddzie jest drugie todo
    private boolean solveSudoku(SudokuBoard board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board.get(row, col) == 0) {
                    List<Integer> range =
                            IntStream.range(1, 10).boxed().collect(Collectors.toList());
                    Collections.shuffle(range);
                    for (int number : range) {
                        board.set(row, col, number);
                        if (board.get(row, col) == number) {
                            if (this.solveSudoku(board)) {
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
        this.curreentNumOfPossibleBoards ++;
        return false;
    }

    private void setOriginalBoardToCopy() {

    }

//    public static void main(String[] args) {
//        SudokuBoard board = new SudokuBoard();
//        board.solveGame();
//        board.resetField(5, 5);
//        board.resetField(5, 4);
//        board.resetField(5, 3);
//        board.resetField(5, 2);
//        board.resetField(5, 1);
//        board.resetField(5, 0);
//        board.resetField(5, 6);
//        board.resetField(5, 7);
//        board.resetField(5, 8);
//        board.resetField(4, 5);
//        board.resetField(4, 4);
//        board.resetField(4, 3);
//        board.resetField(4, 2);
//        board.resetField(4, 1);
//        board.resetField(4, 0);
//        board.resetField(4, 6);
//        board.resetField(4, 7);
//        board.resetField(4, 8);
//        System.out.println(board);
//        SudokuRemover remover = new BacktrackingSudokuRemover(board, 0);
//    }
}
