import java.util.*;

/*
 * file name: RandomDispatcher.java
 * author: Jack Dai
 * last modified: 10/22/2025
 * purpose of the class:
 * This dispatcher selects a server uniformly at random for each incoming
 * Job. The implementation uses a fixed seed (0) so behavior is deterministic
 * for tests.
 */

public class RandomDispatcher extends JobDispatcher {

    private Random rand;

    /**
     * Create a RandomDispatcher managing k servers.
     *
     * @param k       number of servers to create/manage
     * @param showViz whether to enable visualization (passed to superclass)
     */
    public RandomDispatcher(int k, boolean showViz) {
        super(k, showViz);
        this.rand = new Random(0); // deterministic seed for tests
    }

    /**
     * Selects a random server for the given job.
     *
     * @param j the Job to dispatch (not modified)
     * @return a randomly chosen Server from {@link #getServerList()}
     */
    @Override
    public Server pickServer(Job j) {
        int idx = rand.nextInt(getServerList().size());
        return getServerList().get(idx);
    }

}
