package org.grupa5.gui;

import java.io.IOException;
import java.net.URL;
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

    private SudokuBoard sudokuBoard = new SudokuBoard();

    @FXML private TableView<SudokuField> tableView;
    @FXML private TableColumn<SudokuField, Integer> first;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        first.setText("Value");
        sudokuBoard.solveGame();
        tableView.setItems(FXCollections.observableArrayList(new SudokuField(sudokuBoard.get(0,0))));
        first.setCellValueFactory(new PropertyValueFactory<SudokuField, Integer>("value"));
    }

//    public ObservableList<SudokuField> getFields(){
//        sudokuBoard.solveGame();

//        for(int i = 0; i < 9; i++){
//            fields.add(new SudokuField(sudokuBoard.get(0,i)));
//        }
//        return FXCollections.observableArrayList(new SudokuField(sudokuBoard.get(0,0)));
//    }
}
