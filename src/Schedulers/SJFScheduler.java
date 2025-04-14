package Schedulers;

import process.Process;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Comparator;

public class SJFScheduler implements Scheduler{
    private boolean isPreemptive;
    private List<Process> timeline = new ArrayList<>();
    private List<Process> processList = new ArrayList<>();


    public SJFScheduler(boolean isPreemptive) {
        this.isPreemptive = isPreemptive;
    }

    @Override
    public void addProcess(Process p) {
        processList.add(p);
    }

    @Override
    public void removeProcess(int Process_Id) {
        processList.removeIf(p->p.getPid() == Process_Id);
    }

    @Override
    public void execute() {
        List<Process> allProcesses = new ArrayList<>(processList);
        List<Process> readyQueue = new ArrayList<>();
        int currentTime = 0;
        int completed = 0;
        int n = allProcesses.size();

        for (Process p : allProcesses) {
            p.setRemainingTime(p.getBurstTime());  // For preemptive use
        }

        while (completed < n) {
            // Add available processes to the ready queue
            for (Process p : allProcesses) {
                if (p.getArrivalTime() <= currentTime && !readyQueue.contains(p) && p.getRemainingTime() > 0) {
                    readyQueue.add(p);
                }
            }

            // Select process with shortest burst or remaining time
            Process currentProcess = null;
            if (!readyQueue.isEmpty()) {
                Comparator<Process> comparator = isPreemptive
                        ? Comparator.comparingInt(Process::getRemainingTime)
                        : Comparator.comparingInt(Process::getBurstTime);

                currentProcess = readyQueue.stream()
                        .filter(p -> p.getRemainingTime() > 0)
                        .min(comparator)
                        .orElse(null);
            }


            if (currentProcess != null) {
                if (currentProcess.getStartTime() == -1) {
                    currentProcess.setStartTime(currentTime);
                }

                // Log the timeline
                Process running = new Process(currentProcess); // Copy to avoid overwriting info
                running.setStartTime(currentTime);
                running.setPid(currentProcess.getPid());
                running.setBurstTime(1); // Only 1 unit executes per cycle
                timeline.add(running);

                currentProcess.setRemainingTime(currentProcess.getRemainingTime() - 1);

                if (currentProcess.getRemainingTime() == 0) {
                    currentProcess.setCompletionTime(currentTime + 1);
                    completed++;
                    readyQueue.remove(currentProcess);
                }

                currentTime++;
            } else {
                currentTime++; // Idle time
            }

            if (!isPreemptive && currentProcess != null) {
                int execTime = currentProcess.getBurstTime();
                for (int i = 0; i < execTime; i++) {
                    Process slice = new Process(currentProcess);
                    slice.setStartTime(currentTime);
                    slice.setBurstTime(1);
                    timeline.add(slice);
                    currentTime++;
                }
                currentProcess.setCompletionTime(currentTime);
                currentProcess.setRemainingTime(0);
                readyQueue.remove(currentProcess);
                completed++;
            }
        }
    }


    @Override
    public List<Process> getScedulerTimeline() {
         return timeline;/*GuI*/
    }
}

