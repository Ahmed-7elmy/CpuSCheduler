package com.os.cpu_scheduler.controller;

import com.os.cpu_scheduler.model.Process;
import com.os.cpu_scheduler.scheduler.FCFSScheduler;
import com.os.cpu_scheduler.scheduler.PriorityScheduler;
import com.os.cpu_scheduler.scheduler.Scheduler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class LiveController implements Runnable {

    @FXML
    private TextField arrivalField;

    @FXML
    private TextField burstField;

    @FXML
    private TextField IdField;

    @FXML
    private TextField priorityField;

    @FXML
    private VBox prioritySection;

    @FXML
    private Label avgTTLabel;

    @FXML
    private Label avgWTLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Pane ganttChart;

    @FXML
    private Button addProcessBtn;

    @FXML
    private TableView<com.os.cpu_scheduler.model.Process> P_table;

    @FXML
    private TableColumn<com.os.cpu_scheduler.model.Process, Integer> arrivalTimeCol;

    @FXML
    private TableColumn<com.os.cpu_scheduler.model.Process, Integer> burstTimeCol;

    @FXML
    private TableColumn<com.os.cpu_scheduler.model.Process, Integer> endTimeCol;

    @FXML
    private TableColumn<com.os.cpu_scheduler.model.Process, Integer> pidCol;

    @FXML
    private TableColumn<com.os.cpu_scheduler.model.Process, Integer> priorityCol;

    @FXML
    private TableColumn<com.os.cpu_scheduler.model.Process, Integer> remainingTimeCol;

    @FXML
    private TableColumn<com.os.cpu_scheduler.model.Process, Integer> startTimeCol;


    // Global variables
    private ArrayList<com.os.cpu_scheduler.model.Process> processes;
    private Scheduler scheduler;
    private ScheduledFuture<?> t;
    private int time = 0;
    private Text text;
    private boolean sameProcess = false;
    private int xAxis = 14;
    private ArrayList<Shape> holder = new ArrayList<>(2);
    private int last_pid = -1;
    private ObservableList<com.os.cpu_scheduler.model.Process> processesList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Table columns
        pidCol.setCellValueFactory(new PropertyValueFactory<com.os.cpu_scheduler.model.Process, Integer>("pid"));
        priorityCol.setCellValueFactory(new PropertyValueFactory<com.os.cpu_scheduler.model.Process, Integer>("priority"));
        arrivalTimeCol.setCellValueFactory(new PropertyValueFactory<com.os.cpu_scheduler.model.Process, Integer>("arrivalTime"));
        burstTimeCol.setCellValueFactory(new PropertyValueFactory<com.os.cpu_scheduler.model.Process, Integer>("burstTime"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<com.os.cpu_scheduler.model.Process, Integer>("startTime"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<com.os.cpu_scheduler.model.Process, Integer>("completionTime"));
        remainingTimeCol.setCellValueFactory(new PropertyValueFactory<com.os.cpu_scheduler.model.Process, Integer>("remainingTime"));

        P_table.setItems(processesList);

        addProcessBtn.setOnAction(event -> onAddBtnClick());

        prioritySection.managedProperty().bind(prioritySection.visibleProperty());

        if (HelloController.Algorithm.contains("Priority")) {
            priorityCol.setVisible(true);
            prioritySection.setVisible(true);
        }
    }


    public void receivedData(ArrayList<com.os.cpu_scheduler.model.Process> processes, int quantum) {
        this.processes = processes;
        switch (HelloController.Algorithm) {
            case "FCFS" -> scheduler = new FCFSScheduler();
//            case "Round Robin" -> scheduler = new RR(quantum);
//            case "SJF Non-Preemptive" -> scheduler = new SJFNonPreemptive();
//            case "SJF Preemptive" -> scheduler = new SJFPreemptive();
            case "Priority Preemptive" -> scheduler = new PriorityScheduler(true);
            case "Priority Non-Preemptive" -> scheduler = new PriorityScheduler(false);
//            default -> scheduler = new FirstComeFirstServe();
        }

        executScheduler();
    }

    private void calculateAndDisplayAverages() {
        float avgWait = scheduler.calcAvgWaitingTime();
        float avgTurnaround = scheduler.calcAvgTurnaroundTime();

        if (!Float.isNaN(avgWait)) {
            avgWTLabel.setText("Avg. Waiting Time = " + String.format("%.2f", avgWait) + "s");
        } else {
            avgWTLabel.setText("Avg. Waiting Time = ");
        }

        // Check if average turnaround time is not NaN before updating the text
        if (!Float.isNaN(avgTurnaround)) {
            avgTTLabel.setText("Avg. Turnaround Time = " + String.format("%.2f", avgTurnaround) + "s");
        } else {
            avgTTLabel.setText("Avg. Turnaround Time = ");
        }
    }

    @Override
    public void run() {
        try {
            Platform.runLater(() -> {
                while (!processes.isEmpty() && time == processes.get(0).getArrivalTime()) {
                    scheduler.addProcess(processes.get(0));
                    processesList.add(processes.remove(0));
                }

                processesList.sort((p1, p2) -> p1.getStartTime() - p2.getStartTime());

                P_table.refresh();

                com.os.cpu_scheduler.model.Process process = scheduler.fetchNextSlot(time);

                int yAxis = (int)ganttChart.getLayoutBounds().getCenterY();
                int width = 50;
                int length = 30;

                timeLabel.setText("Time = " + time + "s");
                // Calculate average turnaround time and average waiting time
                calculateAndDisplayAverages();

                if ((process.getPid()==-1) && processes.isEmpty()) {
                    if (sameProcess) {
                        ganttChart.getChildren().remove(text);
                    }
                    sameProcess = false;
                    text = new Text(time + "s");
                    text.setFont(new Font(15));
                    text.setX(xAxis - text.getLayoutBounds().getWidth() / 2);
                    text.setY(yAxis + length + text.getLayoutBounds().getHeight());
                    ganttChart.getChildren().addAll(holder);
                    holder.clear();
                    ganttChart.getChildren().add(text);
                    t.cancel(false);
                    return;
                }

                int current_pid = process.getPid();
                Color color = process.getProcessColor();

                if (sameProcess) {
                    ganttChart.getChildren().remove(text);
                    sameProcess = false;
                }

                if (current_pid == last_pid) {
                    sameProcess = true;
                }

                if (!holder.isEmpty()) {
                    ganttChart.getChildren().addAll(holder);
                    holder.clear();
                }

                text = new Text(time + "s");
                text.setFont(new Font(15));
                text.setX(xAxis - text.getLayoutBounds().getWidth() / 2);
                text.setY(yAxis + length + text.getLayoutBounds().getHeight());
                ganttChart.getChildren().add(text);

                Rectangle rectangle = new Rectangle(width, length);
                rectangle.setLayoutX(xAxis);
                rectangle.setLayoutY(yAxis);
                xAxis += width;
                rectangle.setFill(color);
                holder.add(rectangle);

                if (current_pid != last_pid) {
                    Text processName = new Text();
                    if (current_pid == -1) {
                        processName.setText(" ");
                    } else {
                        processName.setText("P" + current_pid);
                    }
                    processName.setFont(new Font(15));
                    processName.setLayoutX(rectangle.getLayoutX() + processName.getLayoutBounds().getWidth());
                    processName.setLayoutY(rectangle.getLayoutY() + processName.getLayoutBounds().getHeight());
                    holder.add(processName);
                }

                last_pid = current_pid;
                time++;
                arrivalField.setText(String.valueOf(time));
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void executScheduler() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        t = executor.scheduleAtFixedRate(this, 0, 1, TimeUnit.SECONDS);
    }

    private void onAddBtnClick(){
        com.os.cpu_scheduler.model.Process process;
        if(IdField.getText().isEmpty() || burstField.getText().isEmpty() || arrivalField.getText().isEmpty()) {
            showError("Please Fill All The Fields.");
            return;
        }
        for (com.os.cpu_scheduler.model.Process proc : processes) {
            if (proc.getPid() == Integer.parseInt(IdField.getText())) {
                showError("This PID Already Exists.");
                return;
            }
        }
        for (com.os.cpu_scheduler.model.Process proc : processesList) {
            if (proc.getPid() == Integer.parseInt(IdField.getText())) {
                showError("This PID Already Exists.");
                return;
            }
        }
        if(Integer.parseInt(IdField.getText()) < 0 || Integer.parseInt(arrivalField.getText()) < 0) {
            showError("Only Non Negative Integers Allowed.");
            return;
        }
        if(Integer.parseInt(IdField.getText()) < 0 || Integer.parseInt(arrivalField.getText()) < 0) {
            showError("Only Non Negative Integers Allowed.");
            return;
        }
        if (Integer.parseInt(arrivalField.getText()) < time) {
            showError("Arrival Time Should Be " + time + " At Least.");
            return;
        }
        if (Integer.parseInt(burstField.getText()) <= 0) {
            showError("Burst Time Should Be A Positive Integer.");
            return;
        }
        if (HelloController.Algorithm.contains("Priority")) {
            if (Integer.parseInt(priorityField.getText()) < 0
                    || Integer.parseInt(priorityField.getText()) > 10) {
                showError("Priority Should Be Between 0 And 10.");
                return;
            }
        }

        if (HelloController.Algorithm.contains("Priority")) {
            process = new com.os.cpu_scheduler.model.Process(
                    Integer.parseInt(IdField.getText()),
                    Integer.parseInt(arrivalField.getText()),
                    Integer.parseInt(burstField.getText()),
                    Integer.parseInt(priorityField.getText())
            );
            priorityField.clear();
        } else {
            process = new Process(
                    Integer.parseInt(IdField.getText()),
                    Integer.parseInt(arrivalField.getText()),
                    Integer.parseInt(burstField.getText())
            );

        }

        processes.add(process);

        IdField.clear();
        arrivalField.clear();
        burstField.clear();

        if(t.isCancelled()) executScheduler();
    }

    static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}