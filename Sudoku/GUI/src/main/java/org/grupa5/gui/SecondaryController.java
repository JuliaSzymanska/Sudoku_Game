package org.grupa5.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.beans.property.*;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import org.grupa5.dao.ReadException;
import org.grupa5.dao.SudokuBoardDaoFactory;
import org.grupa5.dao.WriteException;
import org.grupa5.sudoku.SudokuBoard;
import org.grupa5.sudoku.SudokuField;

public class SecondaryController implements Initializable {

    private SudokuBoard sudokuBoard = new SudokuBoard();
    private boolean flag = true;
    private ResourceBundle resourceBundle;
    private List<IntegerProperty> integerPropertyArrayListForSudokuFieldBinding = new ArrayList<>();

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

    StringConverter<Number> converter = new NumberStringConverter();

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

    private boolean checkNumeric(String value) {
        String number = value.replaceAll("\\s+", "");
        for (int j = 0; j < number.length(); j++) {
            if (!(((int) number.charAt(j) > 47 && (int) number.charAt(j) <= 57))) {
                return false;
            }
        }
        return true;
    }


    // TODO: zrobić żeby grid się centrował po zmianie rozmiaru okna
    private void fillGrid() throws NoSuchMethodException {
        int numRows = grid1.getRowCount();
        int numCols = grid1.getColumnCount();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                TextField textField = new TextField();
                textField.setAlignment(Pos.CENTER);
                textField.setMaxWidth(45);
                textField.setMaxHeight(45);

                if (i != 0 && j != 0) {

                    textField.setTextFormatter(new TextFormatter<>(c -> {
                        if (c.isContentChange()) {
                            if (c.getText().matches("[0-9] | ^$ ")) {
                                return c;
                            }
                        }
                        return c;
                    }));

                    SudokuField sudokuField = this.sudokuBoard.getField(j - 1, i - 1);
                    IntegerProperty integerProperty = new JavaBeanIntegerPropertyBuilder().bean(sudokuField).name("value").build();

                    this.integerPropertyArrayListForSudokuFieldBinding.add(integerProperty);
                    textField.textProperty().bindBidirectional(integerProperty, converter);

                    textField.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent event) {
                            textField.clear();
                            String character = event.getCharacter();
                            if (!checkNumeric(character)) {
                                textField.setText("0");
                                event.consume();
                            }
                        }
                    });
                    textField.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent event) {
                            if (!sudokuBoard.isWholeBoardValid()) {
                                textField.setText("0");
                            }
                        }
                    });

                    int intToAdd = sudokuBoard.get(j - 1, i - 1);
                    if (intToAdd != 0) {
                        textField.setDisable(true);
                    }
                } else if (i == 0 && j == 0) {
                    textField.setDisable(true);
                    textField.setText("X");
                } else if (i == 0) {
                    textField.setDisable(true);
                    textField.setText((Character.toString((char) (64 + j))));
                } else {
                    textField.setDisable(true);
                    textField.setText(("0" + i));
                }
                grid1.add(textField, i, j);
            }
        }
    }

    public void startGame() throws NoSuchMethodException {
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
        if (VariablesCollection.getLocale().toString().equals("en_en")) {
            boxLevel.setItems(FXCollections.observableArrayList(Level.values()[0], Level.values()[1], Level.values()[2]));
            boxLevel.setValue(Level.values()[0]);
        } else {
            boxLevel.setItems(FXCollections.observableArrayList(Level.values()[3], Level.values()[4], Level.values()[5]));
            boxLevel.setValue(Level.values()[3]);
        }
    }

    public void saveSudokuToFile() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            try {
                SudokuBoardDaoFactory.getFileDao(file.getAbsolutePath()).write(this.sudokuBoard);
            } catch (WriteException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Save Error");
                alert.setHeaderText("Error Saving Game");
                alert.setContentText("There was an error saving your game.\n" +
                        "Please try to save again!");

                alert.showAndWait();
            }
        }
    }

    public void readSudokuFromFile() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            try {
                this.sudokuBoard = SudokuBoardDaoFactory.getFileDao(file.getAbsolutePath()).read();
                if (!flag) {
                    flag = true;
                }
                this.fillGrid();
            } catch (ReadException | NoSuchMethodException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Load Error");
                alert.setHeaderText("Error Loading Game");
                alert.setContentText("There was an error loading your game.\n" +
                        "Please try to load again!");
                alert.showAndWait();
            }
        }
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


