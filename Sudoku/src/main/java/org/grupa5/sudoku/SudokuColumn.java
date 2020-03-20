package org.grupa5.sudoku;

import java.util.List;

public class SudokuColumn extends SudokuObject {
    SudokuColumn(List<SudokuField> table) {
        super(table);
    }
    public boolean verify() {
        return super.verify();
    }
}
