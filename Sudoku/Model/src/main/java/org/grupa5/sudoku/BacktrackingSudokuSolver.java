package org.grupa5.sudoku;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.grupa5.sudoku.exceptions.GetException;
import org.grupa5.sudoku.exceptions.SetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BacktrackingSudokuSolver implements SudokuSolver {

    private static final Logger logger = LoggerFactory.getLogger(BacktrackingSudokuSolver.class);

    /**
     * Fills the board in a random way with every usage. Starts by filling every.
     * sector in the board in a random space with a random number. The board is.
     * divided into 9 sectors 3x3 each. After this randomisation the board is solved.
     * using a backtracking algorithm. Then the board is randomly mixed.
     */

    @Override
    public void solve(SudokuBoard board) {
        try {
            this.solveSudoku(board);
        } catch (SetException | GetException e) {
            if (logger.isErrorEnabled()) {
                // TODO: 12.05.2020 Kolejne do obejrzenia w wolnym czasie
                logger.error("Exception thrown by solve, ", e);
            }
        }
    }

    /**
     * Solve sudoku filled with some numbers.
     *
     * @param board is an object of SudokuBoard class
     */

    private boolean solveSudoku(SudokuBoard board) throws SetException, GetException {
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
        return true;
    }
}
