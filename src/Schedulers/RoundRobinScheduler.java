package Schedulers;

import java.util.List;
import java.util.Queue;
import process.Process;
public class RoundRobinScheduler implements Scheduler {
    private Queue<Process> processQueue;
    private int timeQuantum;//timing period
    public RoundRobinScheduler(int timeQuantum) {
        this.timeQuantum = timeQuantum;
    //    processQueue = new LinkedList<>();I Think Queue is Better
    }

    @Override
    public void addProcess(Process p) {

    }

    @Override
    public void removeProcess(int Process_Id) {

    }

    @Override
    public void execute() {

    }

    @Override
    public List<Process> getSchedulerTimeline() {
        return List.of()/*GuI*/;
    }
}
