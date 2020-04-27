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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.grupa5.sudoku.SudokuBoard;
import org.grupa5.sudoku.SudokuField;
import org.grupa5.sudoku.SudokuObject;

public class SecondaryController implements Initializable {

//public class SecondaryController {

    private SudokuBoard sudokuBoard = new SudokuBoard();

    private List<SudokuField> sudokuFieldList = new ArrayList<SudokuField>(sudokuBoard.get(0, 0));
//
    @FXML private TableView<SudokuField> table1;
    @FXML private TableColumn<SudokuField, Integer> column1;
    private ObservableList<SudokuField> studentList;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        column1.setText("Value");

        sudokuBoard.solveGame();
        table1.setItems(FXCollections.observableArrayList(new SudokuField(sudokuBoard.get(0,0))));
        column1.setCellValueFactory(new PropertyValueFactory<SudokuField, Integer>("value"));
    }

//    public ObservableList<SudokuField> getFields(){
//        sudokuBoard.solveGame();

//        for(int i = 0; i < 9; i++){
//            fields.add(new SudokuField(sudokuBoard.get(0,i)));
//        }
//        return FXCollections.observableArrayList(new SudokuField(sudokuBoard.get(0,0)));
//    }
}
