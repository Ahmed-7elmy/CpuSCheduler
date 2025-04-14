package Schedulers;
import java.util.List;
import process.Process;
public interface Scheduler {
    void addProcess(Process p);
    void removeProcess(int Process_Id);
    void execute();
    List<Process>getScedulerTimeline();

}
