package org.grupa5.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.grupa5.sudoku.SudokuBoard;

public class SecondaryController implements Initializable {

    private int numberOfFilds;
    private SudokuBoard sudokuBoard = new SudokuBoard();

    @FXML
    private GridPane grid1;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    public void setNumberOfFields(int number){
        this.numberOfFilds = number;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sudokuBoard.solveGame();
        this.numberOfFilds = 50;
        System.out.println(this.numberOfFilds);
        sudokuBoard.removeFields(this.numberOfFilds);
        int numRows = grid1.getRowCount();
        int numCols = grid1.getColumnCount();

        // Wypełnienie gridpane polami tekstowymi
        for(int i = 0; i < numRows; i++) {
            for(int j = 0; j < numCols; j++) {
                if(i == 0 && j == 0) {
                    grid1.add(new Label("X"), 0,0);
                }
                else if (i != 0 && j == 0) {
                    grid1.add(new Label("0" + Integer.toString(i)), i, j);
                }
                else if (i == 0) {
                    grid1.add(new Label(Character.toString((char) (64 + j))), i, j);
                }
                else {
                    int intToAdd = sudokuBoard.get(j - 1, i - 1);
                    if(sudokuBoard.get(j - 1, i - 1) == 0){
                    grid1.add(new TextField(Integer.toString(intToAdd)), i, j);}
                    else {
                        grid1.add(new Label(Integer.toString(intToAdd)), i, j);
                    }
                }

            }
        }

        // CENTROWANIE
        for( Object i : grid1.getChildren()) {
            if(i instanceof TextField) {
                ((TextField) i).setAlignment(Pos.CENTER);
            }
            if(i instanceof Label) {
                ((Label) i).setMaxWidth(Double.MAX_VALUE);
                ((Label) i).setAlignment(Pos.CENTER);
            }
        }
    }

}
