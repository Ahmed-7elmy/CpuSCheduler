package com.os.cpu_scheduler.scheduler;

import com.os.cpu_scheduler.model.Process;

import java.util.LinkedList;

public class FCFSScheduler extends Scheduler {
    public FCFSScheduler() {
        super(new LinkedList<Process>());
    }

    public Process fetchNextSlot(int time) {
        Process running = queue.peek();
        if (running == null) {
            return new Process(-1);
        }

        if (running.getStartTime() == -1)
            running.setStartTime(time);

        running.decrement(1);

        if (running.getRemainingTime() == 0) {
            running.setCompletionTime(time + 1);
            completedProcesses.add(queue.poll());
            return running;
        }

        return running;
    }
}