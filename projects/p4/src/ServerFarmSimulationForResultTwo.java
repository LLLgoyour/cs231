/*
file name:      ServerFarmSimulationForResultTwo.java
Authors:        Ike Lage & Jack Dai
last modified:  10/22/2025
*/

import java.io.*;

public class ServerFarmSimulationForResultTwo {

    public static void main(String[] args) {

        // Experiment parameters (fixed for required result)
        int meanArrivalTime = 3; // average time between arrivals
        int meanProcessingTime = 100; // average job processing time

        // By default run the long experiment requested: ShortestQueue, servers 30..40,
        // 10_000_000 jobs
        int startServers = 30;
        int endServers = 40;
        int numJobs = 10_000_000;
        boolean showViz = false; // must be false for large experiments

        // Support a quick mode for local testing: pass "quick" as first arg to run a
        // tiny experiment
        if (args.length > 0 && "quick".equals(args[0])) {
            startServers = 2;
            endServers = 6;
            numJobs = 1000;
            System.out.println("Running in quick test mode (not the full experiment)");
        }

        System.out.println("# ServerFarmSimulation: ShortestQueue dispatcher experiment");
        System.out.println("# meanArrivalTime=" + meanArrivalTime + ", meanProcessingTime=" + meanProcessingTime
                + ", jobsPerRun=" + numJobs + ", showViz=" + showViz);
        System.out.println();

        // Prepare output file
        String outFile = "shortest_queue_results.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))) {
            // Print CSV header (stdout and file)
            String header = "numServers,avgWaitTime";
            System.out.println(header);
            writer.write(header);
            writer.newLine();

            // Run experiments for each server count and write CSV lines
            int count = endServers - startServers + 1;
            double[] avgWaits = new double[count];
            for (int numServers = startServers; numServers <= endServers; numServers++) {
                JobMaker jobMaker = new JobMaker(meanArrivalTime, meanProcessingTime);
                JobDispatcher dispatcher = new ShortestQueueDispatcher(numServers, showViz);

                for (int i = 0; i < numJobs; i++) {
                    dispatcher.handleJob(jobMaker.getNextJob());
                }
                dispatcher.finishUp();

                double avgWait = dispatcher.getAverageWaitingTime();
                avgWaits[numServers - startServers] = avgWait;
                String csvLine = String.format("%d,%.5f", numServers, avgWait);
                System.out.println(csvLine);
                writer.write(csvLine);
                writer.newLine();
            }

            // Also print and write a small ASCII bar chart to help visualize results
            writer.newLine();
            System.out.println();
            System.out.println("ASCII chart (numServers vs avgWaitTime)");
            writer.write("ASCII chart (numServers vs avgWaitTime)");
            writer.newLine();
            System.out.println("numServers | avgWait (bar)");
            writer.write("numServers | avgWait (bar)");
            writer.newLine();

            // Compute max for scaling (use log scale to better show large dynamic range)
            double maxAvg = 0.0;
            for (double v : avgWaits)
                if (v > maxAvg)
                    maxAvg = v;
            double maxLog = Math.log10(maxAvg + 1.0);
            int maxBarLen = 80;

            for (int i = 0; i < avgWaits.length; i++) {
                int numServers = startServers + i;
                double avgWait = avgWaits[i];
                // log scaling to reveal order-of-magnitude differences
                double ratio;
                if (maxLog <= 0) {
                    ratio = (avgWait > 0) ? 1.0 : 0.0;
                } else {
                    ratio = Math.log10(avgWait + 1.0) / maxLog;
                }
                int barLen = (int) Math.round(ratio * maxBarLen);
                if (avgWait > 0 && barLen < 1)
                    barLen = 1; // show at least a tiny bar when >0
                StringBuilder bar = new StringBuilder();
                for (int b = 0; b < barLen; b++)
                    bar.append('#');
                String line = String.format("%10d | %8.3f %s", numServers, avgWait, bar.toString());
                System.out.println(line);
                writer.write(line);
                writer.newLine();
            }

            System.out.println("\nResults also written to: " + outFile);
            writer.newLine();
            writer.write("Results written to: " + outFile);
            writer.newLine();

        } catch (IOException e) {
            System.err.println("Error writing results to file: " + e.getMessage());
            e.printStackTrace();
        }

    }

}