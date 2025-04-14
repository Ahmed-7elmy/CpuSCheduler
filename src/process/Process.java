package process;

public class Process {
   private int pid;
   private int arrivalTime;
   private int burstTime;
   private int remainingTime;
   private int priority;
   private int completionTime;
   private int startTime = -1;

    // Constructor
    public Process(int pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.priority = priority;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public int getPid() {
        return pid;
    }

    public int getPriority() {
        return priority;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public void setCompletionTime(int completionTime){
        this.completionTime = completionTime;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public void setStartTime(int startTime){
        this.startTime = startTime;
    }
}
