/*
file name:      ServerTests.java
Authors:        Ike Lage
last modified:  03/12/2025

How to run:     java -ea ServerTests
*/

//See worked example the tests are derived from here: 
//https://docs.google.com/presentation/d/1C4EWP6t8PDN8dXzG59pZVAzKFfb2Hgvi78Rb1l1mr4M/edit#slide=id.g2905fa579fe_0_26

public class ServerTests {

    public static double serverTests() {

        double score = 0;

        Server server = new Server();

        Job j1 = new Job(0, 3.5);
        server.processTo(j1.getArrivalTime());
        server.addJob(j1);
        System.out.println(server.remainingWorkInQueue() + " == 3.5");
        assert server.remainingWorkInQueue() == 3.5;
        if (server.remainingWorkInQueue() == 3.5) {
            score += 0.5;
        }

        Job j2 = new Job(2.5, 0.5);
        server.processTo(j2.getArrivalTime());
        server.addJob(j2);
        System.out.println(server.remainingWorkInQueue() + " == 1.5");
        assert server.remainingWorkInQueue() == 1.5;
        if (server.remainingWorkInQueue() == 1.5) {
            score += 0.5;
        }

        Job j3 = new Job(4.5, 1.5);
        server.processTo(j3.getArrivalTime());
        server.addJob(j3);
        System.out.println(server.remainingWorkInQueue() + " == 1.5");
        assert server.remainingWorkInQueue() == 1.5;
        if (server.remainingWorkInQueue() == 1.5) {
            score += 0.5;
        }

        Job j4 = new Job(5.0, 2.0);
        server.processTo(j4.getArrivalTime());
        server.addJob(j4);
        System.out.println(server.remainingWorkInQueue() + " == 3.0");
        assert server.remainingWorkInQueue() == 3.0;
        if (server.remainingWorkInQueue() == 3.0) {
            score += 0.5;
        }

        // Finish up the last job
        server.processTo(j4.getArrivalTime() + j4.getProcessingTimeNeeded() + server.remainingWorkInQueue());
        System.out.println(server.remainingWorkInQueue() + " == 0.");
        assert server.remainingWorkInQueue() == 0;
        if (server.remainingWorkInQueue() == 0.) {
            score += 0.5;
        }

        // Compute total waiting time
        // Note, waiting time includes processing time and time spent waiting to be
        // processed
        // This isn't worked out in the slides, but you have enough information to do it
        // yourself if you'd like
        System.out.println(server.getTotalWaitingTime() + " == 9.5");
        assert server.getTotalWaitingTime() == 9.5;
        if (server.getTotalWaitingTime() == 9.5) {
            score += 0.5;
        }

        return score;
    }

    public static void main(String[] args) {
        System.out.println(serverTests());
    }

}