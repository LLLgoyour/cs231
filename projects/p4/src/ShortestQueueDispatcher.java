/*
 * file name: ShortestQueueDispatcher.java
 * author: Jack Dai
 * last modified: 10/22/2025
 * Purpose of the class:
 * This is a dispatcher that implements the shortest-queue policy: when a new Job
 * arrives it is assigned to the Server whose queue currently contains the
 * fewest jobs.
 */

public class ShortestQueueDispatcher extends JobDispatcher {

    /**
     * Create a ShortestQueueDispatcher for k servers.
     *
     * @param k       the number of servers to create/manage
     * @param showViz whether to enable visualization (passed to superclass)
     */
    public ShortestQueueDispatcher(int k, boolean showViz) {
        super(k, showViz);
    }

    /**
     * Choose a server for the given Job according to the shortest-queue rule.
     *
     * @param j the Job to dispatch (not modified)
     * @return the Server whose queue currently has the fewest jobs
     */
    @Override
    public Server pickServer(Job j) {
        Server best = null;
        int bestSize = Integer.MAX_VALUE;
        for (Server s : getServerList()) {
            int sz = s.size();
            if (sz < bestSize) {
                bestSize = sz;
                best = s;
            }
        }
        return best;
    }

}
