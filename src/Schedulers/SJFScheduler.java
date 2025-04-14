package Schedulers;

import process.Process;

import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Comparator;

public class SJFScheduler implements Scheduler{
    private boolean isPreemptive;
    private PriorityQueue processList;//or Vector list

    public SJFScheduler(boolean isPreemptive) {
        this.isPreemptive = isPreemptive;
        processList = new PriorityQueue<>(new Comparator<Process>() {
       public int compare(Process p1, Process p2){
           return Integer.compare(p1.getBurstTime(),p2.getBurstTime());
       } });
    }

    @Override
    public void addProcess(Process p) {

    }

    @Override
    public void removeProcess(int Process_Id) {

    }

    @Override
    public void execute() {

    }

    @Override
    public List<Process> getScedulerTimeline() {
        return List.of()/*GuI*/;
    }
}
