/*
 * file name: Server.java
 * author: Jack Dai
 * last modified: 10/11/2025
 * purpose of this class:
 * An individual Server needs to manage a Queue of Jobs, 
 * allowing for jobs to be processed in a FIFO (first-in-first-out) manner. 
 * Additionally, the Server needs to track system time (i.e. the time on the clock) 
 * and some summary statistics about the jobs it is processing that will be needed to analyze the simulation.
 * 
 */

import java.awt.*;

public class Server {
    private double sysTime;
    private double totalWaitingTime;
    private double remainingTime;
    private LinkedList<Job> queue;

    /**
     * this constructor initializes whatever fields the Server will need in order
     * for it to support the other methods. The Server can assume that the system
     * time, number of jobs processed, and total wait time will all start at 0 at
     * its initialization. The Queue of jobs will be empty.
     */
    public Server() {
        this.sysTime = 0.;
        this.totalWaitingTime = 0.;
        this.remainingTime = 0.;
        this.queue = new LinkedList<Job>();
    }

    /**
     * adds the specified Job job into the queue. This is a good place to update the
     * remainingTime field if you have one.
     * 
     * @param job
     */
    public void addJob(Job job) {
        // add to queue and update accounting fields
        this.queue.offer(job);
        this.remainingTime += job.getProcessingTimeNeeded();
    }

    /**
     * advances the system time of this queue to the specified time time. This
     * method must make sure that the elapsed time was spent processing jobs in the
     * queue. This method is a bit unintuitive to implement and there are some
     * important details to get right.
     * 
     * @param time
     */
    public void processTo(double time) {
        // don't go backwards in time
        if (time <= this.sysTime) {
            this.sysTime = Math.max(this.sysTime, time);
            return;
        }

        double timeLeft = time - this.sysTime;

        while (timeLeft > 0 && !this.queue.isEmpty()) {
            Job current = this.queue.peek();
            double toProcess = Math.min(current.getProcessingTimeRemaining(), timeLeft);

            // process the job starting at current sysTime
            current.process(toProcess, this.sysTime);

            // update accounting
            this.remainingTime -= toProcess;
            this.sysTime += toProcess;
            timeLeft -= toProcess;

            // if job finished, remove and update stats
            if (current.isFinished()) {
                this.queue.poll();
                this.totalWaitingTime += current.timeInQueue();
            }
        }

        // advance sysTime if there was idle time (no jobs to process)
        if (timeLeft > 0) {
            this.sysTime += timeLeft;
        }
    }

    /**
     * returns the total remaining processing time in this Server's queue (make sure
     * you store this as a class field. Otherwise your code will run too slow!)
     * 
     * @return the total remaining processing time in this Server's queue
     */
    public double remainingWorkInQueue() {
        return this.remainingTime;
    }

    /**
     * returns the total waiting time for all of the jobs processed by the server so
     * far (as above, store this as a class field).
     * 
     * @return the total waiting time for all of the jobs processed by the server so
     *         far
     */
    public double getTotalWaitingTime() {
        return this.totalWaitingTime;
    }

    /**
     * returns the number of Jobs in this Server's queue. (As above, make sure you
     * store this as a field. Otherwise your code will be too slow!)
     * 
     * @return the number of Jobs in this Server's queue.
     */
    public int size() {
        return this.queue.size();
    }

    /**
     * This is necessary for the GUI we've provided
     * 
     * @param g
     * @param c
     * @param loc
     * @param numberOfServers
     */
    public void draw(Graphics g, Color c, double loc, int numberOfServers) {
        double sep = (ServerFarmViz.HEIGHT - 20) / (numberOfServers + 2.0);
        g.setColor(Color.BLACK);
        g.setFont(new Font(g.getFont().getName(), g.getFont().getStyle(),
                (int) (72.0 * (sep * .5) / Toolkit.getDefaultToolkit().getScreenResolution())));
        g.drawString("Work: " + (remainingWorkInQueue() < 1000 ? remainingWorkInQueue() : ">1000"), 2,
                (int) (loc + .2 * sep));
        g.drawString("Jobs: " + (size() < 1000 ? size() : ">1000"), 5, (int) (loc + .55 * sep));
        g.setColor(c);
        g.fillRect((int) (3 * sep), (int) loc, (int) (.8 * remainingWorkInQueue()), (int) sep);
        g.drawOval(2 * (int) sep, (int) loc, (int) sep, (int) sep);
        if (remainingWorkInQueue() == 0)
            g.setColor(Color.GREEN.darker());
        else
            g.setColor(Color.RED.darker());
        g.fillOval(2 * (int) sep, (int) loc, (int) sep, (int) sep);
    }
}
