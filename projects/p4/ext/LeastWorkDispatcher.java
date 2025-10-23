/*
 * file name: LeastWorkDispatcher.java
 * author: Jack Dai
 * last modified: 10/22/2025
 * purpose of the class:
 * This dispatcher chooses the server whose total remaining processing time
 * is minimal. This attempts to balance based on remaining workload rather than job count.
 */

public class LeastWorkDispatcher extends JobDispatcher {

    /**
     * Create a LeastWorkDispatcher managing k servers.
     *
     * @param k       number of servers to create/manage
     * @param showViz whether to enable visualization (passed to superclass)
     */
    public LeastWorkDispatcher(int k, boolean showViz) {
        super(k, showViz);
    }

    /**
     * Choose the server whose Server.remainingWorkInQueue() is smallest.
     *
     * @param j the Job to dispatch (not modified)
     * @return the Server with minimal remaining work; ties broken by first
     *         encountered server in the list
     */
    @Override
    public Server pickServer(Job j) {
        Server best = null;
        double bestWork = Double.POSITIVE_INFINITY;
        for (Server s : getServerList()) {
            double work = s.remainingWorkInQueue();
            if (work < bestWork) {
                bestWork = work;
                best = s;
            }
        }
        return best;
    }

}
