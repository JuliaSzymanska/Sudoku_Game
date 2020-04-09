package org.grupa5.sudoku;

import java.util.*;

/**
 * This class can represent three types of objects in sudoku:
 * Row,
 * Column,
 * Box.
 */

public class SudokuObject {

    private List<SudokuField> object;

    SudokuObject(List<SudokuField> table) {
        this.object = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            this.object.set(i, table.get(i));
        }
    }

    /**
     * Verifies whether the structure is valid.
     *
     * @return False if the structure has duplicate numbers otherwise return True.
     */

    public boolean verify() {
        Set<Integer> setNumbers = new HashSet<Integer>();
        for (SudokuField x : object) {
            if (!setNumbers.add(x.getFieldValue()) && x.getFieldValue() != 0)
                return false;
        }
        return true;
    }

    // TODO: Ja tak w sumie nie wiem czy to jest klasa modelu danych i czy potrzebujemy mieć w niej equals

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!(other instanceof SudokuObject)) {
            return false;
        }
        SudokuObject sudokuObject = (SudokuObject) other;
        for (int j = 0; j < 9; j++) {
            if (sudokuObject.object.get(j).getFieldValue() != this.object.get(j).getFieldValue()) {
                return false;
            }
        }
        return true;
    }
}