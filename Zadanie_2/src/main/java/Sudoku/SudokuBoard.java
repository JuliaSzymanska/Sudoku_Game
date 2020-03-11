package Sudoku;

import java.util.ArrayList;
import java.util.Random;

public class SudokuBoard {

    private static int[][] board = new int[9][9];

    public void fillBoard() {
        int row = 0, column = 0;
        boolean numberFounded = false;
        Random rand = new Random();
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        for (int i = 1; i < 10; i++) {
            numbers.add(i);
        }
        while (!numbers.isEmpty() || !numberFounded) {
            int number = numbers.get(rand.nextInt(numbers.size()));
            if (check(number, row, column)) {
                board[row][column] = number;
                numberFounded = true;
                break;
            } else {
                numbers.remove(number);
            }

        }
    }

    private boolean check(int number, int row, int column) {
        for (int i = 0; i < 9; i++) {
            if (board[i][column] == number) return false;
            if (board[row][i] == number) return false;
        }
        int sectionR = row / 3, sectionC = column / 3;
        for (int i = sectionR; i < sectionR + 3; i++) {
            for (int j = sectionC; j < sectionC + 3; j++) {
                if (board[i][j] == number) return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int n = 1 / 3;
        System.out.println(n);
    }
}
