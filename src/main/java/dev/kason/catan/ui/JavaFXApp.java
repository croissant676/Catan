package dev.kason.catan.ui;

import com.google.common.io.Resources;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JavaFXApp extends Application {

    @Getter
    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        log.info("Starting JavaFX application, displaying menu window.");
        JavaFXApp.stage = stage;
        Scene scene = new Scene(FXMLLoader.load(Resources.getResource("fxml/menu.fxml")));
        stage.setScene(scene);
        stage.setTitle("Catan");
        stage.show();
    }
}
