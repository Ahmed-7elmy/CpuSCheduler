package Schedulers;

import java.util.List;

public class SJFScheduler implements Scheduler{
    private boolean isPreemptive;
    private List<Process> processList;//or Vector list

    public SJFScheduler(boolean isPreemptive) {
        this.isPreemptive = isPreemptive;
//        processList = new ArrayList<>();
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
