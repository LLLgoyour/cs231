
/*
 * file name: ExperimentRunner.java
 * author: Jack Dai
 * last modified: 10/22/2025
 * purpose of the class:
 * Compact experiment runner that compares several JobDispatcher
 * implementations (LeastWork, Slowdown, ShortestQueue) while sweeping
 * the number of servers. Results are written to a tab-separated file
 * named `experiment_results.txt` and also printed to stdout.
 */
import java.io.FileWriter;
import java.io.PrintWriter;

public class ExperimentRunner {

    /**
     * Run a sweep of server counts and record average waiting time for each
     * dispatcher.
     *
     * @param args
     * @throws Exception on I/O errors or other runtime issues
     */

    // compact experiment: run several dispatchers, repeat and write avg waits
    public static void main(String[] args) throws Exception {
        int minServers = 30;
        int maxServers = 41; // inclusive
        int numJobs = 1000000; // per run (reasonable default)
        double meanArrival = 3.0;
        double meanProc = 100.0;

        String[] names = { "least", "slowdown", "short" };

        try (PrintWriter out = new PrintWriter(new FileWriter("experiment_results.txt"))) {
            out.println("Servers\tDispatcher\tAvgWait");
            for (int numServers = minServers; numServers <= maxServers; numServers++) {
                for (String name : names) {
                    JobMaker maker = new JobMaker(meanArrival, meanProc, 1000);
                    JobDispatcher disp = makeDispatcher(name, numServers, false, meanProc);
                    for (int i = 0; i < numJobs; i++) {
                        disp.handleJob(maker.getNextJob());
                    }
                    disp.finishUp();
                    out.printf("%d\t%s\t%.6f\n", numServers, name, disp.getAverageWaitingTime());
                    System.out.printf("servers=%d %s -> %.6f\n", numServers, name, disp.getAverageWaitingTime());
                }
            }
        }
    }

    /**
     * Factory helper to construct a JobDispatcher by name
     *
     * @param name     key identifying the dispatcher implementation
     * @param k        number of servers to create/manage
     * @param viz      whether to enable visualization
     * @param meanProc mean processing-time
     * @return a configured JobDispatcher instance
     */
    private static JobDispatcher makeDispatcher(String name, int k, boolean viz, double meanProc) {
        switch (name) {
            case "least":
                return new LeastWorkDispatcher(k, viz);
            case "slowdown":
                // use default alpha
                return new SlowdownDispatcher(k, viz);
            case "short":
                return new ShortestQueueDispatcher(k, viz);
            default:
                return new RandomDispatcher(k, viz);
        }
    }
}
