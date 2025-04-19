package com.example.os_cpu_scheduler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


public class App extends Application {

    private static Scene page;
    private static Stage window;

    static void setRoot(String fxml) throws IOException {
        page = new Scene(loadFXML(fxml));
        window.setScene(page);
        window.setTitle("CPU Scheduler");
        window.centerOnScreen();
        window.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        window = stage;
        page = new Scene(loadFXML("HomePage"));
        window.setTitle("CPU Scheduler");
        Image icon = new Image(getClass().getResourceAsStream("icon.png"));
        window.getIcons().add(icon);
        window.setScene(page);
        window.show();
    }
}