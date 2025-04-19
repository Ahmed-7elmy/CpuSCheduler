package com.example.os_cpu_scheduler;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private Button nextBtn;

    static String Algorithm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBox.getItems().addAll("FCFS", "Round Robin", "SJF Preemptive", "SJF Non-Preemptive", "Priority Preemptive", "Priority Non-Preemptive");
    }

    @FXML
    void onNextBtn(ActionEvent event) throws IOException {
        Algorithm = choiceBox.getValue();
        if(Algorithm != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Process.fxml"));
            Stage processStage = new Stage();
            processStage.setTitle("CPU Scheduler");
            processStage.setScene(new Scene((Pane)loader.load()));
            processStage.show();
        }
        else{
            ProcessController.showError("Algorithm is not selected");
        }
    }
}
