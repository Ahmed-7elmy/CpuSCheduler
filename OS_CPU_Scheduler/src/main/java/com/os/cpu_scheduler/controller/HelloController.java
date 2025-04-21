package com.os.cpu_scheduler.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    
    @FXML
    private ChoiceBox<String> choiceBox;
    static String Algorithm;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBox.getItems().addAll("FCFS", "Round Robin", "SJF Preemptive", "SJF Non-Preemptive", "Priority Preemptive", "Priority Non-Preemptive");
    }
    
    @FXML
    void onNextBtn(ActionEvent event) throws IOException {
        Algorithm = choiceBox.getValue();
        if (Algorithm != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/os/cpu_scheduler/Process.fxml"));
            Stage processStage = new Stage();
            Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/os/cpu_scheduler/icon.png")));
            processStage.getIcons().add(icon);
            processStage.setTitle("CPU Scheduler");
            processStage.setScene(new Scene((Pane) loader.load()));
            processStage.show();
        } else
            ProcessController.showError("Algorithm is not selected");
    }
}
