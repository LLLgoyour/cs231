/*
file name:      DispatcherTests.java
Authors:        Ike Lage
last modified:  03/12/2025

How to run:     java -ea DispatcherTests
*/

public class DispatcherTests {

    public static double dispatcherTests() {
        double testScore = 0.;

        /**
         * Single Server Test
         */
        {
            // Note, since there's one server, it doesn't matter which specific dispatcher
            // we use
            JobDispatcher jd = new RandomDispatcher(1, false);
            Job[] jobs = new Job[5];

            for (int i = 0; i < jobs.length; i++)
                jd.handleJob(jobs[i] = new Job(i, 2 * i + 1));
            jd.finishUp();

            System.out.println(jd.getAverageWaitingTime() + " == 9");
            assert jd.getAverageWaitingTime() == 9.0;
            if (jd.getAverageWaitingTime() == 9)
                testScore += 4;
        }

        /**
         * Multiple Server Tests
         */
        {
            // Round robin Dispatcher
            JobDispatcher jd = new RoundRobinDispatcher(2, false);

            // Some jobs that produce different behaviors for the 3 deterministic servers
            jd.handleJob(new Job(0, 10.0));
            jd.handleJob(new Job(1., 2.0));
            jd.handleJob(new Job(2., 2.0));
            jd.handleJob(new Job(4., 1.0));
            jd.handleJob(new Job(5., 7.0));

            jd.finishUp();

            System.out.println(jd.getAverageWaitingTime() + " == 7.4");
            assert jd.getAverageWaitingTime() == 7.4;
            if (jd.getAverageWaitingTime() == 7.4)
                testScore++;
        }
        {
            // ShortestQueue Dispatcher
            JobDispatcher jd = new ShortestQueueDispatcher(2, false);

            // Some jobs that produce different behaviors for the 3 deterministic servers
            jd.handleJob(new Job(0, 10.0));
            jd.handleJob(new Job(1., 2.0));
            jd.handleJob(new Job(2., 2.0));
            jd.handleJob(new Job(4., 1.0));
            jd.handleJob(new Job(5., 7.0));

            jd.finishUp();

            System.out.println(jd.getAverageWaitingTime() + " == 6.0");
            assert jd.getAverageWaitingTime() == 6.0;
            if (jd.getAverageWaitingTime() == 6.0)
                testScore++;
        }
        {
            // LeastWork Dispatcher
            JobDispatcher jd = new LeastWorkDispatcher(2, false);

            // Some jobs that produce different behaviors for the 3 deterministic servers
            jd.handleJob(new Job(0, 10.0));
            jd.handleJob(new Job(1., 2.0));
            jd.handleJob(new Job(2., 2.0));
            jd.handleJob(new Job(4., 1.0));
            jd.handleJob(new Job(5., 7.0));

            jd.finishUp();

            System.out.println(jd.getAverageWaitingTime() + " == 5.0");
            assert jd.getAverageWaitingTime() == 5.0;
            if (jd.getAverageWaitingTime() == 5.0)
                testScore++;
        }

        return testScore;
    }

    public static void main(String[] args) {
        System.out.println(dispatcherTests());
    }

}
