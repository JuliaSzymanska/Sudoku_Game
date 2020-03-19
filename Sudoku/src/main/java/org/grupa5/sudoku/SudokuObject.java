package org.grupa5.sudoku;

import java.util.ArrayList;
import java.util.List;

/**
 * This class can represent three types of objects in sudoku:
 * Row,
 * Column,
 * Box.
 */

public class SudokuObject {

    private SudokuField[] object;

    SudokuObject(SudokuField[] table) {
        this.object = table;
    }

    /**
     * Verifies whether the structure is valid.
     * @return False if the structure has duplicate numbers otherwise return True.
     */

    public boolean verify() {
        List<Integer> list = new ArrayList<Integer>();
        for (SudokuField x : object) {
            if (list.contains(x.getFieldValue())) {
                return false;
            }
            list.add(x.getFieldValue());
        }
        return true;
    }
}