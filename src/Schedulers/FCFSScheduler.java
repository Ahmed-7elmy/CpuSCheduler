package Schedulers;

import java.util.*;

import process.Process;

public class FCFSScheduler implements Scheduler{
    private List<Process> processList;
    private List<Process> timeline;
    private int currentTime = 0;

    public FCFSScheduler() {
    }

    @Override
    public void addProcess(Process p) {
        if(p == null){
            throw new IllegalArgumentException("Process cannot be null");
        }
        processList.add(p);
    }

    @Override
    public void removeProcess(int processId) {
        if(processList.isEmpty()){
            return;
        }

        Iterator<Process> iterator = processList.iterator();
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
        Queue<Process> processQueue = new PriorityQueue<>(new Comparator<Process>() {
            public int compare(Process p1, Process p2) {
                return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime());
            }
        });

        processQueue.addAll(processList);

        if(processQueue.isEmpty()){
            return;
        }

        while (!processQueue.isEmpty()) {
            Process current = new Process(processQueue.poll());
            int startTime = Math.max(currentTime, current.getArrivalTime());
            int completionTime = startTime + current.getBurstTime();
            current.setStartTime(startTime);
            current.setCompletionTime(completionTime);
            for(int i = startTime; i <= completionTime; i++) {
                current = new Process(current);
                timeline.add(current);
                current.setRemainingTime(current.getRemainingTime()-1);
            }
            currentTime = completionTime;
        }
    }


    @Override
    public List<Process> getSchedulerTimeline() {
        return timeline /*GuI*/;
    }
}