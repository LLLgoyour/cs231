
/**
 * file name: Experiments.java
 * author: Jack Dai
 * last modified: 11/01/2025
 *
 * Purpose: Run a small set of experiments comparing two Map implementations
 * (BSTMap and HashMap) on two text datasets (reddit comments and
 * Shakespeare). For each combination the program computes:
 *  - total and unique word counts
 *  - top-N most frequent words
 *  - average build time (ms) over several runs
 *  - maxDepth of the underlying data structure after building
 *
 * Results are written to `report.txt` in the program working directory.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Experiment runner utility.
 *
 * This class provides small helper methods to read tokenized words from a
 * file, build a Map-based frequency table, compute timing statistics and
 * extract the top-N most frequent words. The {@link #main(String[])} method
 * ties everything together and writes a human-readable report.
 */
public class Experiments {

    /**
     * Read words from a text file. Splits on whitespace (space, tab, newline,
     * carriage return). Empty tokens are ignored.
     *
     * @param filename path to the input text file
     * @return ArrayList of tokens (may be empty if the file is missing or has
     *         no tokens)
     */
    // read words from file (split on whitespace)
    public static ArrayList<String> readWords(String filename) {
        ArrayList<String> words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("[ \\t\\n\\r]+");
                for (String w : parts) {
                    if (w.length() > 0)
                        words.add(w);
                }
            }
        } catch (IOException e) {
            System.out.println("Unable to read file: " + filename + " -> " + e.getMessage());
        }
        return words;
    }

    /**
     * Build a frequency map from the given list of words, timing the
     * insertion process.
     *
     * @param map   a fresh, empty MapSet to populate
     * @param words list of tokens to insert
     * @return elapsed time in milliseconds spent inserting all words
     */
    // build map and return time in ms
    public static double buildMap(MapSet<String, Integer> map, ArrayList<String> words) {
        long start = System.currentTimeMillis();
        for (String word : words) {
            if (map.containsKey(word)) {
                map.put(word, map.get(word) + 1);
            } else {
                map.put(word, 1);
            }
        }
        long elapsed = System.currentTimeMillis() - start;
        return (double) elapsed;
    }

    /**
     * Measure average build time by creating fresh instances of the provided
     * map's implementation and timing builds for the provided number of runs.
     *
     * @param mapPrototype prototype instance used only to detect implementation
     * @param words        list of tokens to insert
     * @param runs         number of repeated runs to average
     * @return average elapsed time in milliseconds
     */
    // average build time over runs
    public static double averageBuildTime(MapSet<String, Integer> mapPrototype, ArrayList<String> words, int runs) {
        double total = 0.0;
        for (int i = 0; i < runs; i++) {
            // create a fresh instance of the same class as mapPrototype
            MapSet<String, Integer> map;
            if (mapPrototype instanceof BSTMap) {
                map = new BSTMap<String, Integer>();
            } else {
                map = new HashMap<String, Integer>();
            }
            double t = buildMap(map, words);
            total += t;
        }
        return total / runs;
    }

    /**
     * Return the top-N most frequent words from a populated map as strings
     * formatted "word - count" (sorted by count descending).
     *
     * @param map populated MapSet
     * @param n   number of top entries to return
     * @return list of formatted "word - count" strings
     */
    // get top N words from the built map
    public static ArrayList<String> topN(MapSet<String, Integer> map, int n) {
        ArrayList<MapSet.KeyValuePair<String, Integer>> entries = map.entrySet();
        Collections.sort(entries, new Comparator<MapSet.KeyValuePair<String, Integer>>() {
            @Override
            public int compare(MapSet.KeyValuePair<String, Integer> a, MapSet.KeyValuePair<String, Integer> b) {
                return b.getValue().compareTo(a.getValue());
            }
        });
        ArrayList<String> out = new ArrayList<>();
        int limit = Math.min(n, entries.size());
        for (int i = 0; i < limit; i++) {
            MapSet.KeyValuePair<String, Integer> kv = entries.get(i);
            out.add(kv.getKey() + " - " + kv.getValue());
        }
        return out;
    }

    /**
     * Program entry point. Reads both datasets, runs experiments for BSTMap and
     * HashMap, then writes results to `report.txt`.
     *
     * @param args ignored
     */
    public static void main(String[] args) {
        String reddit = "CLEANED_reddit_comments_2015.txt";
        String shakespeare = "CLEANED_shakespeare.txt";
        int runs = 5;

        ArrayList<String> redditWords = readWords(reddit);
        ArrayList<String> shakespeareWords = readWords(shakespeare);

        StringBuilder report = new StringBuilder();
        report.append("Word count analysis report\n");
        report.append("===========================\n\n");

        // For each data structure, run analyses on both datasets
        for (String ds : new String[] { "BST", "HashMap" }) {
            report.append("Data structure: " + ds + "\n\n");

            if (ds.equals("BST")) {
                // Reddit
                BSTMap<String, Integer> m = new BSTMap<>();
                double avg = averageBuildTime(m, redditWords, runs);
                // build once to capture topN and stats
                m.clear();
                buildMap(m, redditWords);
                ArrayList<String> top10 = topN(m, 10);
                report.append("Reddit (" + reddit + ")\n");
                report.append("  total words: " + redditWords.size() + "\n");
                report.append("  unique words: " + m.size() + "\n");
                report.append(String.format("  avg build time over %d runs: %.2f ms\n", runs, avg));
                report.append("  maxDepth: " + m.maxDepth() + "\n");
                report.append("  top 10 words:\n");
                for (String s : top10)
                    report.append("    " + s + "\n");
                report.append("\n");

                // Shakespeare
                m = new BSTMap<>();
                avg = averageBuildTime(m, shakespeareWords, runs);
                m.clear();
                buildMap(m, shakespeareWords);
                top10 = topN(m, 10);
                report.append("Shakespeare (" + shakespeare + ")\n");
                report.append("  total words: " + shakespeareWords.size() + "\n");
                report.append("  unique words: " + m.size() + "\n");
                report.append(String.format("  avg build time over %d runs: %.2f ms\n", runs, avg));
                report.append("  maxDepth: " + m.maxDepth() + "\n");
                report.append("  top 10 words:\n");
                for (String s : top10)
                    report.append("    " + s + "\n");
                report.append("\n-------------------------------\n\n");

            } else {
                // HashMap
                HashMap<String, Integer> h = new HashMap<>();
                double avg = averageBuildTime(h, redditWords, runs);
                h.clear();
                buildMap(h, redditWords);
                ArrayList<String> top10 = topN(h, 10);
                report.append("Reddit (" + reddit + ")\n");
                report.append("  total words: " + redditWords.size() + "\n");
                report.append("  unique words: " + h.size() + "\n");
                report.append(String.format("  avg build time over %d runs: %.2f ms\n", runs, avg));
                report.append("  maxDepth: " + h.maxDepth() + "\n");
                report.append("  top 10 words:\n");
                for (String s : top10)
                    report.append("    " + s + "\n");
                report.append("\n");

                h = new HashMap<>();
                avg = averageBuildTime(h, shakespeareWords, runs);
                h.clear();
                buildMap(h, shakespeareWords);
                top10 = topN(h, 10);
                report.append("Shakespeare (" + shakespeare + ")\n");
                report.append("  total words: " + shakespeareWords.size() + "\n");
                report.append("  unique words: " + h.size() + "\n");
                report.append(String.format("  avg build time over %d runs: %.2f ms\n", runs, avg));
                report.append("  maxDepth: " + h.maxDepth() + "\n");
                report.append("  top 10 words:\n");
                for (String s : top10)
                    report.append("    " + s + "\n");
                report.append("\n-------------------------------\n\n");
            }
        }

        // write report
        try (FileWriter fw = new FileWriter("report.txt")) {
            fw.write(report.toString());
            System.out.println("Experiments complete. report.txt written.");
        } catch (IOException e) {
            System.out.println("Unable to write report.txt: " + e.getMessage());
        }
    }
}
