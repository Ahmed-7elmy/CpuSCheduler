//package Schedulers;
//
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Queue;
//
//import process.Process;
//
//public class RoundRobinScheduler implements Scheduler {
//    private Queue<Process> processQueue;
//    private int timeQuantum;//timing period
//    private List<Process> timeline;//GUI
//
//    public RoundRobinScheduler(int timeQuantum) {
//        this.timeQuantum = timeQuantum;
//        this.processQueue = new LinkedList<>();//I Think Queue is Better
//        this.timeline = new ArrayList<>();
//    }
//
//    @Override
//    public void addProcess(Process p) {
//        processQueue.add(p);
//
//    }
//
//    @Override
//    public void removeProcess(int Process_Id) {//will not be used I think
//        processQueue.removeIf(p -> p.getPid() == Process_Id);
//
//    }
//
//    @Override
//    public List<Process> getScedulerTimeline() {
//        return timeline;//for GUI
//    }
//
//    @Override
//    public void execute() {
//        int currentTime = 0;
//        Queue<Process> queue = new LinkedList<>(processQueue);
//
//        while (!queue.isEmpty()) {
//            Process current_process = queue.poll();// pop in java
//
//            if (current_process.getStartTime() == -1) {
//                current_process.setStartTime(currentTime);
//            }
//
//
//            int runTime = Math.min(current_process.getRemainingTime(), timeQuantum);
//            current_process.setRemainingTime(current_process.getRemainingTime() - runTime);//fadel mn time
//            currentTime += runTime;
//
//            Process execSlice = new Process(
//                    current_process.getPid(),
//                    current_process.getArrivalTime(),
//                    runTime,
//                    current_process.getPriority()
//            );
//            execSlice.setStartTime(currentTime - runTime);
//            execSlice.setEndTime(currentTime);
//            timeline.add(execSlice);
//
//
//            if (current_process.getRemainingTime() > 0) {
//                queue.add(current_process); // Put it back in queue if not finished
//            } else {
//                current_process.setEndTime(currentTime); // Mark end time for completed process
//            }
//        }
//    }
//
//}
////ChatGBTforGUI enhancement
//
