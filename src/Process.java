public class Process {
    int pid;
    int arrivalTime;
    int burstTime;
    int remainingTime;
    int priority;

    // Constructor
    public Process(int pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.priority = priority;
    }

}
