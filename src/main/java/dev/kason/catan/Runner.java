package dev.kason.catan;

import com.google.common.io.Resources;
import dev.kason.catan.ui.JavaFXApp;
import javafx.application.Application;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.lang.management.ManagementFactory;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Slf4j
public class Runner {
    public static void main(String[] args) throws Exception {
        Resources.readLines(Resources.getResource("banner.txt"), StandardCharsets.UTF_8).forEach(System.out::println);
        log.info("Starting Catan with args: {} (JVM has been running for {} ms)", Arrays.toString(args), ManagementFactory.getRuntimeMXBean().getUptime());
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        Application.launch(JavaFXApp.class, args);
    }
}