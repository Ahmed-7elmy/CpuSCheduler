package scheduler;

import model.Process;
import java.util.List;

public interface Scheduler {

    // Add a process to the scheduler (initially when adding to the ready queue)
    void addProcess(Process process);

    // Advance time by 1-second and update the process statuses
    void tick();

    // Get the current time in seconds
    int getCurrentTime();

    // Get the current running process (if any)
    Process getCurrentProcess();

    // Get all completed processes (once they finish execution)
    List<Process> getAllProcesses();

    // Get all completed processes (once they finish execution)
    List<Process> getCompletedProcesses();

    // Is the scheduler finished (all processes completed)?
    boolean isDone();

    // Update the remaining burst time for the currently running process
    void decrementRemainingTime();

    // Run the scheduler with the current set of processes without live scheduling
    void runCurrentProcesses();
}