package Schedulers;
import java.util.*;

import process.Process;

public class RoundRobinLive {
    private int timeQuantum;
    public List<Process> allProcesses = Collections.synchronizedList(new ArrayList<>());
    private boolean running = true;

    public RoundRobinLive(int timeQuantum) {
        this.timeQuantum = timeQuantum;
    }

    public void addProcess(Process p) {
        allProcesses.add(p);
    }

    public void execute() {
        new Thread(() -> {
            int currentTime = 0;
            Queue<Process> readyQueue = new LinkedList<>();
            Set<Integer> arrivedPIDs = new HashSet<>();

            while (running) {
                synchronized (allProcesses) {
                    for (Process p : allProcesses) {
                        if (p.getArrivalTime() <= currentTime && !arrivedPIDs.contains(p.getPid())) {
                            readyQueue.add(p);
                            arrivedPIDs.add(p.getPid());
                        }
                    }
                }

                if (!readyQueue.isEmpty()) {
                    Process current = readyQueue.poll();
                    int runTime = Math.min(timeQuantum, current.getRemainingTime());

                    for (int i = 0; i < runTime; i++) {
                        System.out.println("Running P" + current.getPid() + " (t=" + currentTime + ")");
                        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
                        currentTime++;

                        synchronized (allProcesses) {
                            for (Process p : allProcesses) {
                                if (p.getArrivalTime() <= currentTime && !arrivedPIDs.contains(p.getPid())) {
                                    readyQueue.add(p);
                                    arrivedPIDs.add(p.getPid());
                                }
                            }
                        }
                    }

                    current.setRemainingTime(current.getRemainingTime() - runTime);
                    if (current.getRemainingTime() > 0) {
                        readyQueue.add(current);
                    } else {
                        System.out.println("P" + current.getPid() + " finished at time " + (currentTime-1));
                    }
                } else {
                    System.out.println("CPU Idle at time " + currentTime);
                    try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
                    currentTime++;
                }
            }
        }).start();
    }

    public void stop() {
        running = false;
    }
}
