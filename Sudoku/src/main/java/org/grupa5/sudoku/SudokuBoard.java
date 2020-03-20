package org.grupa5.sudoku;
// import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
// import java.util.Arrays;

public class SudokuBoard {
    //    private SudokuField[][] board = new SudokuField[9][9];
    //    private ArrayList<ArrayList<SudokuField>> board;
    private List<List<SudokuField>> board = Arrays.asList(
        Arrays.asList(new SudokuField[9]),
        Arrays.asList(new SudokuField[9]),
        Arrays.asList(new SudokuField[9]),
        Arrays.asList(new SudokuField[9]),
        Arrays.asList(new SudokuField[9]),
        Arrays.asList(new SudokuField[9]),
        Arrays.asList(new SudokuField[9]),
        Arrays.asList(new SudokuField[9]),
        Arrays.asList(new SudokuField[9]));

    // TODO: zrób to
     public SudokuColumn getColumn(int column) {
         return new SudokuColumn(board.get(column));
     }

     public SudokuRow getRow(int row) {
         return new SudokuRow(board.get(row));
     }

     public SudokuObject getSector(int row, int column) {
        int sectorNr = this.getSectorNumber(row, column);
         int begX = (sectorNr / 3) * 3;
         int begY = (sectorNr % 3) * 3;
         int k = 0;
         List<SudokuField> box = Arrays.asList(new SudokuField[9]);
         for (int i = begX; i <= begX + 2; i++) {
             for (int j = begY; j <= begY + 2; j++) {
                box.set(k, board.get(i).get(j));
                k++;
             }
         }
         return new SudokuBox(box);
     }

    /**
     * A simple getter that returns copy of the 'board' variable.
     * @return copy of the board.
     */
    public List<List<SudokuField>>  getBoard() {
//        ArrayList<ArrayList<SudokuField>> copy = new ArrayList<>(
//        this.board.stream().map(x -> new ArrayList<>(x)).collect(Collectors.toList()));
        List<List<SudokuField>> copy = Arrays.asList();
        Collections.copy(copy, board);
        return copy;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!(other instanceof SudokuBoard)) {
            return false;
        }
        SudokuBoard plansza = (SudokuBoard)other;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (plansza.get(i, j) != this.get(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Return int from board at [x][y] position.
     */

    public int get(int x, int y) {
        return this.board.get(x).get(y).getFieldValue();
    }

    /**
     * Set value at [x][y] position in board.
     */

    public void set(int x, int y, int value) {
        if (checkBoard(x, y, value)) {
            this.board.get(x).get(y).setFieldValue(value);
        }
    }

    /**
     * Reset board, fill with 0.
     */

    public void resetBoard() {
        ArrayList<ArrayList<SudokuField>> plansza = new ArrayList<ArrayList<SudokuField>>();
        //SudokuField[][] plansza = new SudokuField[9][9];
        for (int i = 0; i < 9; i++) {
            plansza.add(new ArrayList<SudokuField>());
            for (int j = 0; j < 9; j++) {
                plansza.get(i).add(new SudokuField());
            }
        }
        this.board = plansza;
        //TODO: dodaj tworzenie odpowiednich instancji listy rzędów, kolumn i boxów
    }

    /**
     * Check if value can be set at col.
     */

    // TODO: usun to i używaj metod z klas kolumn / rzędów / boxów

    private boolean checkCol(int col, int value) {
        if (col < 0 || col > 8) {
            throw new IndexOutOfBoundsException("Column has to be in range 0 - 8");
        }
        for (int i = 0; i <= 8; i++) {
            if (this.board.get(i).get(col).getFieldValue() == value) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if value can be set at row.
     */

    // TODO: usun to i używaj metod z klas kolumn / rzędów / boxów

    private boolean checkRow(int row, int value) {
        if (row < 0 || row > 8) {
            throw new IndexOutOfBoundsException("Row has to be in range 0 - 8");
        }
        for (int i = 0; i <= 8; i++) {
            if (this.board.get(row).get(i).getFieldValue() == value) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if value can be set at sectorNr.
     */

    // TODO: usun to i używaj metod z klas kolumn / rzędów / boxów

    private boolean checkSector(int sectorNr, int value) {
        if (sectorNr < 0 || sectorNr > 8) {
            throw new IndexOutOfBoundsException("Sector sectorNr has to be in range from 0 to 8");
        }
        int begX = (sectorNr / 3) * 3;
        int begY = (sectorNr % 3) * 3;
        for (int i = begX; i <= begX + 2; i++) {
            for (int j = begY; j <= begY + 2; j++) {
                if (this.board.get(i).get(j).getFieldValue() == value) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Return the number of sector in which is object at posinion [row][col].
     */

    private int getSectorNumber(int row, int col) {
        int sectorNr = (row / 3) * 3;
        sectorNr += col / 3;
        return sectorNr;
    }

    /**
     * Check if number can be set at position [row][column].
     */

    private boolean checkBoard(int row, int column, int number) {
        if (row < 0 || row > 8) {
            throw new IndexOutOfBoundsException("Row has to be in range 0 - 8");
        }
        if (column < 0 || column > 8) {
            throw new IndexOutOfBoundsException("Column has to be in range 0 - 8");
        }
        if (number == 0) {
            return true;
        }
        // TODO : podmien na getCol(col).verify(), getRow(row).verify(), getBox(row, col).verify()
        return this.checkCol(column, number) && this.checkRow(row, number)
                && this.checkSector(getSectorNumber(row, column), number);
    }

       /**
        * Write all board's numbers to StringBuilder and then converted to String.
        */
    
       public String getInfoSudoku() {
           StringBuilder output = new StringBuilder("X ");
           for (int i = 0; i <= 8; i++) {
               output.append((char) ('a' + i)).append(" ");
           }
           output.append("\n");
           int counter = 0;
           for (ArrayList<SudokuField> x : this.board) {
               output.append((char) ('a' + counter)).append(" ");
               for (SudokuField y : x) {
                   output.append(y.getFieldValue()).append(" ");
               }
               output.append("\n");
               counter++;
           }
           return output.toString();
       }

}
