package com.os.cpu_scheduler.scheduler;

import java.util.*;
import com.os.cpu_scheduler.model.Process;

public class RRScheduler extends Scheduler {
    private int timeQuantum;
    private Queue<Process> readyQueue;
    private Process currentProcess = null;
    private int currentQuantum = 0;
    
    public RRScheduler(int timeQuantum) {
        super(new LinkedList<>());
        this.timeQuantum = timeQuantum;
        this.readyQueue = super.queue;
    }
    
    @Override
    public void addProcess(Process p) {
        readyQueue.add(p);
    }
    
    @Override
    public Process fetchNextSlot(int time) {
        // If no current process or quantum expired or process is done
        if (currentProcess == null || currentQuantum == timeQuantum || currentProcess.getRemainingTime() == 0) {
            if (currentProcess != null) {
                if (currentProcess.getRemainingTime() > 0) {
                    readyQueue.add(currentProcess); // Put it back at the end
                } else {
                    currentProcess.setCompletionTime(time);
                    completedProcesses.add(currentProcess);
                    System.out.println("P" + currentProcess.getPid() + " finished at time " + time);
                }
            }
            // If no current process
            // ed5ol next process in readyQueue
            currentProcess = readyQueue.poll();
            currentQuantum = 0;
            if (currentProcess != null && currentProcess.getStartTime() == -1) {
                currentProcess.setStartTime(time);
            }
        }
        
        // If still no process
        if (currentProcess == null) {
            return new Process(-1); // Idle
        }
        
        // Run the process for 1 unit
        //running.decrement(1);
        currentProcess.setRemainingTime(currentProcess.getRemainingTime() - 1);
        currentQuantum++;
        
        System.out.println("Running P" + currentProcess.getPid() + " (t=" + time + ")");
        return currentProcess;
    }
}