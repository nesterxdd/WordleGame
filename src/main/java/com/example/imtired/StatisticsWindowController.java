package com.example.imtired;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class StatisticsWindowController {


    @FXML
    private TableView<Attempt> attemptsTable;

    @FXML
    private TableColumn<Attempt, Integer> attemptNumberColumn;

    @FXML
    private TableColumn<Attempt, String> timeSpentColumn;

    @FXML
    private TableColumn<Attempt, Integer> numTriesColumn;

    @FXML
    private TableColumn<Attempt, String> isWinColumn;

    @FXML
    private TableColumn<Attempt, String> secretWord;

    private ObservableList<Attempt> attemptsData;

    public void initialize() {
        // Initialize table columns with the corresponding properties
        attemptNumberColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfAttempt"));
        timeSpentColumn.setCellValueFactory(new PropertyValueFactory<>("elapsedTime")); // Updated
        numTriesColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfTries")); // Updated
        isWinColumn.setCellValueFactory(new PropertyValueFactory<>("win")); // Updated
        secretWord.setCellValueFactory(new PropertyValueFactory<>("targetWord"));

    }
    public void setAttempts(List<Attempt> attempts) {
        attemptsData = FXCollections.observableArrayList(attempts);
        attemptsTable.setItems(attemptsData);
    }


}

