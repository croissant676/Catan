package dev.kason.catan.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StartMenu {

    @FXML
    private Button startButton;
    @FXML
    private Button continueButton;
    @FXML
    private Button viewGameRulesButton;

    @FXML
    public void initialize() {
        startButton.setOnMouseClicked(event -> {
            log.info("Creating a new game");
        });

    }
}
