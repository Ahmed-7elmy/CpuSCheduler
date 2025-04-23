package com.os.cpu_scheduler.scheduler;

import com.os.cpu_scheduler.model.Process;

import java.util.*;


public class SJFScheduler extends Scheduler {
    private final boolean isPreemptive;
    private final Queue<Process> timeline = new LinkedList<>();
    private final List<Process> allProcesses = new ArrayList<>();
    private final List<Process> processBuffer = new ArrayList<>();
    private final List<Process> readyQueue = new ArrayList<>();
    
    //private int currentTime = 0;
    private Process executingProcess = null;      // Used in non-preemptive mode
    private int executionTimeLeft = 0;
    
    public SJFScheduler(boolean isPreemptive) {
        super(new LinkedList<>());
        this.isPreemptive = isPreemptive;
    }
    
    
    @Override
    public void addProcess(Process p) {
        processBuffer.add(p);
    }
    
    
    public void removeProcess(int Process_Id) {
        processBuffer.removeIf(p -> p.getPid() == Process_Id);
        allProcesses.removeIf(p -> p.getPid() == Process_Id);
        readyQueue.removeIf(p -> p.getPid() == Process_Id);
        if (executingProcess != null && executingProcess.getPid() == Process_Id) {
            executingProcess = null;
            executionTimeLeft = 0;
        }
    }
    
    public void execute(int currentTime) {
        allProcesses.addAll(processBuffer);
        for (Process p : processBuffer) {
            p.setRemainingTime(p.getBurstTime());
        }
        processBuffer.clear();
        
        for (Process p : allProcesses) {
            if (p.getArrivalTime() <= currentTime && !readyQueue.contains(p) && p.getRemainingTime() > 0) {
                readyQueue.add(p);
            }
        }
        
        if (isPreemptive) {
            executePreemptive(currentTime);
        } else {
            executeNonPreemptive(currentTime);
        }
    }
    
    private void executePreemptive(int currentTime) {
        Process currentProcess = readyQueue.stream()
                .filter(p -> p.getRemainingTime() > 0)
                .min(Comparator.comparingInt(Process::getRemainingTime))
                .orElse(null);
        
        if (currentProcess != null) {
            if (currentProcess.getStartTime() == -1)
                currentProcess.setStartTime(currentTime);
            
            Process running = new Process(currentProcess);
            running.setStartTime(currentTime);
            //running.setBurstTime(1);
            timeline.add(running);
            
            currentProcess.setRemainingTime(currentProcess.getRemainingTime() - 1);
            
            if (currentProcess.getRemainingTime() == 0) {
                currentProcess.setCompletionTime(currentTime + 1);
                completedProcesses.add(currentProcess);
                readyQueue.remove(currentProcess);
            }
        }
        
    }
    
    private void executeNonPreemptive(int currentTime) {
        if (executingProcess == null) {
            executingProcess = readyQueue.stream()
                    .filter(p -> p.getRemainingTime() > 0)
                    .min(Comparator.comparingInt(Process::getBurstTime))
                    .orElse(null);
            
            if (executingProcess != null) {
                if (executingProcess.getStartTime() == -1)
                    executingProcess.setStartTime(currentTime);
                executionTimeLeft = executingProcess.getBurstTime();
            }
        }
        
        if (executingProcess != null) {
            Process slice = new Process(executingProcess);
            slice.setStartTime(currentTime);
            //slice.setBurstTime(1);
            timeline.add(slice);
            
            executingProcess.setRemainingTime(executingProcess.getRemainingTime() - 1);
            executionTimeLeft--;
            
            if (executionTimeLeft == 0) {
                executingProcess.setCompletionTime(currentTime + 1);
                completedProcesses.add(executingProcess);
                readyQueue.remove(executingProcess);
                executingProcess = null;
            }
            
        }
    }
    
    @Override
    public Process fetchNextSlot(int currentTime) {
        execute(currentTime);
        if (timeline.isEmpty()) {
            return new Process(-1, 0, 0, 0);
        }
        if (timeline.peek().getStartTime() > currentTime) {
            return new Process(-1, 0, 0, 0);
        }
        return timeline.poll();
    }
    
    public boolean isFinished() {
        return allProcesses.stream().allMatch(p -> p.getRemainingTime() == 0);
    }
    
}