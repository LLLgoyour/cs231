/*
 * file name: SlowdownDispatcher.java
 * author name: Jack Dai
 * last modified: 10/22/2025
 * purpose of the class:
 * A dispatcher that routes an incoming {@link Job} to the server minimizing an
 * estimated slowdown score. The heuristic estimates the slowdown a job would
 * experience if placed on a server by combining the current work ahead on the
 * server and the job's own size, with a small queue-length penalty to avoid
 * creating long lines of tiny jobs.
 *
 * Score formula (smaller is better): 
 * score = 1 + workAhead / size(j) + alpha * queueLen
 *
 */
public class SlowdownDispatcher extends JobDispatcher {

    /** Small penalty applied per queued job to discourage long tiny-job queues. */
    private final double alpha;

    /**
     * Create a SlowdownDispatcher with a default alpha (0.10).
     *
     * @param k       number of servers to create/manage
     * @param showViz whether to enable visualization (passed to superclass)
     */
    public SlowdownDispatcher(int k, boolean showViz) {
        this(k, showViz, 0.10);
    }

    /**
     * Create a SlowdownDispatcher with a tunable alpha parameter.
     *
     * @param k       number of servers to create/manage
     * @param showViz whether to enable visualization (passed to superclass)
     * @param alpha   non-negative queue-length penalty applied to the score
     */
    public SlowdownDispatcher(int k, boolean showViz, double alpha) {
        super(k, showViz);
        this.alpha = Math.max(0.0, alpha);
    }

    /**
     * Choose the server that minimizes the slowdown score for job j
     *
     * @param j the incoming Job to dispatch (not modified)
     * @return the Server that yields the smallest estimated slowdown
     */
    @Override
    public Server pickServer(Job j) {
        Server best = getServerList().get(0);
        double bestScore = score(best, j);
        for (int i = 1; i < getServerList().size(); i++) {
            Server s = getServerList().get(i);
            double sc = score(s, j);
            if (sc < bestScore) {
                best = s;
                bestScore = sc;
            }
        }
        return best;
    }

    /**
     * Compute the slowdown score for placing job j on server s
     *
     * @param s target Server
     * @param j Job being considered
     * @return score where smaller values are preferred
     */
    private double score(Server s, Job j) {
        double size = Math.max(1e-9, j.getProcessingTimeNeeded());
        double workAhead = s.remainingWorkInQueue();
        double slowdown = 1.0 + workAhead / size;
        return slowdown + alpha * s.size();
    }
}
