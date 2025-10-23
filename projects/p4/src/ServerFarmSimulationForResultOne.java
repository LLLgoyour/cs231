/*
file name:      ServerFarmSimulationForResultOne.java
Authors:        Ike Lage & Jack Dai
last modified:  10/22/2025
*/

import java.io.*;
import java.util.*;

public class ServerFarmSimulationForResultOne {

    public static void main(String[] args) {

        // Experiment configuration (required defaults per assignment)
        int meanArrivalTime = 3; // How often a new job arrives at the server farm, on average
        int meanProcessingTime = 100; // How long a job takes to process, on average

        // Default experiment parameters (these can be overridden via command-line args)
        int numServers = 34; // Numbers of servers in the farm (for required result 1)
        int numJobs = 10000000; // Number of jobs to process for required result 1
        boolean showViz = false; // Must be false for experiments

        // Allow quick testing by passing a single integer arg for smaller job counts
        if (args.length > 0) {
            try {
                numJobs = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("First arg must be an integer (numJobs). Using default " + numJobs);
            }
        }

        // The four dispatcher types to test
        String[] dispatchers = { "random", "round", "shortest", "least" };

        // Prepare JobMaker once per experiment run
        Map<String, Double> results = new LinkedHashMap<>();

        for (String dispatcherType : dispatchers) {
            System.out.println("Running dispatcher: " + dispatcherType + " with " + numServers + " servers and "
                    + numJobs + " jobs...");

            JobMaker jobMaker = new JobMaker(meanArrivalTime, meanProcessingTime);

            JobDispatcher dispatcher = null;
            if (dispatcherType.equals("random")) {
                dispatcher = new RandomDispatcher(numServers, showViz);
            } else if (dispatcherType.equals("round")) {
                dispatcher = new RoundRobinDispatcher(numServers, showViz);
            } else if (dispatcherType.equals("shortest")) {
                dispatcher = new ShortestQueueDispatcher(numServers, showViz);
            } else if (dispatcherType.equals("least")) {
                dispatcher = new LeastWorkDispatcher(numServers, showViz);
            }

            // Process all jobs
            for (int i = 0; i < numJobs; i++) {
                dispatcher.handleJob(jobMaker.getNextJob());
            }
            dispatcher.finishUp(); // Finish remaining jobs

            double avgWait = dispatcher.getAverageWaitingTime();
            results.put(dispatcherType, avgWait);
            System.out.println("Done: " + dispatcherType + " avg wait = " + avgWait);
        }

        // Print a formatted table to stdout and write to results.txt
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-12s | %-16s | %-8s | %-10s\n", "Dispatcher", "AvgWaitTime", "Servers", "Jobs"));
        sb.append("---------------------------------------------------------\n");
        for (Map.Entry<String, Double> e : results.entrySet()) {
            sb.append(String.format("%-12s | %-16.4f | %-8d | %-10d\n", e.getKey(), e.getValue(), numServers, numJobs));
        }

        String table = sb.toString();
        System.out.println("\n=== Experiment results ===\n" + table);

        // Also write to results.txt in the current working directory
        try (FileWriter fw = new FileWriter("results.txt")) {
            fw.write("Experiment: meanArrivalTime=" + meanArrivalTime + ", meanProcessingTime=" + meanProcessingTime
                    + "\n");
            fw.write("Servers=" + numServers + ", Jobs=" + numJobs + "\n\n");
            fw.write(table);
            System.out.println("Results written to results.txt");
        } catch (IOException ioe) {
            System.err.println("Failed to write results.txt: " + ioe.getMessage());
        }

    }

}