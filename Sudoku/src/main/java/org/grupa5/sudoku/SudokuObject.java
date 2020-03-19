package org.grupa5.sudoku;

import java.util.ArrayList;
import java.util.List;

public class SudokuObject {

    private List<SudokuField> object;

    SudokuObject(ArrayList<SudokuField> lista) {
        this.object = lista;
    }

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