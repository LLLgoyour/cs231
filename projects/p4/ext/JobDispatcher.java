/*
 * file name: JobDispatcher.java
 * author: Jack Dai
 * last modified: 10/11/2025
 * purpose of the class:
 * Abstract dispatcher that manages a collection of Servers and routes Jobs to
 * a chosen Server according to a dispatch policy implemented in subclasses.
 */

import java.util.*;
import java.awt.*;

public abstract class JobDispatcher {

    protected ArrayList<Server> servers;
    protected double sysTime;
    protected ServerFarmViz viz;
    protected LinkedList<Job> jobsHandled;

    /**
     *
     * constructs a JobDispatcher with k total Servers.
     *
     * @param k
     * @param showViz
     */
    public JobDispatcher(int k, boolean showViz) {
        this.servers = new ArrayList<Server>();
        for (int i = 0; i < k; i++) {
            this.servers.add(new Server());
        }
        this.sysTime = 0.;
        this.jobsHandled = new LinkedList<Job>();
        this.viz = new ServerFarmViz(this, showViz);
    }

    /**
     * returns the system time
     * 
     * @return the system time
     */
    public double getTime() {
        return this.sysTime;
    }

    /**
     * returns the collection of Servers
     * 
     * @return the collection of Servers
     */
    public ArrayList<Server> getServerList() {
        return this.servers;
    }

    /**
     * updates the system time to the specified time and calls processTo on each
     * server.
     * 
     * @param time
     */
    public void advanceTimeTo(double time) {
        // ensure we don't move time backwards for servers; servers handle that
        for (Server s : this.servers) {
            s.processTo(time);
        }
        this.sysTime = time;
    }

    /**
     * handle an incoming job: record it, advance time to arrival, repaint viz,
     * pick a server and add the job, repaint again
     * 
     * @param job
     */
    public void handleJob(Job job) {
        this.jobsHandled.add(job);
        advanceTimeTo(job.getArrivalTime());
        if (this.viz != null)
            this.viz.repaint();
        Server s = pickServer(job);
        s.addJob(job);
        if (this.viz != null)
            this.viz.repaint();
    }

    /**
     * advances the time to the earliest point when all jobs will have completed.
     */
    public void finishUp() {
        double maxRemaining = 0.;
        for (Server s : this.servers) {
            double rem = s.remainingWorkInQueue();
            if (rem > maxRemaining)
                maxRemaining = rem;
        }
        advanceTimeTo(this.sysTime + maxRemaining);
    }

    /**
     * returns number of jobs handled
     * 
     * @return number of jobs handled
     */
    public int getNumJobsHandled() {
        return this.jobsHandled.size();
    }

    /**
     * returns average waiting time across handled jobs
     * 
     * @return average waiting time across handled jobs
     */
    public double getAverageWaitingTime() {
        if (this.jobsHandled.isEmpty())
            return 0.;
        double tot = 0.;
        for (Job j : this.jobsHandled) {
            tot += j.timeInQueue();
        }
        return tot / this.jobsHandled.size();
    }

    /**
     * pickServer is abstract, concrete dispatchers implement their policy
     * 
     * @param j
     * @return
     */
    public abstract Server pickServer(Job j);

    /**
     * draw method used by GUI
     */
    public void draw(Graphics g) {
        double sep = (ServerFarmViz.HEIGHT - 20) / (getServerList().size() + 2.0);
        g.drawString("Time: " + getTime(), (int) sep, ServerFarmViz.HEIGHT - 20);
        g.drawString("Jobs handled: " + getNumJobsHandled(), (int) sep, ServerFarmViz.HEIGHT - 10);
        for (int i = 0; i < getServerList().size(); i++) {
            getServerList().get(i).draw(g, (i % 2 == 0) ? Color.GRAY : Color.DARK_GRAY, (i + 1) * sep,
                    getServerList().size());
        }
    }

}