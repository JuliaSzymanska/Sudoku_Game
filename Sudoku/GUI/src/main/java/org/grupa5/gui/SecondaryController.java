package org.grupa5.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
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
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.grupa5.dao.SudokuBoardDaoFactory;
import org.grupa5.sudoku.SudokuBoard;

public class SecondaryController implements Initializable {

    private int numberOfFields;
    private SudokuBoard sudokuBoard = new SudokuBoard();
    private boolean flag = true;
    private ResourceBundle resourceBundle;

    @FXML
    private ComboBox<Level> boxLevel = new ComboBox<>();

    public enum Level {
        Easy(42),
        Medium(54),
        Hard(60),
        Prosty(42),
        Sredni(54),
        Trudny(60);

        private final int number;

        public int getNumber() {
            return number;
        }

        Level(int number) {
            this.number = number;
        }
    }

//    public enum LevelPolish {
//        Prosty(42),
//        Sredni(54),
//        Trudny(60);
//
//        private final int number;
//
//        public int getNumber() {
//            return number;
//        }
//
//        LevelPolish(int number) {
//            this.number = number;
//        }
//    }

    // TODO: to jest konwerter do bindingu, nie wiem jeszcze czy bedzie potrzebny
    StringConverter<Integer> converter = new IntegerStringConverter();

    @FXML
    private GridPane grid1;

    @FXML
    private Button secondaryButton;

    @FXML
    private Button language;

    @FXML
    private Button saveButton;

    @FXML
    private Label level;

    @FXML
    private Button loadButton;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    public void startGame() {
        if (!flag) {
            try {
                this.switchToPrimary();
            } catch (IOException e) {
                e.printStackTrace();
            }
            flag = true;
            return;
        }
        flag = false;
        secondaryButton.setText(resourceBundle.getString("end"));
        this.numberOfFields = boxLevel.getSelectionModel().getSelectedItem().getNumber();
        sudokuBoard.solveGame();
        sudokuBoard.removeFields(this.numberOfFields);
        int numRows = grid1.getRowCount();
        int numCols = grid1.getColumnCount();

        // Wypełnienie gridpane polami tekstowymi
        // TODO: zrobić sliczne wyswietlanie jak na zdj które wysłąlem na mesku że na górze jest
        //  kolumna i numer a po lewej jest rząd i numer
        for (int i = 0; i < numRows; i++) {
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
        for (Object i : grid1.getChildren()) {
            if (i instanceof TextField) {
                ((TextField) i).setAlignment(Pos.CENTER);
                ((TextField) i).setMaxWidth(45);
                ((TextField) i).setMaxHeight(45);

            }
            if (i instanceof Label) {
                ((Label) i).setMaxWidth(Double.MAX_VALUE);
                ((Label) i).setAlignment(Pos.CENTER);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.resourceBundle = rb;
        if (resourceBundle.getString("language").equals("PL")) {
            boxLevel.setItems(FXCollections.observableArrayList(Level.values()[0], Level.values()[1], Level.values()[2]));
            boxLevel.setValue(Level.values()[0]);
        } else {
            boxLevel.setItems(FXCollections.observableArrayList(Level.values()[3], Level.values()[4], Level.values()[5]));
            boxLevel.setValue(Level.values()[3]);
        }
    }

    // TODO: generalnie zapisywanie dziaa, ale no trzeba by chyba zrobic jakies wykrywanie że sie nie udało czy cos?
    //  na przyklad że jak sie DAO wywali bo coś to wystakuje powiadomienie no nie zapisalismy

    public void saveSudokuToFile() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            SudokuBoardDaoFactory.getFileDao(file.getAbsolutePath()).write(this.sudokuBoard);
        }
    }

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

    //TODO: naprawic zeby jezyk sie zmienial tez na pierwszym ekranie

    public void changeLanguage() {
        if (resourceBundle.getString("language").equals("PL")) {
            resourceBundle = ResourceBundle.getBundle("Lang", new Locale("pl_PL"));
            boxLevel.setItems(FXCollections.observableArrayList(Level.values()[3], Level.values()[4], Level.values()[5]));
            boxLevel.setValue(Level.values()[3]);
        } else {
            resourceBundle = ResourceBundle.getBundle("Lang", new Locale("en_EN"));
            boxLevel.setItems(FXCollections.observableArrayList(Level.values()[0], Level.values()[1], Level.values()[2]));
            boxLevel.setValue(Level.values()[0]);
        }
        secondaryButton.setText(resourceBundle.getString("start"));
        language.setText(resourceBundle.getString("language"));
        level.setText(resourceBundle.getString("level"));
        saveButton.setText(resourceBundle.getString("saveGame"));
        loadButton.setText(resourceBundle.getString("loadGame"));
    }

}


