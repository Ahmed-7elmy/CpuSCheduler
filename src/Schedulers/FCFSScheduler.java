package Schedulers;

import java.util.*;

import process.Process;

public class FCFSScheduler implements Scheduler{
    private Queue<Process> processQueue;
    private List<Process> timeline;
    private int currentTime = 0;
    private int totalWaitingTime;
    private int totalTurnaroundTime;


    private PriorityQueue<Process> processList= new PriorityQueue<>(new Comparator<Process>() {
        public int compare(Process p1, Process p2){
            return Integer.compare(p1.getArrivalTime(),p2.getArrivalTime());
        }});

    public FCFSScheduler() {
        this.processQueue = new LinkedList<>();
    }
    @Override
    public void addProcess(Process p) {
        if(p == null){
            throw new IllegalArgumentException("Process cannot be null");
        }
        processQueue.add(p);
    }

    @Override
    public void removeProcess(int processId) {
        if(processQueue == null || processQueue.isEmpty()){
            return;
        }

        Iterator<Process> iterator = processQueue.iterator();
        while(iterator.hasNext()){
            Process p = iterator.next();
            if(p.getPid() == processId){
                iterator.remove();
                return;
            }
        }
    }

    @Override
    public void execute() {
        if(processQueue == null || processQueue.isEmpty()){
            return;
        }

        while (!processList.isEmpty()) {
            Process current = processList.poll();

            int startTime = Math.max(currentTime, current.getArrivalTime());
            int completionTime = startTime + current.getBurstTime();
            current.setStartTime(startTime);
            current.setCompletionTime(completionTime);
            for(int i = startTime; i <= completionTime; i++) {
                timeline.add(current);
            }
            currentTime = completionTime;
        }
    }

    @Override
    public List<Process> getSchedulerTimeline() {
        return timeline /*GuI*/;
    }
}