package com.os.cpu_scheduler.scheduler;

import com.os.cpu_scheduler.model.Process;
import java.util.LinkedList;

public class PriorityScheduler extends Scheduler {
    private final boolean isPreemptive;
    private Process runningProcess;
    
    public PriorityScheduler(boolean isPreemptive) {
        super(new LinkedList<>());
        this.isPreemptive = isPreemptive;
    }
    
    @Override
    public Process fetchNextSlot(int currentTime) {
        // preemptive: scheduler can interrupt the running process if a higher priority process arrives
        // non-preemptive: scheduler will let the running process finish then choose the one with the highest priority
        Process highestPriority;
        
        if (!isPreemptive && runningProcess != null && !completedProcesses.contains(runningProcess))
            highestPriority = runningProcess;
        else {
            highestPriority = selectHighestPriorityProcess();
            runningProcess = highestPriority;
        }
        
        // If no process is available, return process with pid = -1
        if (highestPriority == null)
            return new Process(-1);
        
        // Set start time if not already started
        if (highestPriority.getStartTime() == -1)
            highestPriority.setStartTime(currentTime);
        
        // Decrement remaining time (we're simulating 1 unit of CPU currentTime)
        highestPriority.decrement(1);
        
        // If a process is done, mark it complete and remove from queue
        if (highestPriority.getRemainingTime() == 0) {
            highestPriority.setCompletionTime(currentTime + 1);
            queue.remove(highestPriority);
            completedProcesses.add(highestPriority);
            runningProcess = null;
        }
        return highestPriority;
    }
    
    private Process selectHighestPriorityProcess() {
        Process highestPriority = null;
        
        for (Process p : queue)
            if (!completedProcesses.contains(p))
                if (highestPriority == null || p.getPriority() < highestPriority.getPriority() ||
                        (p.getPriority() == highestPriority.getPriority() && p.getArrivalTime() < highestPriority.getArrivalTime()))
                    highestPriority = p;
        
        return highestPriority;
    }
}
