package dev.kason.catan.ui;

import com.google.common.io.Resources;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StartMenu {
    private static final Stage stage = JavaFXApp.getStage();

    @FXML
    private Button startButton;
    @FXML
    private Button continueButton;
    @FXML
    private Button viewGameRulesButton;

    @FXML
    @SneakyThrows
    public void initialize() {
        Stage stage = JavaFXApp.getStage();
        startButton.setOnMouseClicked(this::onStartButtonClick);
        continueButton.setOnMouseClicked(event -> {
            log.info("Continue an existing game");
            GameState.setCurrent(GameState.CONTINUE_GAME);
        });
        viewGameRulesButton.setOnMouseClicked(event -> {
            log.info("View the rules");
            GameState.setCurrent(GameState.VIEW_RULES);
        });
    }

    @SneakyThrows
    public void onStartButtonClick(MouseEvent mouseEvent) {
        log.info("Creating a new game");
        GameState.setCurrent(GameState.SELECT_PLAYERS);
        Scene scene = new Scene(FXMLLoader.load(Resources.getResource("fxml/menu.fxml")));
        log.info("Setting scene");
        stage.setScene(scene);
    }

}
