import java.awt.event.MouseListener;
import java.lang.Thread.State;
import java.time.Duration;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

@SuppressWarnings("unchecked")
public class VoronoiGame {

    private VoronoiGraph graph;
    private VoronoiPlayerAlgorithm[] players;

    public VoronoiGame(int numPlayers) {
        graph = new VoronoiGraph();
        players = new VoronoiPlayerAlgorithm[numPlayers];
    }

    public VoronoiGame(int n, double density, int numPlayers) throws Exception {
        graph = new VoronoiGraph(n, density);
        players = new VoronoiPlayerAlgorithm[numPlayers];
    }

    public void setPlayerAlgorithm(int index, Class<? extends VoronoiPlayerAlgorithm> c) throws Exception {
        players[index] = c.getConstructor(VoronoiGraph.class).newInstance(graph);
    }

    public HashMap<Integer, Integer> getResults() {
        return graph.playerValues();
    }

    public void reset() {
        graph.reset();
    }

    private static class Box {
        Vertex v;
    }

    private static Box box = new Box();

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which class would you like to use for player 1? Note that this is case-sensitive.");
        String player1Class = scanner.nextLine();
        System.out.println("Which class would you like to use for player 2? Note that this is case-sensitive.");
        String player2Class = scanner.nextLine();
        VoronoiGraphDisplay vgd;
        int numPlayers = 2;
        Class<? extends VoronoiPlayerAlgorithm>[] players = new Class[] {
                (Class<? extends VoronoiPlayerAlgorithm>) Class.forName(player1Class),
                (Class<? extends VoronoiPlayerAlgorithm>) Class.forName(player2Class),
        };

        {
            // /**
            // * The visual test
            // */
            VoronoiGame vg = new VoronoiGame(numPlayers);

            vg.setPlayerAlgorithm(0, players[0]);
            vg.setPlayerAlgorithm(1, players[1]);

            vgd = new VoronoiGraphDisplay(vg.graph, 50);

            int turns = 2;
            for (int turn = 0; turn < turns; turn++) {
                final int turnNum = turn;
                for (int player = 0; player < numPlayers; player++) {
                    final int playerNum = player;
                    Thread toRun = new Thread(new Runnable() {
                        public void run() {
                            box.v = (vg.players[playerNum]).chooseVertex(playerNum, turns - turnNum - 1);
                        }
                    });
                    toRun.start();

                    CountDownLatch latch = new CountDownLatch(1);
                    vgd.win.addMouseListener(new MouseListener() {

                        @Override
                        public void mouseClicked(java.awt.event.MouseEvent e) {
                            latch.countDown();
                        }

                        @Override
                        public void mousePressed(java.awt.event.MouseEvent e) {
                        }

                        @Override
                        public void mouseReleased(java.awt.event.MouseEvent e) {
                        }

                        @Override
                        public void mouseEntered(java.awt.event.MouseEvent e) {
                        }

                        @Override
                        public void mouseExited(java.awt.event.MouseEvent e) {
                        }
                    });
                    latch.await();
                    if (toRun.getState() == State.TERMINATED) {
                        vg.graph.setToken((Vertex) box.v, player);
                    } else {
                        toRun.interrupt();
                        System.out.println("Player " + playerNum + " took too long and they lose their turn!");
                    }
                    box.v = null;
                    vgd.repaint();
                }
            }
            HashMap<Integer, Integer> results = vg.getResults();
            System.out.println("P1 Total: " + results.getOrDefault(0, 0));
            System.out.println("P2 Total: " + results.getOrDefault(1, 0));

            CountDownLatch latch = new CountDownLatch(1);
            vgd.win.addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    latch.countDown();
                }

                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {
                }

                @Override
                public void mouseReleased(java.awt.event.MouseEvent e) {
                }

                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                }
            });
            latch.await();
            vgd.win.setVisible(false);
            vgd.win.dispose();
        } // END OF VISUAL

        {
            /**
             * 100 random graphs with p1 first, then 100 random graphs with p2 first
             */
            int p1Wins = 0;
            int p2Wins = 0;
            int ties = 0;
            int games = 10;
            int numVertices = 100;
            double density = .1;
            int numTurns = 10;
            int[] turns = new int[numTurns];
            for (int i = 0; i < numTurns; i++) {
                turns[i] = i;
            }

            System.out.print("0% done testing");
            for (int game = 0; game < games; game++) {
                System.gc();
                final VoronoiGame vg = new VoronoiGame(numVertices, density, numPlayers);

                for (int i : new int[] { 0, 1 }) {
                    vg.reset();
                    Thread setPlayer0 = new Thread(new Runnable() {
                        public void run() {
                            try {
                                vg.setPlayerAlgorithm(0, players[i]);
                            } catch (Exception e) {
                                vg.players[0] = null;
                            }
                        }
                    });

                    setPlayer0.start();
                    setPlayer0.join(500);
                    setPlayer0.interrupt();

                    Thread setPlayer1 = new Thread(new Runnable() {
                        public void run() {
                            try {
                                vg.setPlayerAlgorithm(1, players[1 - i]);
                            } catch (Exception e) {
                                vg.players[0] = null;
                            }
                        }
                    });

                    setPlayer1.start();
                    setPlayer1.join(500);
                    setPlayer1.interrupt();

                    if (vg.players[0] == null && vg.players[1] == null) {
                        System.out.println("Neither player could instantiate! Game declared draw");
                        ties++;
                    } else if (vg.players[0] == null) {
                        System.out.println("Only player " + (1 - i) + " instantiated: they win by default");
                        if (i == 0)
                            p2Wins++;
                        else
                            p1Wins++;
                    } else if (vg.players[1] == null) {
                        System.out.println("Only player " + i + " instantiated: they win by default");
                        if (i == 0)
                            p1Wins++;
                        else
                            p2Wins++;
                    }

                    for (int turn : turns) {
                        for (int playerIdx : new int[] { 0, 1 }) {
                            Thread toRun = new Thread(new Runnable() {
                                public void run() {
                                    box.v = (vg.players[playerIdx]).chooseVertex(playerIdx,
                                            numTurns - turn - 1);
                                }
                            });

                            toRun.setPriority(Thread.MAX_PRIORITY);
                            toRun.start();
                            boolean res = toRun.join(Duration.ofMillis(500));
                            if (res)
                                vg.graph.setToken(box.v, playerIdx);
                            else {
                                toRun.interrupt();
                                System.out.println(
                                        "Player " + (playerIdx ^ i) + " took too long and they lose their turn!");
                            }
                            box.v = null;
                        }
                    }
                    HashMap<Integer, Integer> results = vg.getResults();
                    int p1val = results.getOrDefault(i, 0);
                    int p2val = results.getOrDefault(1 - i, 0);
                    if (p1val < p2val)
                        p2Wins++;
                    else if (p2val < p1val)
                        p1Wins++;
                    else
                        ties++;
                }
                System.out.print("\r" + (game * 10 + 10) + "% done testing");
            }
            System.out.println();

            System.out.println("P1Wins: " + p1Wins);
            System.out.println("P2Wins: " + p2Wins);
            System.out.println("Ties: " + ties);
        }

        scanner.nextLine();
        scanner.close();
        System.exit(0);
    }
}
