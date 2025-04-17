import Schedulers.RoundRobinLive;
import Schedulers.RoundRobinScheduler;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import process.Process;


public class Main {
    public static void main(String[] args) {
        System.out.println("Enter Quantum:");
        Scanner s = new Scanner(System.in);
        int result = s.nextInt();
        s.nextLine();
          RoundRobinLive scheduler = new RoundRobinLive(result);
        // Initial processes
        scheduler.addProcess(new Process(1, 0, 5, 1));
        scheduler.addProcess(new Process(2, 2, 4, 1));
        //scheduler.addProcess(new Process(3, 4, 6, 1));

        scheduler.execute();

        // User input thread
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Press Enter to add a new process or type 'exit' to stop:");
                String input = scanner.nextLine();//for enter button
                if (input.equalsIgnoreCase("exit")) {
                    scheduler.stop();
                    System.out.println("Scheduler stopped.");
                    break;
                }
                try {
                    System.out.println("Add new process (pid arrival burst): ");
                    int pid = scanner.nextInt();
                    int arrival = scanner.nextInt();
                    int burst = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    Process newP = new Process(pid, arrival, burst, 1);
                    scheduler.addProcess(newP);
                    System.out.println("Added P" + pid);
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter integers.");
                    scanner.nextLine(); // clear the buffer
                }
            }
        }).start();
    }
    }
