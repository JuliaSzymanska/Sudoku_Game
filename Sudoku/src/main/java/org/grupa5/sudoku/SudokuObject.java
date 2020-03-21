package org.grupa5.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class can represent three types of objects in sudoku:
 * Row,
 * Column,
 * Box.
 */

public class SudokuObject {
    private List<SudokuField> object;

    SudokuObject(List<SudokuField> table) {
        this.object = Arrays.asList(
                table.get(0),
                table.get(1),
                table.get(2),
                table.get(3),
                table.get(4),
                table.get(5),
                table.get(6),
                table.get(7),
                table.get(8));
    }

    /**
     * Verifies whether the structure is valid.
     *
     * @return False if the structure has duplicate numbers otherwise return True.
     */

    protected boolean verify() {
        List<Integer> list = new ArrayList<Integer>();
        for (SudokuField x : object) {
            if (list.contains(x.getFieldValue())) {
                return false;
            }
            list.add(x.getFieldValue());
        }
        return true;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SudokuObject)) {
            return false;
        }
        SudokuObject sudokuObject = (SudokuObject) object;
        for (int j = 0; j < 9; j++) {
            if (sudokuObject.object.get(j) != this.object.get(j)) {
                return false;
            }
        }
        return true;
    }
}