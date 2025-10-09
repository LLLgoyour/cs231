/*
file name:      JobMaker.java
Authors:        Max Bender, Modified by Ike Lage
last modified:  03/07/2024
*/

import java.util.Iterator;
import java.util.Random;

public class JobMaker implements Iterable<Job>, Iterator<Job> {

    private double meanArrivalTime;
    private double meanProcessingTime;
    private double curTime;

    public JobMaker(double meanArrivalTime, double meanProcessingTime) {
        this.meanArrivalTime = meanArrivalTime;
        this.meanProcessingTime = meanProcessingTime;
        this.curTime = 0;
    }

    public double nextExponential(double mean) {
        Random r = new Random();
        double val = -mean * Math.log(r.nextDouble());
        double valAdjusted = (1.0 * ((int) (val * 128))) / 128;
        valAdjusted = Math.max(valAdjusted, 1.0 / 128);
        return valAdjusted;
    }

    public Job getNextJob() {
        // Get total processing time of job
        double jobProcessingTime = nextExponential(this.meanProcessingTime);
        // jobProcessingTime = 5 ;
        Job nextJob = new Job(this.curTime, jobProcessingTime);
        // Set arrival time of the following job
        this.curTime += nextExponential(this.meanArrivalTime);
        return nextJob;
    }

    public boolean hasNext() {
        return true;
    }

    public Job next() {
        return getNextJob();
    }

    public Iterator<Job> iterator() {
        return this;
    }

}