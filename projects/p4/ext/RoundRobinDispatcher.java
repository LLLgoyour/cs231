/*
 * file name: RoundRobinDispatcher.java
 * author: Jack Dai
 * last modified: 10/22/2025
 * Purpose of the class:
 * This is a dispatcher that assigns jobs to servers using a round-robin strategy.
 * Each incoming job is given to the next server in sequence; after the last
 * server is reached, selection wraps back to the first server.
 */

public class RoundRobinDispatcher extends JobDispatcher {

    private int nextIndex;

    /**
     * Create a RoundRobinDispatcher managing k servers.
     *
     * @param k       number of servers to create/manage
     * @param showViz whether to enable visualization (passed to superclass)
     */
    public RoundRobinDispatcher(int k, boolean showViz) {
        super(k, showViz);
        this.nextIndex = 0;
    }

    /**
     * Selects the next server in rotation for the incoming Job.
     *
     * @param j the Job to dispatch (not modified)
     * @return the selected Server for this job
     */
    @Override
    public Server pickServer(Job j) {
        Server s = getServerList().get(nextIndex);
        nextIndex = (nextIndex + 1) % getServerList().size();
        return s;
    }

}
