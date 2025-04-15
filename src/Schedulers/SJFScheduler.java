package Schedulers;

import process.Process;

import java.util.*;

public class SJFScheduler implements Scheduler {
    private boolean isPreemptive;
    private List<Process> timeline = new ArrayList<>();
    private List<Process> allProcesses = new ArrayList<>();
    private List<Process> processBuffer = new ArrayList<>();
    private List<Process> readyQueue = new ArrayList<>();

    private int currentTime = 0;
    private Process executingProcess = null;      // Used in non-preemptive mode
    private int executionTimeLeft = 0;

    public SJFScheduler(boolean isPreemptive) {
        this.isPreemptive = isPreemptive;
    }

    @Override
    public void addProcess(Process p) {
        processBuffer.add(p);
    }

    @Override
    public void removeProcess(int Process_Id) {
        processBuffer.removeIf(p -> p.getPid() == Process_Id);
        allProcesses.removeIf(p -> p.getPid() == Process_Id);
        readyQueue.removeIf(p -> p.getPid() == Process_Id);
        if (executingProcess != null && executingProcess.getPid() == Process_Id) {
            executingProcess = null;
            executionTimeLeft = 0;
        }
    }
    @Override
    public void execute() {
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
            executePreemptive();
        } else {
            executeNonPreemptive();
        }
    }

    private void executePreemptive() {
        Process currentProcess = readyQueue.stream()
                .filter(p -> p.getRemainingTime() > 0)
                .min(Comparator.comparingInt(Process::getRemainingTime))
                .orElse(null);

        if (currentProcess != null) {
            if (currentProcess.getStartTime() == -1)
                currentProcess.setStartTime(currentTime);

            Process running = new Process(currentProcess);
            running.setStartTime(currentTime);
            running.setBurstTime(1);
            timeline.add(running);

            currentProcess.setRemainingTime(currentProcess.getRemainingTime() - 1);

            if (currentProcess.getRemainingTime() == 0) {
                currentProcess.setCompletionTime(currentTime + 1);
                readyQueue.remove(currentProcess);
            }
        }

        currentTime++;
    }

    private void executeNonPreemptive() {
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
            slice.setBurstTime(1);
            timeline.add(slice);

            executingProcess.setRemainingTime(executingProcess.getRemainingTime() - 1);
            executionTimeLeft--;

            if (executionTimeLeft == 0) {
                executingProcess.setCompletionTime(currentTime + 1);
                readyQueue.remove(executingProcess);
                executingProcess = null;
            }

            currentTime++;
        }
    }

    @Override
    public List<Process> getScedulerTimeline() {
        return timeline;
    }

    public boolean isFinished() {
        return allProcesses.stream().allMatch(p -> p.getRemainingTime() == 0);
    }

    public int getCurrentTime() {
        return currentTime;
    }
}
