package org.grupa5.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import org.grupa5.sudoku.SudokuBoard;
import org.grupa5.sudoku.SudokuField;
import org.grupa5.sudoku.SudokuObject;

public class SecondaryController implements Initializable {

//public class SecondaryController {

    private SudokuBoard sudokuBoard = new SudokuBoard();

    @FXML
    private GridPane grid1;

//    private List<SudokuField> sudokuFieldList = new ArrayList<SudokuField>(sudokuBoard.get(0, 0));
//
//    @FXML private TableView<SudokuField> table1;
//    @FXML private TableColumn<SudokuField, Integer> column1;
//    @FXML private TableColumn<SudokuField, Integer> column2;
//    private ObservableList<SudokuField> studentList;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sudokuBoard.solveGame();
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
                    grid1.add(new TextField(Integer.toString(intToAdd)), i, j);
                }

            }
        }

        // CENTROWANIE
        for( Object i : grid1.getChildren()) {
            if(i instanceof TextField) {
                ((TextField) i).setAlignment(Pos.CENTER);
            }
            if(i instanceof Label) {
                ((Label) i).setAlignment(Pos.CENTER);
            }
        }
    }


//    public ObservableList<SudokuField> getFields(){
//        sudokuBoard.solveGame();

//        for(int i = 0; i < 9; i++){
//            fields.add(new SudokuField(sudokuBoard.get(0,i)));
//        }
//        return FXCollections.observableArrayList(new SudokuField(sudokuBoard.get(0,0)));
//    }
}
