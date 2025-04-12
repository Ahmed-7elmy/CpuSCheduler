package Schedulers;

import java.util.List;
import java.util.Queue;

public class FCFSScheduler implements Scheduler{
    private Queue<Process> processQueue;

    //Constructor needss to be implemented
    public FCFSScheduler() {
        //processQueue = Queue or linked List Search;
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
    public List<Process> getScedulerTimeline() {
        return List.of()/*GuI*/;
    }
}
