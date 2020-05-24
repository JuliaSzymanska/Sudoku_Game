package org.grupa5.gui;

import org.grupa5.sudoku.SudokuBoard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class VariablesCollection {

    private static final VariablesCollection INSTANCE = new VariablesCollection();

    private static boolean isGameInProgress = false;

    private static SudokuBoard sudokuBoard = new SudokuBoard();

    private VariablesCollection() {
    }

    public static VariablesCollection getInstance() {
        return INSTANCE;
    }

    private static Locale locale = getDefaultLocale();

    public static Locale getLocale() {
        return locale;
    }

    public static void setLocale(Locale locale) {
        VariablesCollection.locale = locale;
    }

    public static List<Locale> getSupportedLocales() {
        return new ArrayList<>(Arrays.asList(new Locale("en_EN"), new Locale("pl_PL")));
    }

    public static Locale getDefaultLocale() {
        Locale sysDefault = Locale.getDefault();
        return getSupportedLocales().contains(sysDefault) ? sysDefault : new Locale("en_EN");
    }

    public static void setIsGameInProgress(boolean isGameInProgress) {
        VariablesCollection.isGameInProgress = isGameInProgress;
    }

    public static boolean getIsGameInProgress() {
        return isGameInProgress;
    }

    public static SudokuBoard getSudokuBoard() {
        return sudokuBoard;
    }

    public static void setSudokuBoard(SudokuBoard sudokuBoard) {
        VariablesCollection.sudokuBoard = sudokuBoard;
    }
}
