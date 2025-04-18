package Schedulers;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Iterator;
import process.Process;
public class FCFSScheduler implements Scheduler{
    private Queue<Process> processQueue;

    //Constructor needs to be implemented
    public FCFSScheduler() {
        this.processQueue = new LinkedList<>();
    }
    @Override
    public void addProcess(Process p) {
        if(p == null){
            throw new IllegalArgumentException("Process cannot be null");
        }
        processQueue.add(p);
        System.out.println("Process has been added");
    }

    @Override
    public void removeProcess(int processId) {
        if(processQueue == null){
            System.out.println("Queue is not initialized");
            return;
        }

        if(processQueue.isEmpty()){
            System.out.println("Process queue is empty");
            return;
        }

        Iterator<Process> iterator = processQueue.iterator();
        while(iterator.hasNext()){
            Process p = iterator.next();
            if(p.getPid() == processId){
                iterator.remove();
                System.out.println("Removed process with PID: " + processId);
                return;
            }
        }
        System.out.println("Process with PID " + processId + " not found in queue");
    }

    @Override
    public void execute() {

    }

    @Override
    public Process getNextProcess(){
        return null;
    }

    public List<Process> getScedulerTimeline() {
        return List.of() /*GuI*/;
    }

}