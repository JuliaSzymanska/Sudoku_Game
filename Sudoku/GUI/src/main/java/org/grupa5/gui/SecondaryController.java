package org.grupa5.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import org.grupa5.sudoku.SudokuField;

public class SecondaryController implements Initializable {

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

    // TODO: zrobić żeby grid się centrował po zmianie rozmiaru okna
    private void fillGrid() {
        int numRows = grid1.getRowCount();
        int numCols = grid1.getColumnCount();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                TextField textField = new TextField();
                textField.setAlignment(Pos.CENTER);
                textField.setMaxWidth(45);
                textField.setMaxHeight(45);
                if (i != 0 && j != 0) {
                    ObjectProperty<SudokuField> sudokuFieldObjectProperty = new SimpleObjectProperty<SudokuField>(sudokuBoard.getField(i - 1, j - 1));
                    Binding<SudokuField> sudokuFieldBinding = Bindings.createObjectBinding(() ->
                            sudokuFieldObjectProperty.get() == null ? null : sudokuFieldObjectProperty.get(), sudokuFieldObjectProperty);
                    System.out.println("ostre JD");
                    System.out.println(sudokuBoard.getField(i-1, j-1));
                    System.out.println(sudokuFieldBinding.getValue().getValue());
                    sudokuBoard.getField(i-1,j-1).setValue(9);
                    System.out.println(sudokuBoard.getField(i-1, j-1));
                    System.out.println(sudokuFieldBinding.getValue().getValue());

                    int intToAdd = sudokuBoard.get(j - 1, i - 1);
                    if (intToAdd != 0) {
                        textField.setDisable(true);
                    }
                    SimpleStringProperty stringProperty = new SimpleStringProperty();
                    stringProperty.set(Integer.toString(intToAdd));
                    textField.textProperty().bindBidirectional(stringProperty);
                }
                else if (i == 0 && j == 0) {
                    textField.setDisable(true);
                    textField.setText("X");
                }
                else if (i == 0) {
                    textField.setDisable(true);
                    textField.setText((Character.toString((char) (64 + j))));
                }
                else {
                    textField.setDisable(true);
                    textField.setText(("0" + i));
                }
                    grid1.add(textField, i, j);
            }
        }
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
        int numberOfFields = boxLevel.getSelectionModel().getSelectedItem().getNumber();
        sudokuBoard.solveGame();
        sudokuBoard.removeFields(numberOfFields);
        this.fillGrid();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.resourceBundle = ResourceBundle.getBundle("Lang", VariablesCollection.getLocale());
        if (resourceBundle.getString("language").equals("PL")) {
            boxLevel.setItems(FXCollections.observableArrayList(Level.values()[0], Level.values()[1], Level.values()[2]));
            boxLevel.setValue(Level.values()[0]);
        } else {
            boxLevel.setItems(FXCollections.observableArrayList(Level.values()[3], Level.values()[4], Level.values()[5]));
            boxLevel.setValue(Level.values()[3]);
        }
    }

    // TODO: generalnie zapisywanie dziala, ale no trzeba by chyba zrobic jakies wykrywanie że sie nie udało czy cos?
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

    public void changeLanguage() {
        // TODO: tutaj też Dżulia popraw tego ifa na coś co ma sens ._. mój mózg dzisiaj to przerasta
        if (VariablesCollection.getLocale().toString().equals("en_en")) {
            VariablesCollection.setLocale(new Locale("pl_PL"));
            resourceBundle = ResourceBundle.getBundle("Lang", VariablesCollection.getLocale());
            boxLevel.setItems(FXCollections.observableArrayList(Level.values()[3], Level.values()[4], Level.values()[5]));
            boxLevel.setValue(Level.values()[3]);
        } else {
            VariablesCollection.setLocale(new Locale("en_EN"));
            resourceBundle = ResourceBundle.getBundle("Lang", VariablesCollection.getLocale());
            boxLevel.setItems(FXCollections.observableArrayList(Level.values()[0], Level.values()[1], Level.values()[2]));
            boxLevel.setValue(Level.values()[0]);
        }
        updateLanguage();
    }

    private void updateLanguage() {
        secondaryButton.setText(resourceBundle.getString("start"));
        language.setText(resourceBundle.getString("language"));
        level.setText(resourceBundle.getString("level"));
        saveButton.setText(resourceBundle.getString("saveGame"));
        loadButton.setText(resourceBundle.getString("loadGame"));
    }

}


