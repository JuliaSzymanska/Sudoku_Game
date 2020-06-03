package org.grupa5.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.application.Platform;
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
import org.grupa5.exceptions.*;
import org.grupa5.dao.SudokuBoardDaoFactory;
import org.grupa5.sudoku.SudokuBoard;
import org.grupa5.sudoku.SudokuField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SecondaryController implements Initializable {

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
    private static final String saveGameDb = "saveGameDb";


    private SudokuBoard sudokuBoard;
    private ResourceBundle resourceBundle;
    private final List<IntegerProperty> integerPropertyArrayListForSudokuFieldBinding = new ArrayList<>();

    @FXML
    private ComboBox<Level> boxLevel = new ComboBox<>();

    public enum Level {
        Easy(42),
        Medium(54),
        Hard(60);

        private final int number;

        public int getNumber() {
            return number;
        }

        public String getLocaleText() {
            ResourceBundle bundle = ResourceBundle.getBundle("Lang");
            return bundle.getString(this.name());
        }

        Level(int number) {
            this.number = number;
        }

        @Override
        public String toString() {
            return this.getLocaleText();
        }
    }

    StringConverter<Number> converter = new SudokuNumberStringConverter();

    @FXML
    private Button exit;

    @FXML
    private GridPane grid1;

    @FXML
    private Button secondaryButton;

    @FXML
    private Button language;

    @FXML
    private Button buttonFile;

    @FXML
    private Button buttonDb;

    @FXML
    private Label level;


    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    private boolean checkNumeric(String value) {
        String number = value.replaceAll("\\s+", "");
        for (int j = 0; j < number.length(); j++) {
            if (!(((int) number.charAt(j) > 48 && (int) number.charAt(j) <= 57))) {
                return false;
            }
        }
        return true;
    }

    private void switchStartAndEndButtons() {
        VariablesCollection.setIsGameInProgress(!VariablesCollection.getIsGameInProgress());
        if (!VariablesCollection.getIsGameInProgress()) {
            try {
                Media media = new Media(new File(".\\src\\main\\resources\\org\\grupa5\\gui\\sound.wav").toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setAutoPlay(true);
                this.switchToPrimary();
            } catch (IOException e) {
                if (this.logger.isErrorEnabled()) {
                    logger.error("", e);
                }
            }
            return;
        }
        secondaryButton.setText(resourceBundle.getString(end));
        buttonFile.setText(resourceBundle.getString(saveGame));
        buttonFile.setOnAction(e -> saveSudokuToFile());
        buttonDb.setText(resourceBundle.getString(saveGameDb));
        buttonDb.setOnAction(e -> saveSudokuToDb());
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
                textField.setStyle("-fx-background-color: rgba(255, 255, 255, 0.15);" +
                        "-fx-text-fill: white");
                textField.setTextFormatter(new TextFormatter<>(c -> {
                    if (c.isContentChange()) {
                        if (c.getText().matches("[1-9] | ^$ ")) {
                            return c;
                        }
                    }
                    return c;
                }));

                SudokuField sudokuField = this.sudokuBoard.getField(j, i);
                IntegerProperty integerProperty = new JavaBeanIntegerPropertyBuilder().bean(sudokuField).name("value").build();

                this.integerPropertyArrayListForSudokuFieldBinding.add(integerProperty);
                textField.textProperty().bindBidirectional(integerProperty, converter);

                textField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
                    textField.clear();
                    String character = event.getCharacter();
                    if (!checkNumeric(character)) {
                        textField.setText("");
                        event.consume();
                    }
                });
                textField.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
                    if (!sudokuBoard.isWholeBoardValid()) {
                        textField.setText("");
                    }
                });
                if (!sudokuField.isEditable()) {
                    textField.setDisable(true);
                }
                grid1.add(textField, i, j);
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Grid Filled");
        }

    }

    public void startGame() throws NoSuchMethodException {
        if (logger.isInfoEnabled()) {
            logger.info("Sudoku Game Started");
        }
        switchStartAndEndButtons();
        if (!VariablesCollection.getIsGameInProgress()) {
            this.level.setVisible(true);
            this.boxLevel.setVisible(true);
            return;
        }
        int numberOfFields = boxLevel.getSelectionModel().getSelectedItem().getNumber();
        this.sudokuBoard.solveGame();
        this.sudokuBoard.removeFields(numberOfFields);
        VariablesCollection.setSudokuBoard(this.sudokuBoard);
        this.level.setVisible(false);
        this.boxLevel.setVisible(false);
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

        BackgroundImage backgroundImage4 = new BackgroundImage(new Image(getClass().
                getResource("exit.png").toExternalForm()), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        Background background = new Background(backgroundImage);
        Background background2 = new Background(backgroundImage2);
        Background background3 = new Background(backgroundImage3);
        Background background4 = new Background(backgroundImage4);

        this.secondaryButton.setBackground(background);

        this.buttonDb.setBackground(background2);
        this.buttonFile.setBackground(background2);
        this.exit.setBackground(background4);

        this.language.setBackground(background3);

        if (logger.isDebugEnabled()) {
            logger.debug("SecondaryController init");
        }

        this.sudokuBoard = VariablesCollection.getSudokuBoard();
        this.resourceBundle = ResourceBundle.getBundle("Lang", Locale.getDefault());

        boxLevel.setItems(FXCollections.observableArrayList(Level.values()[0], Level.values()[1], Level.values()[2]));
        boxLevel.setValue(Level.values()[0]);


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
        if (!VariablesCollection.getIsGameInProgress()) {
            this.alertNotAbleToSaveGame();
            return;
        }

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
        if (!VariablesCollection.getIsGameInProgress()) {
            this.alertNotAbleToSaveGame();
            return;
        }
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
        } else {
            Locale.setDefault(new Locale("en", "en"));
        }
        resourceBundle = ResourceBundle.getBundle("Lang", Locale.getDefault());
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

    public void languageButtonPressed() {
        this.language.setBackground(getBackgroundForImage("Button_Small_Wood_Border_Smaller_Wcisniety.png"));
    }

    public void loadDBButtonPressed() {
        this.buttonDb.setBackground(getBackgroundForImage("Button_Small_Wood_Border_Wcisniety.png"));
    }

    public void loadDBButtonRelease() {
        this.buttonDb.setBackground(getBackgroundForImage("Button_Small_Wood_Border.png"));
    }

    public void loadFileButtonPressed() {
        this.buttonFile.setBackground(getBackgroundForImage("Button_Small_Wood_Border_Wcisniety.png"));
    }

    public void loadFileButtonRelease() {
        this.buttonFile.setBackground(getBackgroundForImage("Button_Small_Wood_Border.png"));
    }

    public void startButtonPressed() {
        this.secondaryButton.setBackground(getBackgroundForImage("Button_Wide_Wood_Border_wcisniety.png"));
    }

    public void startButtonReleased() {
        this.secondaryButton.setBackground(getBackgroundForImage("Button_Wide_Wood_Border.png"));
    }

    @FXML
    private void exitWindow() {
        Platform.exit();
    }

    private Background getBackgroundForImage(String image) {
        BackgroundImage backgroundImage2 = new BackgroundImage(new Image(getClass().
                getResource(image).toExternalForm()), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        return new Background(backgroundImage2);
    }

}