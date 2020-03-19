package org.grupa5.sudoku;

import java.util.ArrayList;
import java.util.List;

public class SudokuObject {

    private int[] object = new int[9];

    public boolean verify() {
        List<Integer> list = new ArrayList<Integer>();
        for (int x : object) {
            if (list.contains(x)) {
                return false;
            }
            list.add(x);
        }
        return true;
    }
}