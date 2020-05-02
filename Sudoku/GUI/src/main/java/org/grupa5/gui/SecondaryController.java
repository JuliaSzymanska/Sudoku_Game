package org.grupa5.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.grupa5.dao.SudokuBoardDaoFactory;
import org.grupa5.sudoku.SudokuBoard;

public class SecondaryController implements Initializable {

    private int numberOfFields;
    private SudokuBoard sudokuBoard = new SudokuBoard();
    private boolean flag = true;
    private ResourceBundle resourceBundle;


    @FXML
    private ComboBox<Level> boxLevel = new ComboBox<>();

    public enum Level{
        Easy(42),
        Medium(54),
        Hard(60);

        private final int number;

        public int getNumber(){
            return number;
        }

        Level(int number) {
            this.number = number;
        }
    }

    @FXML
    private GridPane grid1;

    @FXML
    private Button secondaryButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button loadButton;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    public void setNumberOfFields(int number){
        this.numberOfFields = number;
    }

    public void startGame() {
        if(!flag){
            try {
                this.switchToPrimary();
            } catch (IOException e) {
                e.printStackTrace();
            }
            flag = true;
            return;
        }
        flag = false;
        // TODO: mam z tym problem
//         secondaryButton.setText(resourceBundle.getString("end"));
        secondaryButton.setText("fixME");
        this.numberOfFields = boxLevel.getSelectionModel().getSelectedItem().getNumber();
        sudokuBoard.solveGame();
        sudokuBoard.removeFields(this.numberOfFields);
        int numRows = grid1.getRowCount();
        int numCols = grid1.getColumnCount();

        // Wypełnienie gridpane polami tekstowymi
        // TODO: zrobić sliczne wyswietlanie jak na zdj które wysłąlem na mesku że na górze jest
        //  kolumna i numer a po lewej jest rząd i numer
        for(int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (i == 0 && j == 0) {
                    grid1.add(new Label("X"), 0, 0);
                } else if (i != 0 && j == 0) {
                    grid1.add(new Label("0" + Integer.toString(i)), i, j);
                } else if (i == 0) {
                    grid1.add(new Label(Character.toString((char) (64 + j))), i, j);
                } else {
                    int intToAdd = sudokuBoard.get(j - 1, i - 1);
                    if (sudokuBoard.get(j - 1, i - 1) == 0) {
                        grid1.add(new TextField(Integer.toString(intToAdd)), i, j);
                    } else {
                        grid1.add(new Label(Integer.toString(intToAdd)), i, j);
                    }
                }

            }
        }
        // CENTROWANIE
        for( Object i : grid1.getChildren()) {
            if(i instanceof TextField) {
                ((TextField) i).setAlignment(Pos.CENTER);
                ((TextField) i).setMaxWidth(45);
                ((TextField) i).setMaxHeight(45);

            }
            if(i instanceof Label) {
                ((Label) i).setMaxWidth(Double.MAX_VALUE);
                ((Label) i).setAlignment(Pos.CENTER);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        boxLevel.setItems( FXCollections.observableArrayList(Level.values()));
//        boxLevel.getItems().addAll(Level.values());
        boxLevel.setValue(Level.Easy);
        this.resourceBundle = rb;
    }

    // TODO: generalnie zapisywanie dziaa, ale no trzeba by chyba zrobic jakies wykrywanie że sie nie udało czy cos?
    //  na przyklad że jak sie DAO wywali bo coś to wystakuje powiadomienie no nie zapisalismy

    // TODO: przycisk do save nie chce żeby był tam gdzie jest, ale narazie jest
    public void saveSudokuToFile() {
        FileChooser fileChooser = new FileChooser();


        // TODO: usun komentarze
        // Set extension filter for text files
        // DAO używa .txt
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            SudokuBoardDaoFactory.getFileDao(file.getAbsolutePath()).write(this.sudokuBoard);
        }
    }

    // TODO: to samo co przy save
    //  ale generalnie "dziaa"

    public void readSudokuFromFile() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(new Stage());

        System.out.println(this.sudokuBoard);
        if (file != null) {
            // TODO: jesli wczytamy nie wlasciwy plik to board ustawi sie na NULL, no chyba zle, znowu te wyjatki
            this.sudokuBoard = SudokuBoardDaoFactory.getFileDao(file.getAbsolutePath()).read();
        }
        System.out.println(this.sudokuBoard);
    }

}


