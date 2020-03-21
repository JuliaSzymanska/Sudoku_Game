package org.grupa5.sudoku;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SudokuBoard {

    public SudokuBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board.get(i).set(j, new SudokuField());
            }
        }
    }

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

    // TODO: jeśli masz lepszy pomysł na tą metodę, to śmiało :)
    public SudokuObject getColumn(int column) {
        List<SudokuField> col = Arrays.asList(
                board.get(0).get(column),
                board.get(1).get(column),
                board.get(2).get(column),
                board.get(3).get(column),
                board.get(4).get(column),
                board.get(5).get(column),
                board.get(6).get(column),
                board.get(7).get(column),
                board.get(8).get(column));
        return new SudokuObject(col);
    }


    public SudokuObject getRow(int row) {
        return new SudokuObject(board.get(row));
    }

    public SudokuObject getBox(int row, int column) {
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
        return new SudokuObject(box);
    }

    /**
     * A simple getter that returns copy of the 'board' variable.
     *
     * @return copy of the board.
     */
    public List<List<SudokuField>> getBoard() {
//        ArrayList<ArrayList<SudokuField>> copy = new ArrayList<>(
//        this.board.stream().map(x -> new ArrayList<>(x)).collect(Collectors.toList()));
        List<List<SudokuField>> copy = Arrays.asList(
                Arrays.asList(new SudokuField[9]),
                Arrays.asList(new SudokuField[9]),
                Arrays.asList(new SudokuField[9]),
                Arrays.asList(new SudokuField[9]),
                Arrays.asList(new SudokuField[9]),
                Arrays.asList(new SudokuField[9]),
                Arrays.asList(new SudokuField[9]),
                Arrays.asList(new SudokuField[9]),
                Arrays.asList(new SudokuField[9]));
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                copy.get(i).set(j, new SudokuField());
            }
        }
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
        SudokuBoard plansza = (SudokuBoard) other;
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
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board.get(i).set(j, new SudokuField());
            }
        }
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
    // TODO: trzeba to jakoś zamienić chwilowo nie ma pomysłu
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
//        board.get(row).set(column, number);
        // TODO : podmien na getCol(col).verify(), getRow(row).verify(), getBox(row,
        // col).verify()
        return// getColumn(column).verify() && getRow(row).verify() && getBox(row, column).verify();
                this.checkCol(column, number) && this.checkRow(row, number)
                        && this.checkSector(getSectorNumber(row, column), number);
    }

    /**
     * Write all board's numbers to StringBuilder and then converted to String.
     */

//       public String getInfoSudoku() {
//           StringBuilder output = new StringBuilder("X ");
//           for (int i = 0; i <= 8; i++) {
//               output.append((char) ('a' + i)).append(" ");
//           }
//           output.append("\n");
//           int counter = 0;
//           for (ArrayList<SudokuField> x : this.board) {
//               output.append((char) ('a' + counter)).append(" ");
//               for (SudokuField y : x) {
//                   output.append(y.getFieldValue()).append(" ");
//               }
//               output.append("\n");
//               counter++;
//           }
//           return output.toString();
//       }

}
