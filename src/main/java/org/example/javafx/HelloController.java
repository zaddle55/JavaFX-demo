package org.example.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private  Label introText;


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    protected void onExitButtonClick() {
        System.exit(0);
    }

    @FXML void onButtonEnter() {
        introText.setText("You entered the button");
    }
    @FXML
    protected void onClearButtonClick() {
        introText.setText("");
    }

}
