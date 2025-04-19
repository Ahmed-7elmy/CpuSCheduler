package com.example.os_cpu_scheduler;

import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public class Process {
    private int pid;
    private int arrivalTime;
    private int burstTime;
    private int remainingTime;
    private int priority;
    private int completionTime;
    private int startTime = -1;
    private int endTime = -1;
    private Color processColor;

    // Constructor
    public Process(int pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.priority = priority;
        this.completionTime = -1;
        this.startTime = -1;
        this.endTime = -1;
        // Process Color
        double red = Math.random();
        double green = Math.random();
        double blue = Math.random();
        this.processColor = Color.color(red, green, blue);
    }

    public Process(int pid, int arrivalTime, int burstTime) {
        this(pid, arrivalTime, burstTime, 1);
    }

    public Process(int pid) {
        this.pid = pid;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public int getPid() {
        return pid;
    }

    public int getPriority() {
        return priority;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public Color getProcessColor() {
        return processColor;
    }

    public void setProcessColor(Color processColor) {
        this.processColor = processColor;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public void setCompletionTime(int completionTime){
        this.completionTime = completionTime;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public void setStartTime(int startTime){
        this.startTime = startTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public int calcTurnaroundTime() {
        return  completionTime - arrivalTime;
    }

    public int calcWaitingTime() {
        return  completionTime - arrivalTime - burstTime;
    }

    public int calcResponseTime (){
        return startTime - arrivalTime;
    }

    @Override
    protected Object clone() {
        Process ProcessClone = new Process(this.pid, this.arrivalTime, this.burstTime, this.priority);
        return ProcessClone;
    }

    public void decrement(int quantum) {
        if (remainingTime < quantum) {
            remainingTime = 0;
        } else {
            remainingTime -= quantum;
        }
    }
}
