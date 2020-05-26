package org.grupa5.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.beans.property.*;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import org.grupa5.exceptions.*;
import org.grupa5.dao.SudokuBoardDaoFactory;
import org.grupa5.sudoku.SudokuBoard;
import org.grupa5.sudoku.SudokuField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecondaryController implements Initializable {

    @FXML
    private AnchorPane root;

    private final Logger logger = LoggerFactory.getLogger(SecondaryController.class);
    private static final String loadGame = "loadGame";
    private static final String saveGame = "saveGame";
    private static final String end = "end";
    private static final String loadError = "loadError";
    private static final String saveError = "saveError";
    private static final String nameSaveDB = "nameSaveDB";
    private static final String loadingFailed = "loadingFailed";
    private static final String savingFailed = "savingFailed";
    private static final String tryAgain = "tryAgain";


    private SudokuBoard sudokuBoard;
    private ResourceBundle resourceBundle;
    private final List<IntegerProperty> integerPropertyArrayListForSudokuFieldBinding = new ArrayList<>();

    @FXML
    private ComboBox<Level> boxLevel = new ComboBox<>();

    // TODO: 13.05.2020 zapisac nazwy jako klucze  internalizowac tego enuma jakos
    //   https://softwareengineering.stackexchange.com/questions/256806/best-approach-for-multilingual-java-enum
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

    StringConverter<Number> converter = new SudokuNumberStringConverter();

    @FXML
    private GridPane grid1;

    // TODO: 26.05.2020 Zrobić żeby przyciski po nacisnięciu się zmieniały
    @FXML
    private Button secondaryButton;

    @FXML
    private Button language;

    @FXML
    private Button saveButtonFile;

    @FXML
    private Button loadButtonFile;

    @FXML
    private Button loadButtonDb;

    @FXML
    private Button saveButtonDb;

    @FXML
    private Label level;

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

    private void switchStartAndEndButtons() {
        VariablesCollection.setIsGameInProgress(!VariablesCollection.getIsGameInProgress());
        if (!VariablesCollection.getIsGameInProgress()) {
            try {
                this.switchToPrimary();
            } catch (IOException e) {
                if (this.logger.isErrorEnabled()) {
                    logger.error("", e);
                }
            }
            return;
        }
        secondaryButton.setText(resourceBundle.getString(end));
    }

    private void fillGrid() throws NoSuchMethodException {
        int numRows = grid1.getRowCount();
        int numCols = grid1.getColumnCount();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                TextField textField = new TextField();
                textField.setAlignment(Pos.CENTER);
                textField.setMaxWidth(45);
                textField.setMaxHeight(45);
                textField.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1);" +
                        "-fx-text-fill: white");
                textField.setTextFormatter(new TextFormatter<>(c -> {
                    if (c.isContentChange()) {
                        if (c.getText().matches("[0-9] | ^$ ")) {
                            return c;
                        }
                    }
                    return c;
                }));

                SudokuField sudokuField = this.sudokuBoard.getField(j, i);
                IntegerProperty integerProperty = new JavaBeanIntegerPropertyBuilder().bean(sudokuField).name("value").build();

                this.integerPropertyArrayListForSudokuFieldBinding.add(integerProperty);
                textField.textProperty().bindBidirectional(integerProperty, converter);

                textField.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        textField.clear();
                        String character = event.getCharacter();
                        if (!checkNumeric(character)) {
                            textField.setText("");
                            event.consume();
                        }
                    }
                });
                textField.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (!sudokuBoard.isWholeBoardValid()) {
                            textField.setText("");
                        }
                    }
                });

                int intToAdd = 0;
                try {
                    intToAdd = sudokuBoard.get(j, i);
                } catch (GetException e) {
                    if (this.logger.isErrorEnabled()) {
                        logger.error("", e);
                    }
                }
                if (intToAdd != 0) {
                    textField.setDisable(true);
                }
                grid1.add(textField, i, j);
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Grid Filled");
        }

    }

    // TODO: 26.05.2020 spraw żeby enum znikal w trakcie gry
    public void startGame() throws NoSuchMethodException {
        if (logger.isInfoEnabled()) {
            logger.info("Sudoku Game Started");
        }
        switchStartAndEndButtons();
        int numberOfFields = boxLevel.getSelectionModel().getSelectedItem().getNumber();
        this.sudokuBoard.solveGame();
        this.sudokuBoard.removeFields(numberOfFields);
        VariablesCollection.setSudokuBoard(this.sudokuBoard);
        this.fillGrid();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BackgroundImage backgroundImage = new BackgroundImage(new Image(getClass().
                getResource("Button_Wide_Wood_Border.png").toExternalForm()), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        BackgroundImage backgroundImage2 = new BackgroundImage(new Image(getClass().
                getResource("Button_Small_Wood_Border.png").toExternalForm()), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        BackgroundImage backgroundImage3 = new BackgroundImage(new Image(getClass().
                getResource("Button_Small_Wood_Border_Smaller.png").toExternalForm()), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        Background background = new Background(backgroundImage);
        Background background2 = new Background(backgroundImage2);
        Background background3 = new Background(backgroundImage3);
        this.secondaryButton.setBackground(background);
        this.loadButtonDb.setBackground(background2);
        this.saveButtonDb.setBackground(background2);
        this.saveButtonFile.setBackground(background2);
        this.loadButtonFile.setBackground(background2);
        this.language.setBackground(background3);
        if (logger.isDebugEnabled()) {
            logger.debug("SecondaryController init");
        }
        this.sudokuBoard = VariablesCollection.getSudokuBoard();
        this.resourceBundle = ResourceBundle.getBundle("Lang", Locale.getDefault());
        if (Locale.getDefault().equals(new Locale("en", "en"))) {
            boxLevel.setItems(FXCollections.observableArrayList(Level.values()[0], Level.values()[1], Level.values()[2]));
            boxLevel.setValue(Level.values()[0]);
        } else {
            boxLevel.setItems(FXCollections.observableArrayList(Level.values()[3], Level.values()[4], Level.values()[5]));
            boxLevel.setValue(Level.values()[3]);
        }
        if (VariablesCollection.getIsGameInProgress()) {
            try {
                this.fillGrid();
            } catch (NoSuchMethodException e) {
                if (logger.isTraceEnabled()) {
                    logger.trace("", e);
                }
            }
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
            } catch (DaoException e) {
                this.alertNotAbleToSaveGame();
                if (this.logger.isInfoEnabled()) {
                    this.logger.info("", e);
                }
            }
        }
    }

    public void saveSudokuToDb() {
        TextInputDialog td = new TextInputDialog(resourceBundle.getString(nameSaveDB));
        td.setTitle(resourceBundle.getString(saveGame));
        td.setHeaderText(resourceBundle.getString(saveGame));
        AtomicBoolean isTextPropert = new AtomicBoolean(true);
        td.showAndWait().ifPresent((text) -> {
            try {
                checkTextInutDB(text);
            } catch (NumberFormatException | DaoWriteException e) {
                this.alertNotAbleToSaveGame();
                isTextPropert.set(false);
                if (this.logger.isInfoEnabled()) {
                    this.logger.info("", e);
                }
            }
        });
        if (!isTextPropert.get()) {
            return;
        }
        String inputString = td.getEditor().getText();
        if (!inputString.equals("")) {
            try {
                // TODO: 18.05.2020 zrobic parametr
                SudokuBoardDaoFactory.getJdbcDao(inputString).write(this.sudokuBoard);
            } catch (DaoException e) {
                this.alertNotAbleToSaveGame();
                if (this.logger.isInfoEnabled()) {
                    this.logger.info("", e);
                }
            }
        }
    }

    public void readSudokuFromFile() {
        // TODO: 05.05.2020 popraw wczytywanie rozpoczetej gry, zeby pola ktore wczensije
        //  sie wpisalo przed zapisem a nie sa zerami dalo sie zmienic po wczytaniu
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            try {
                this.sudokuBoard = SudokuBoardDaoFactory.getFileDao(file.getAbsolutePath()).read();
                switchStartAndEndButtons();
                this.fillGrid();
            } catch (DaoException | NoSuchMethodException e) {
                this.alertNotAbleToReadGame();
                if (this.logger.isInfoEnabled()) {
                    this.logger.info("", e);
                }
            }
        }
    }

    public void readSudokuFromDb() {
        TextInputDialog td = new TextInputDialog(resourceBundle.getString("nameReadDB"));
        td.setTitle(resourceBundle.getString(loadGame));
        td.setHeaderText(resourceBundle.getString(loadGame));
        AtomicBoolean isTextPropert = new AtomicBoolean(true);
        td.showAndWait().ifPresent((text) -> {
            try {
                checkTextInutDB(text);
            } catch (NumberFormatException | DaoWriteException e) {
                this.alertNotAbleToReadGame();
                isTextPropert.set(false);
                if (this.logger.isInfoEnabled()) {
                    this.logger.info("", e);
                }
            }
        });
        if (!isTextPropert.get()) {
            return;
        }
        String inputString = td.getEditor().getText();
        try {
            // TODO: 18.05.2020 poprawic parametr
            this.sudokuBoard = SudokuBoardDaoFactory.getJdbcDao(inputString).read();
            switchStartAndEndButtons();
            this.fillGrid();
        } catch (NoSuchMethodException | DaoException e) {
            this.alertNotAbleToReadGame();
            if (this.logger.isInfoEnabled()) {
                this.logger.info("", e);
            }
        }
    }

    private void checkTextInutDB(String text) throws DaoWriteException {
        if (text.length() > 20) {
            throw new DaoWriteException("DBWrite");
        }
        for (int i = 0; i < text.length(); i++) {
            if (!Character.isLetterOrDigit(text.charAt(i))) {
                throw new DaoWriteException("DBWrite");
            }
        }

    }

    private void alertNotAbleToReadGame() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(resourceBundle.getString(loadError));
        alert.setHeaderText(resourceBundle.getString(loadError));
        alert.setContentText(resourceBundle.getString(loadingFailed) + "\n" +
                resourceBundle.getString(tryAgain));
        alert.showAndWait();

        if (logger.isErrorEnabled()) {
            logger.error(resourceBundle.getString(loadingFailed));
        }
    }

    private void alertNotAbleToSaveGame() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(resourceBundle.getString(saveError));
        alert.setHeaderText(resourceBundle.getString(saveError));
        alert.setContentText(resourceBundle.getString(savingFailed) + "\n" +
                resourceBundle.getString(tryAgain));

        alert.showAndWait();

        if (logger.isErrorEnabled()) {
            logger.error(resourceBundle.getString(savingFailed));
        }
    }

    public void changeLanguage() {
        if (Locale.getDefault().equals(new Locale("en", "en"))) {
            Locale.setDefault(new Locale("pl", "pl"));
            resourceBundle = ResourceBundle.getBundle("Lang", Locale.getDefault());
            boxLevel.setItems(FXCollections.observableArrayList(Level.values()[3], Level.values()[4], Level.values()[5]));
            boxLevel.setValue(Level.values()[3]);
        } else {
            Locale.setDefault(new Locale("en", "en"));
            resourceBundle = ResourceBundle.getBundle("Lang", Locale.getDefault());
            boxLevel.setItems(FXCollections.observableArrayList(Level.values()[0], Level.values()[1], Level.values()[2]));
            boxLevel.setValue(Level.values()[0]);
        }
        try {
            updateLanguage();
        } catch (IOException e) {
            if (logger.isErrorEnabled()) {
                logger.error("changeLanguage threw ", e);
            }
        }
    }

    private void updateLanguage() throws IOException {
        App reload = new App();
        reload.reload("secondary");
    }

}


