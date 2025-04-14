package scheduler;

import java.util.List;
import java.util.Queue;

public class FCFSScheduler implements Scheduler{
    private Queue<Process> processQueue;

    //Constructor needs to be implemented
    public FCFSScheduler() {
    }

    @Override
    public void addProcess(model.Process process) {

    }

    @Override
    public void tick() {

    }

    @Override
    public int getCurrentTime() {
        return 0;
    }

    @Override
    public model.Process getCurrentProcess() {
        return null;
    }

    @Override
    public List<model.Process> getAllProcesses() {
        return List.of();
    }

    @Override
    public List<model.Process> getCompletedProcesses() {
        return List.of();
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public void decrementRemainingTime() {

    }

    @Override
    public void runCurrentProcesses() {

    }
}
