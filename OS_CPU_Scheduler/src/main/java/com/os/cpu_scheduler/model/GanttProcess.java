package com.os.cpu_scheduler.model;

public class GanttProcess {
    int pid;
    int runningTime;


    public GanttProcess(int pid, int runningTime) {
        this.pid = pid;
        this.runningTime = runningTime;
    }

    public GanttProcess(Process p, int runningTime) {
        this(p.getPid(), runningTime);
    }

    public int getPid() {
        return pid;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

    public void increment(int quantum) {
        this.runningTime += quantum;
    }
}
