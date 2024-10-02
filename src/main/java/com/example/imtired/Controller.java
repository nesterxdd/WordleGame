package com.example.imtired;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.Instant;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class Controller {

    private GameModel gameModel;
    private final String PATH_TO_FILE = "src/main/5LetterWordsForProject.txt";
    private final String PATH_TO_LOOSE_IMAGE = "sadSticker.png";
    private final String PAT_TO_WIN_IMAGE = "congrats.png";
    private final int maxNumOfTries = 6;
    private final int maxNumOfLetters = 5;

    //styles and colors
    private static final String CORRECT_COLOR = "-fx-background-color: #89CFF0; -fx-border-color: lightblue; -fx-border-width: 2";
    private static final String PARTIAL_COLOR = "-fx-background-color: yellow; -fx-border-color: lightblue; -fx-border-width: 2";
    private static final String INCORRECT_COLOR = "-fx-background-color: lightgray; -fx-border-color: lightblue; -fx-border-width: 2";
    private static final String NEW_LABEL = "-fx-border-color: lightblue; -fx-border-width: 2";

    private int currentAttempt;

    private Instant startTime;

    @FXML
    private TextField guessInput;

    @FXML
    private GridPane gridPane;

    //constructor
    public Controller() {
        // Initialization logic here
        gameModel = new GameModel(maxNumOfTries,maxNumOfLetters, PATH_TO_FILE);
        currentAttempt = 1;
        gameModel.generateRandomTargetWord();
        gridPane = new GridPane();
        startGame();
        System.out.println(gameModel.getTargetWord());
    }

    // method for handling filters for the textfield and for handling events from keyboard
    @FXML
    public void initialize() {

        Pattern pattern = Pattern.compile("[a-zA-Z]*"); // Only allow letters

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (pattern.matcher(newText).matches() && newText.length() <= gameModel.getMaxNumOfLetters()) {
                return change;
            } else {
                return null;
            }
        };

        StringConverter<String> converter = new StringConverter<>() {
            @Override
            public String toString(String object) {
                return object;
            }

            @Override
            public String fromString(String string) {
                return string;
            }
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(converter, null, filter);
        guessInput.setTextFormatter(textFormatter);

        // Add a key event listener to guessInput
        guessInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                // Enter key was pressed, call the enterButtonPressed method
                enterButtonPressed();
            }
        });
    }

    @FXML
    protected void enterButtonPressed() {
        String text = guessInput.getText();
        if (text.length() == 5 && gameModel.contains(text)) {
            updateUIWithGuess(text);
            handleGameEvents(text);
        } else {
            // Word is incorrect, shake the TextField
            shakeTextField();
        }
        System.out.println("I was pressed");
    }

    private void shakeTextField() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(50), guessInput);
        tt.setFromX(-5); // Move left by 5 pixels
        tt.setToX(5);    // Move right by 5 pixels
        tt.setCycleCount(5); // Number of times to shake
        tt.setAutoReverse(true); // Auto reverse the animation
        tt.play();
    }

    public void statisticsButtonPressed() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Statistics.fxml"));
            Parent root1 = fxmlLoader.load();

            // Get the controller
            StatisticsWindowController controller = fxmlLoader.getController();
            controller.setAttempts(gameModel.getAttempts()); // Pass the attempts data to the controller

            // Create and show the stage
            Stage stage = new Stage();
            stage.setTitle("Statistics");
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            // Set the modality to APPLICATION_MODAL
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait(); // This will block interaction with the main window until the StatisticsWindow is closed
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading FXML: " + e.getMessage());
        }
    }

    //assigns new targetWord, resets all the labels
    protected void resetGame() {

        // Clear all labels in the gridPane
        for(int i = 0; i < gameModel.getCurrentRow(); i++){
            for(int j = 0; j < gameModel.getMaxNumOfLetters(); j++){
                Label temp = findLabelInGridPane(gridPane, i,j);
                temp.setText("");
                temp.setStyle(NEW_LABEL);
            }
        }
        gameModel.resetGame();
        startGame();
        currentAttempt++;
        guessInput.setText("");
        System.out.println("New target word: " + gameModel.getTargetWord());
    }
    private void startGame() {
        // Record the start time when the game starts
        startTime = Instant.now();
    }

    private void showCongratsWindow(String title, String message, String pathToImage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CongratsWindow.fxml"));
            Parent root1 = fxmlLoader.load();

            // Get the controller
            CongratsWindowController controller = fxmlLoader.getController();
            controller.setMainController(this);

            // Set the message and image in the controller
            controller.setMessage(message);
            controller.setImage(pathToImage);

            // Create and show the stage
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root1));
            stage.initStyle(StageStyle.UTILITY);
            stage.setResizable(false);
            // Set the modality to APPLICATION_MODAL
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait(); // This will block interaction with the main window until the CongratsWindow is closed
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading FXML: " + e.getMessage());
        }


    }
    private Label findLabelInGridPane(GridPane gridPane, int row, int col) {
        for (Node node : gridPane.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer colIndex = GridPane.getColumnIndex(node);
            if(rowIndex == null && row == 0 || (rowIndex != null && rowIndex == row)) {
                if (colIndex == null && col == 0 ||(colIndex != null && colIndex == col)) {
                    return (Label) node;
                }
            }
        }
        return null;
    }

    private void updateUIWithGuess(String text) {
        guessInput.setText("");
        for (int i = 0; i < gameModel.getMaxNumOfLetters(); i++) {
            Label label = findLabelInGridPane(gridPane, gameModel.getCurrentRow(), i);
            String letter = text.substring(i, i + 1);
            label.setText(letter);
            if (letter.equals(gameModel.getTargetWord().substring(i, i + 1))) {
                label.setStyle(CORRECT_COLOR);
            } else if (gameModel.getTargetWord().contains(letter)) {
                label.setStyle(PARTIAL_COLOR);
            } else {
                label.setStyle(INCORRECT_COLOR);
            }
        }
        gameModel.incrementRow();
    }
    private void handleGameEvents(String text) {
        if (gameModel.isGameOver() || gameModel.isWordGuessed(text)) {
            // To store the end time
            Instant endTime = Instant.now();

            // Calculate the duration manually
            long timeSpentMillis = endTime.toEpochMilli() - startTime.toEpochMilli();
            Attempt temp = new Attempt(gameModel.getCurrentRow(), gameModel.isWordGuessed(text), gameModel.getTargetWord(), new Duration(timeSpentMillis), currentAttempt);
            gameModel.addAttempt(temp);

            if (gameModel.getCurrentRow() >= gameModel.getMaxNumOfTries()) {
                // Game Over: Maximum rows reached.
                showCongratsWindow("Game Over", "Maximum rows reached.", PATH_TO_LOOSE_IMAGE);
            } else if (gameModel.isWordGuessed(text)) {
                // Congratulations! You guessed the word.
                showCongratsWindow("Congratulations!", "You guessed the word.", PAT_TO_WIN_IMAGE);
            }
        }
    }
}