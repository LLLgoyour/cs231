/*
file name:      WordCounter.java
Authors:        Ike Lage & Jack Dai
last modified:  10/31/2025

How to run:     java WordCounter
*/

import java.util.ArrayList;
import java.io.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;

public class WordCounter {

    private MapSet<String, Integer> wordCounts;
    private int wordCount;

    // constructor, where data_structure is either "bst" or "hashmap"
    public WordCounter(String data_structure) {
        if (data_structure.equals("BST")) {
            wordCounts = new BSTMap<String, Integer>();
        } else {
            assert data_structure.equals("HashMap") : "Invalid data structure";
            wordCounts = new HashMap<String, Integer>();
        }
    }

    // given the filename of a text file, read the text file and return an ArrayList
    // list of all the words in the file.
    public ArrayList<String> readWords(String filename) {

        ArrayList<String> words = new ArrayList<String>();

        try {
            // assign to a variable of type FileReader a new FileReader object, passing
            // filename to the constructor
            FileReader fr = new FileReader(filename);
            // assign to a variable of type BufferedReader a new BufferedReader, passing the
            // FileReader variable to the constructor
            BufferedReader br = new BufferedReader(fr);

            // assign to a variable of type String line the result of calling the readLine
            // method of your BufferedReader object.
            String line = br.readLine();
            // start a while loop that loops while line isn't null
            while (line != null) {
                // assign to line the result of calling the readLine method of your
                // BufferedReader object.
                String[] lineWords = line.split("[ ]+");
                for (String word : lineWords) {
                    words.add(word);
                }
                line = br.readLine();
            }
            // call the close method of the BufferedReader
            br.close();

            this.wordCount = words.size();

            return words;
        } catch (FileNotFoundException ex) {
            System.out.println("WordCounter.readWords():: unable to open file " + filename);
        } catch (IOException ex) {
            System.out.println("WordCounter.readWords():: error reading file " + filename);
        }

        return null;
    }

    // given an ArrayList of words, put the words into the map data structure.
    // Return the time taken in ms.
    public double buildMap(ArrayList<String> words) {

        long startTime = System.currentTimeMillis();

        for (String word : words) {

            if (this.wordCounts.containsKey(word)) {
                this.wordCounts.put(word, this.wordCounts.get(word) + 1);
            } else {
                this.wordCounts.put(word, 1);
            }
        }

        long totalTime = System.currentTimeMillis() - startTime;
        return totalTime;
    }

    // return the total word count from the last time readWords was called.
    public int totalWordCount() {
        return this.wordCount;
    }

    // return the unique word count
    public int uniqueWordCount() {
        return this.wordCounts.size();
    }

    // return the number of times the word occurred in the list of words.
    public int getCount(String word) {
        Integer count = this.wordCounts.get(word);
        if (count != null) {
            return count;
        }
        return 0;
    }

    // return the frequency of the word in the list of words.
    public int getFrequency(String word) {
        return this.getCount(word) / this.totalWordCount();
    }

    // clear the map data structure.
    public void clearMap() {
        this.wordCounts.clear();
        this.wordCount = 0;
    }

    // write a word count file given the current set of words in the data structure.
    public boolean writeWordCount(String filename) {

        try {
            // assign to a variable of type FileReader a new FileReader object, passing
            // filename to the constructor
            FileWriter fw = new FileWriter(filename);
            fw.write(Integer.toString(this.totalWordCount()) + "\n");
            ArrayList<String> keys = this.wordCounts.keySet();
            ArrayList<Integer> values = this.wordCounts.values();
            for (int i = 0; i < keys.size(); i++) {
                fw.write(keys.get(i) + " - " + Integer.toString(values.get(i)) + "\n");
            }
            fw.close();
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println("WordCounter.readWords():: unable to open file " + filename);
        } catch (IOException ex) {
            System.out.println("WordCounter.readWords():: error reading file " + filename);
        }
        return false;
    }

    public static void main(String[] args) {
        // Choose file for a specific year -- this is a sample for 2008
        String filename = "CLEANED_reddit_comments_2015.txt";

        // Use the BST
        WordCounter wc = new WordCounter("BST");

        // Get the words out of the file
        ArrayList<String> words = wc.readWords(filename);

        // Build the Map from the words
        wc.buildMap(words);

        // Write word counts to an output file
        wc.writeWordCount("output.txt");

    }

}