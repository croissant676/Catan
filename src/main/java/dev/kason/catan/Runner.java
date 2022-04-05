package dev.kason.catan;

import com.google.common.io.Resources;
import dev.kason.catan.ui.JavaFXApp;
import javafx.application.Application;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.nio.charset.StandardCharsets;

@Slf4j
public class Runner {
    @SneakyThrows
    public static void main(String[] args) {
        Resources.readLines(
                Resources.getResource("banner.txt"),
                StandardCharsets.UTF_8
        ).forEach(System.out::println);
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        log.info("Starting Catan ... ");
        Application.launch(JavaFXApp.class, args);
    }
}