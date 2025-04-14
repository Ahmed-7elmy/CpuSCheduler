package Schedulers;
import java.util.List;

public interface Scheduler {
    void addProcess(Process p);
    void removeProcess(int Process_Id);
    void execute();
    List<Process>getScedulerTimeline();

}
