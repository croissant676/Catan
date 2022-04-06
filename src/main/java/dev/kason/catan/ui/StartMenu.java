package dev.kason.catan.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class StartMenu {

    @FXML
    private Button startButton;
    @FXML
    private Button continueButton;
    @FXML
    private Button viewGameRulesButton;

    @FXML
    public void initialize() {
        Stage stage = JavaFXApp.getStage();
        startButton.setOnMouseClicked(event -> {
            log.info("start button");
        });
        continueButton.setOnMouseClicked(event -> {
            log.info("continue button");
        });
        viewGameRulesButton.setOnMouseClicked(event -> {
            log.info("rules button");
        });
    }
}
