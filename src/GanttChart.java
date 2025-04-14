import model.Process;
import java.util.List;

public class GanttChart {
    public static void printGanttChart(List<Process> completedProcesses) {
        StringBuilder chart = new StringBuilder();
        StringBuilder timeline = new StringBuilder();

        int currentTime = 0;

        for (Process process : completedProcesses) {
            int startTime = process.getStartTime();
            int completionTime = process.getCompletionTime();

            // Add idle time if there's a gap
            if (startTime > currentTime) {
                chart.append(" | Idle ");
                timeline.append(String.format("%-7d", currentTime));
                currentTime = startTime;
            }

            // Add the process to the chart
            chart.append(" | ").append(process.getPid());
            timeline.append(String.format("%-7d", currentTime));
            currentTime = completionTime;
        }

        // Add the final time marker
        timeline.append(String.format("%-7d", currentTime));

        // Print the Gantt chart
        System.out.println("Gantt Chart:");
        System.out.println(chart.append(" |"));
        System.out.println(timeline);
    }
}