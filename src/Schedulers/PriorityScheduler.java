package Schedulers;

import java.util.List;
import process.Process;
public class PriorityScheduler implements Scheduler{
    private List<Process> processList;//or Vector
    private boolean isPreemptive;

    public PriorityScheduler(boolean isPreemptive) {
        this.isPreemptive = isPreemptive;
//        processList = new ArrayList<>();search on ArrayList
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
