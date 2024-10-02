package com.example.imtired;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class CongratsWindowController {
    private Controller mainController;

    @FXML
    private Label messageLabel;

    @FXML
    private ImageView imageBox;

    @FXML
    private Button resetButton;


    public CongratsWindowController() {
        // Initialization logic here
        resetButton = new Button();
        messageLabel = new Label();
        imageBox = new ImageView();
    }

    public void setMainController(Controller mainController) {
        this.mainController = mainController;
    }


    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    public void setImage(String path){
       imageBox.setImage(new Image(path));
    }

    @FXML
    private void resetGame1()  {
        mainController.resetGame();
        // Get the current window's Stage and close it
        Stage stage = (Stage) resetButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void statisticsButtonPressed(){
        mainController.statisticsButtonPressed();
    }
}
