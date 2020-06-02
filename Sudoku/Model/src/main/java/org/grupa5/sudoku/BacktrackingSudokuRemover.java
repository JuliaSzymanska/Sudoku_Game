package org.grupa5.sudoku;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BacktrackingSudokuRemover implements SudokuRemover {

    private List<Integer> allFields;
    private SudokuBoard sudokuBoard;
    private int numOfFieldsToRemove;
    private SudokuBoard copyBoard;
    private int curreentNumOfPossibleBoards;

    /**
     * Removes number of fileds in board.
     *
     * @param board               - board
     * @param numOfFieldsToRemove - number of fileds to remove
     */

    public BacktrackingSudokuRemover(SudokuBoard board, int numOfFieldsToRemove) {
        this.sudokuBoard = board;
        this.numOfFieldsToRemove = numOfFieldsToRemove;
        this.copyBoard = this.sudokuBoard.clone();
        allFields = IntStream.range(0, 80).boxed().collect(Collectors.toList());
        Collections.shuffle(allFields);
        remove();
    }

    @Override
    public void remove() {
        int counter = 0;
        for (Integer i : allFields) {
            if (counter >= numOfFieldsToRemove) {
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
            } else {
                counter++;
            }
        }
        this.setOriginalBoardToCopy();
    }

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
        this.curreentNumOfPossibleBoards++;
        return false;
    }

    private void setOriginalBoardToCopy() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (this.copyBoard.get(row, col) == 0) {
                    this.sudokuBoard.resetField(row, col);
                    this.sudokuBoard.getField(row, col).setEditable(true);
                }
            }
        }
    }
}
