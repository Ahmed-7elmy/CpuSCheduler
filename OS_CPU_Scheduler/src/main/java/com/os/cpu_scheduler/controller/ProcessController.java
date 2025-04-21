package com.os.cpu_scheduler.controller;

import com.os.cpu_scheduler.model.Process;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ProcessController {

    @FXML
    private Label additionalFieldLabel;


    @FXML
    private VBox additionalSection;

    @FXML
    private TextField additionalTextField;

    @FXML
    private TextField arrivalField;

    @FXML
    private TextField burstField;

    @FXML
    private TextField IdField;

    @FXML
    private TableColumn<com.os.cpu_scheduler.model.Process, Integer> P_col;

    @FXML
    private TableView<com.os.cpu_scheduler.model.Process> P_table;
    @FXML
    private TableColumn<com.os.cpu_scheduler.model.Process, Integer> ArriveTimeCol;

    @FXML
    private TableColumn<com.os.cpu_scheduler.model.Process, Integer> BurstTimeCol;

    @FXML
    private TableColumn<com.os.cpu_scheduler.model.Process, Integer> PriorityCol;

    // Global variables
    private int quantum = 0;
    private boolean quantumSet = false;
    ObservableList<com.os.cpu_scheduler.model.Process> processes = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        P_col.setCellValueFactory(new PropertyValueFactory<com.os.cpu_scheduler.model.Process, Integer>("pid"));

        ArriveTimeCol.setCellValueFactory(new PropertyValueFactory<com.os.cpu_scheduler.model.Process, Integer>("arrivalTime"));
        ArriveTimeCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        ArriveTimeCol.setOnEditCommit(e -> {
            com.os.cpu_scheduler.model.Process p = e.getRowValue();
            p.setArrivalTime(e.getNewValue());
        });

        BurstTimeCol.setCellValueFactory(new PropertyValueFactory<com.os.cpu_scheduler.model.Process, Integer>("burstTime"));
        BurstTimeCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        BurstTimeCol.setOnEditCommit(e -> {
            com.os.cpu_scheduler.model.Process p = e.getRowValue();
            p.setBurstTime(e.getNewValue());
        });

        PriorityCol.setCellValueFactory(new PropertyValueFactory<com.os.cpu_scheduler.model.Process, Integer>("priority"));
        PriorityCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        PriorityCol.setOnEditCommit(e -> {
            com.os.cpu_scheduler.model.Process p = e.getRowValue();
            p.setPriority(e.getNewValue());
        });

        additionalSection.managedProperty().bind(additionalSection.visibleProperty());

        // Show fields depending on the algorithm
        if(HelloController.Algorithm.contains("Priority")){
            additionalFieldLabel.setText("Priority");
            additionalSection.setVisible(true);
            PriorityCol.setVisible(true);
        } else if (HelloController.Algorithm.contains("Round")) {
            additionalFieldLabel.setText("Quantum");
            additionalSection.setVisible(true);
        }
    }
    @FXML
    void addProcess(ActionEvent event) {
        boolean safe = true;
        if(IdField.getText().isEmpty() || burstField.getText().isEmpty() || arrivalField.getText().isEmpty()) {
            safe = false;
            showError("Please Fill All The Fields.");
        }
        if (HelloController.Algorithm.contains("Priority") || (HelloController.Algorithm.contains("Round") && !quantumSet)) {
            if(additionalTextField.getText().isEmpty()) {
                safe = false;
                showError("Please Fill All The Fields.");
            }
        }

        if(safe){
            for (com.os.cpu_scheduler.model.Process proc : processes) {
                if (proc.getPid() == Integer.parseInt(IdField.getText())) {
                    safe = false;
                    showError("This PID Already Exists.");
                }
            }
            if(Integer.parseInt(IdField.getText()) < 0 || Integer.parseInt(arrivalField.getText()) < 0) {
                safe =  false;
                showError("Only Non Negative Integers Allowed.");
            }
            if (Integer.parseInt(burstField.getText()) <= 0) {
                safe = false;
                showError("Burst Time Should Be A Positive Integer.");
            }
            if (HelloController.Algorithm.contains("Priority")) {
                if(Integer.parseInt(additionalTextField.getText()) < 0
                        || Integer.parseInt(additionalTextField.getText()) > 10) {
                    safe = false;
                    showError("Priority Should Be Between 0 And 10.");
                }
            }
            if(HelloController.Algorithm.contains("Round") && !quantumSet) {
                if (Integer.parseInt(additionalTextField.getText()) <= 0) {
                    safe = false;
                    showError("Quantum Should Be A Positive Integer.");
                }
            }
        }
        com.os.cpu_scheduler.model.Process process;
        if(safe) {
            if (HelloController.Algorithm.contains("Priority")) {
                process = new com.os.cpu_scheduler.model.Process(
                        Integer.parseInt(IdField.getText()),
                        Integer.parseInt(arrivalField.getText()),
                        Integer.parseInt(burstField.getText()),
                        Integer.parseInt(additionalTextField.getText())
                );
                additionalTextField.clear();
            } else {
                process = new com.os.cpu_scheduler.model.Process(
                        Integer.parseInt(IdField.getText()),
                        Integer.parseInt(arrivalField.getText()),
                        Integer.parseInt(burstField.getText())
                );

                if (HelloController.Algorithm.contains("Round") && quantum == 0) {
                    quantum = Integer.parseInt(additionalTextField.getText());
                    additionalSection.setDisable(true);
                    quantumSet = true;
                }
            }

            P_table.getItems().add(process);
            processes = P_table.getItems();

            // Clearing the fields
            IdField.clear();
            arrivalField.clear();
            burstField.clear();
        }

    }

    @FXML
    void removeProcess(ActionEvent event) {
        com.os.cpu_scheduler.model.Process selected = P_table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            P_table.getItems().remove(selected);
        } else {
            showError("Please select a process to remove.");
        }
    }
    @FXML
    void notLiveScheduling(ActionEvent event) throws IOException {
        if (!P_table.getItems().isEmpty()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/os/cpu_scheduler/NotLive.fxml"));
            Stage processStage = new Stage();
            processStage.setTitle("CPU Scheduler");
            processStage.setScene(new Scene((Pane)loader.load()));
            processStage.show();
        }
        else
            showError("Table is not initialized.");
    }

    private ArrayList<com.os.cpu_scheduler.model.Process> getTableProcesses() {
        ArrayList<com.os.cpu_scheduler.model.Process> processesQueue = new ArrayList<com.os.cpu_scheduler.model.Process>();
        processes =  P_table.getItems();
        for (com.os.cpu_scheduler.model.Process process : processes) {
            processesQueue.add((Process)process.clone());
        }
        processesQueue.sort((p1, p2) -> p1.getArrivalTime() - p2.getArrivalTime());

        return processesQueue;
    }

    @FXML
    void LiveScheduling(ActionEvent event) throws IOException {
        if (!P_table.getItems().isEmpty()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/os/cpu_scheduler/LivePage.fxml"));
            Stage LiveStage = new Stage();
            Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/os/cpu_scheduler/icon.png")));
            LiveStage.getIcons().add(icon);
            LiveStage.setTitle("CPU Scheduler");
            LiveStage.setScene(new Scene((Pane)loader.load()));
            LiveStage.show();
            LiveController data = loader.<LiveController>getController();
            data.receivedData(this.getTableProcesses(), quantum);
            LiveStage.show();
        }
        else
            showError("Table is not initialized.");
    }

    static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
