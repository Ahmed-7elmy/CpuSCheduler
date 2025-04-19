//package scheduler;
//
//import java.util.List;
//
//public class SJFScheduler implements Scheduler{
//    private List<Process> processList;//or Vector list
//    private boolean isPreemptive;
//
//    public SJFScheduler(boolean isPreemptive) {
//        this.isPreemptive = isPreemptive;
////        processList = new ArrayList<>();
//    }
//
//    @Override
//    public void addProcess(model.Process process) {
//
//    }
//
//    @Override
//    public void tick() {
//
//    }
//
//    @Override
//    public int getCurrentTime() {
//        return 0;
//    }
//
//    @Override
//    public model.Process getCurrentProcess() {
//        return null;
//    }
//
//    @Override
//    public List<model.Process> getAllProcesses() {
//        return List.of();
//    }
//
//    @Override
//    public List<model.Process> getCompletedProcesses() {
//        return List.of();
//    }
//
//    @Override
//    public boolean isDone() {
//        return false;
//    }
//
//    @Override
//    public void decrementRemainingTime() {
//
//    }
//
//    @Override
//    public void runCurrentProcesses() {
//
//    }
//}
