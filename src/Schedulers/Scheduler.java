package Schedulers;
import java.util.List;
import java.util.Queue;

import process.Process;
public interface Scheduler {
    void addProcess(Process p);
    void removeProcess(int Process_Id);
    void execute();
    Process getNextProcess();
}
