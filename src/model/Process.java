package model;

public class Process {
    private String pid;
    private int arrivalTime;
    private int burstTime;
    private int remainingTime;

    // Priority is optional — used only in Priority schedulers
    private Integer priority = null;

    private int startTime = -1;
    private int completionTime = -1;

    // Constructor WITHOUT priority (for FCFS, RR)
    public Process(String pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }

    // Constructor WITH priority (for SJF, Priority)
    public Process(String pid, int arrivalTime, int burstTime, int priority) {
        this(pid, arrivalTime, burstTime);
        this.priority = priority;
    }

    // Getters
    public String getPid() {
        return pid;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public Integer getPriority() {
        return priority;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    // Setters
    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public void setStartTime(int startTime) {
        if (this.startTime == -1) {
            this.startTime = startTime;
        }
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    // Calculated Metrics
    public int getTurnaroundTime() {
        return completionTime - arrivalTime;
    }

    public int getWaitingTime() {
        return getTurnaroundTime() - burstTime;
    }

    public boolean hasPriority() {
        return priority != null;
    }
}
