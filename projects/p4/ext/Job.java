/*
file name:      Job.java
Authors:        Ike Lage
last modified:  03/07/2024

Provides functionality of a Job object. 



Eg: 
Job job = new Job(1, 3); // this Job would arrive at time 1 and need 3 units of processing time

job.process(1, 2); // at time 2, we process this job for 1 unit of time.

System.out.println(job.getProcessingTimeRemaining()); // this will print 2, as there are still 2 units of processing time needed to finish this job.

job.process(2, 4); // at time 4, process this job for another 2 units of time.

System.out.println(job.isFinished()) // This prints true, as the job is now done.

System.out.println(job.timeInQueue()) // This prints 5, as the job arrived at time 1 and finished at time 6. 
*/

public class Job {

    private double arrivalTime;
    private double finishTime;
    private double processingTimeNeeded;
    private double processingTimeSpent;

    public Job(double arrivalTime, double processingTimeNeeded) {
        this.arrivalTime = arrivalTime;
        this.processingTimeNeeded = processingTimeNeeded;
        this.processingTimeSpent = 0.;
    }

    public double getArrivalTime() {
        return this.arrivalTime;
    }

    public double getProcessingTimeNeeded() {
        return this.processingTimeNeeded;
    }

    public double getProcessingTimeRemaining() {
        return this.processingTimeNeeded - this.processingTimeSpent;
    }

    public boolean isFinished() {
        return getProcessingTimeRemaining() <= 0;
    }

    public double timeInQueue() {
        return this.finishTime - this.arrivalTime;
    }

    public void process(double timeToProcessFor, double timeProcessingStarted) {
        double timeRemaining = getProcessingTimeRemaining();
        this.processingTimeSpent = this.processingTimeSpent + timeToProcessFor;
        if (this.isFinished()) {
            this.finishTime = timeProcessingStarted + timeRemaining;
        }
    }

    public String toString() {
        return "[Arrival: " + arrivalTime + ", Finish: " + finishTime + ", Time Needed: " + processingTimeNeeded
                + ", Time Spent: " + processingTimeSpent + "]";
    }

}