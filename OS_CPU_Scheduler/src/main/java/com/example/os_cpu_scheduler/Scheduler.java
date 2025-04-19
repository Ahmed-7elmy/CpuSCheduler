package com.example.os_cpu_scheduler;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public abstract class Scheduler {

    Queue<Process> queue;
    ArrayList<Process> completedProcesses;
    ArrayList<GanttProcess> ganttChart;

    public Scheduler(Queue<Process> queue) {
        this.queue = queue;
        this.completedProcesses = new ArrayList<Process>();
        this.ganttChart = new ArrayList<GanttProcess>();

    }

    public void addProcess(Process p) {
        queue.add(p);
    }

    public abstract Process fetchNextSlot(int time);

    public float calcAvgTurnaroundTime() {
        int sum = 0;
        for (Process p : completedProcesses) {
            sum += p.calcTurnaroundTime();
        }
        return (float) sum / completedProcesses.size();
    }


    public float calcAvgWaitingTime() {
        int sum = 0;
        for (Process p : completedProcesses) {
            sum += p.calcWaitingTime();
        }
        return (float) sum / completedProcesses.size();

    }

    public void printAllProcesses() {
        for (Process p : completedProcesses) {
            System.out.println(p.getPid());
        }
    }

}
