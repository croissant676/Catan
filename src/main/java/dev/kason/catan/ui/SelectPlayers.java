package dev.kason.catan.ui;

import com.google.common.io.Resources;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings("unused")
public class SelectPlayers {
    private static final Stage stage = JavaFXApp.getStage();
    @FXML
    private ComboBox<String> selectComboBox;
    @FXML
    private Button nextButton;

    @FXML
    public void initialize() {
        selectComboBox.setItems(FXCollections.observableArrayList("2 Players", "3 Players", "4 Players"));
        nextButton.setOnMouseClicked(this::onStartButton);
    }

    @SneakyThrows
    private void onStartButton(MouseEvent event) {
        log.info("Done setting player amount");
        String selected = selectComboBox.getValue();
        int numPlayers = Integer.parseInt(selected);
        GameState.setCurrent(GameState.SELECT_MAP);
        Scene scene = new Scene(FXMLLoader.load(Resources.getResource("fxml/select_map.fxml")));
        stage.setScene(scene);
    }
}
