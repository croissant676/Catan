package dev.kason.catan.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JavaFXApp extends Application {

    @Getter
    private static Stage stage;

    @Override
    public void init() {
        log.info("Initialized application `{}`", getClass().getCanonicalName());
        GameState.setCurrent(GameState.INIT);
    }

    @Override
    public void start(Stage stage) throws Exception {
        JavaFXApp.stage = stage;
        stage.setTitle("Catan");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/start_menu.fxml"));
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }
}
