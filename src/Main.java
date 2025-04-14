
import model.Process;
import scheduler.PriorityScheduler;
import scheduler.Scheduler;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Instantiate the scheduler (preemptive or non-preemptive)
        boolean isPreemptive = true;  // Change to false for non-preemptive
        Scheduler scheduler = new PriorityScheduler(isPreemptive);

        // 2. Create processes
        List<Process> processes = List.of(
                new Process("P1", 0, 10, 3), // Process ID, Arrival Time, Burst Time, Priority
                new Process("P2", 1, 5, 2),  // Higher priority (smaller number)
                new Process("P3", 2, 8, 1),  // Highest priority
                new Process("P4", 4, 6, 4)   // Lower priority
        );

        // 3. Add processes to the scheduler
        for (Process process : processes)
            scheduler.addProcess(process);


        // print the processes in a table
        System.out.println("Processes:");
        System.out.printf("%-15s %-15s %-15s %-15s\n", "Process ID", "Arrival Time", "Burst Time", "Priority");
        System.out.println("--------------------------------------------------------");
        for (Process p : scheduler.getAllProcesses())
            System.out.printf("%-15s %-15s %-15s %-15s\n", p.getPid(), p.getArrivalTime(), p.getBurstTime(), p.getPriority());
        System.out.println("--------------------------------------------------------");

        System.out.println("Scheduler Type: " + (isPreemptive ? "Preemptive" : "Non-Preemptive"));
        System.out.println("--------------------------------------------------------");

        // 4. Print the scheduler timeline
        System.out.println("Scheduler Timeline:");
        System.out.printf("%-15s %-15s %-15s %-15s\n", "Time", "Process ID", "Remaining Time", "Priority");
        System.out.println("--------------------------------------------------------");
        while (!scheduler.isDone()) {
            scheduler.tick();
            Process current = scheduler.getCurrentProcess();
            if (current != null)
                System.out.printf("%-15s %-15s %-15s %-15s\n", scheduler.getCurrentTime(), current.getPid(), current.getRemainingTime(), current.getPriority());
        }

        // 5. After all processes are completed, print the results
        List<Process> completedProcesses = scheduler.getCompletedProcesses();
        System.out.println("\nCompleted Processes:");
        for (Process p : completedProcesses) {
            System.out.println(p.getPid());
        }
    }
}
