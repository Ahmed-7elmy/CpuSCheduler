package scheduler;

import model.Process;
import java.util.*;

public class PriorityScheduler implements Scheduler {
    // preemptive means the scheduler can interrupt a running process
    // non-preemptive means the scheduler will let the current process finish before switching
    private final boolean isPreemptive;

    private final List<Process> allProcesses = new ArrayList<>();
    private final List<Process> completedProcesses = new ArrayList<>();
    private final Queue<Process> readyQueue = new LinkedList<>();

    private int currentTime = 0;
    private Process currentProcess = null;

    public PriorityScheduler(boolean isPreemptive) {
        this.isPreemptive = isPreemptive;
    }

    @Override
    public void addProcess(Process process) {
        allProcesses.add(process);
    }

    @Override
    public void tick() {
        // 1. Add arrived processes to the ready queue
        for (Process p : allProcesses)
            if (p.getArrivalTime() == currentTime)
                readyQueue.add(p);

        // 2. Check if current process is completed
        if (currentProcess != null && currentProcess.getRemainingTime() == 0) {
            currentProcess.setCompletionTime(currentTime);
            completedProcesses.add(currentProcess);
            currentProcess = null;
        }

        // 3. Select the next process (preemptive or not)
        if (isPreemptive || currentProcess == null)
            currentProcess = selectHighestPriority();

        // 4. Run the process if available
        if (currentProcess != null) {
            decrementRemainingTime();
            if (currentProcess.getStartTime() == -1)
                currentProcess.setStartTime(currentTime);
        }
        currentTime++;
    }

    private Process selectHighestPriority() {
        List<Process> available = new ArrayList<>();

        for (Process p : allProcesses)
            if (p.getArrivalTime() <= currentTime && !completedProcesses.contains(p) && p.getRemainingTime() > 0)
                available.add(p);

        return available.stream()
                .min(Comparator.comparing(Process::getPriority).thenComparing(Process::getArrivalTime))
                .orElse(null);
    }
    
    @Override
    public void runCurrentProcesses() {
        // Run all processes non-live (no tick)
        while (!isDone()) {
            for (Process p : allProcesses) {
                // Run the process to completion
                while (p.getRemainingTime() > 0)
                    p.setRemainingTime(p.getRemainingTime() - 1);

                p.setCompletionTime(currentTime);
                completedProcesses.add(p);
                currentTime++;
            }
        }
    }

    @Override
    public void decrementRemainingTime() {
        if (currentProcess != null)
            currentProcess.setRemainingTime(currentProcess.getRemainingTime() - 1);
    }

    @Override
    public boolean isDone() {
        return completedProcesses.size() == allProcesses.size();
    }

    // Getters for testing and debugging
    @Override
    public List<Process> getAllProcesses() {
        return allProcesses;
    }

    @Override
    public Process getCurrentProcess() {
        return currentProcess;
    }

    @Override
    public List<Process> getCompletedProcesses() {
        return completedProcesses;
    }

    @Override
    public int getCurrentTime() {
        return currentTime;
    }
}
