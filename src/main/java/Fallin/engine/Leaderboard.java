package Fallin.engine;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * The Leaderboard class manages the leaderboard for the game, maintaining a list of high scores with associated dates.
 * It allows adding new scores, retrieving the top scores, and saving/loading the scores to/from a file.
 */
public class Leaderboard implements Serializable {
    private List<ScoreEntry> scores;
    private static final String leaderboardFile = "leaderboard.dat";
    private final int scoresToSave = 20;

    /**
     * Constructs a new Leaderboard and loads scores from the file.
     */
    public Leaderboard() {
        scores = new ArrayList<>();
        loadScores();
    }

    /**
     * Adds a new score to the leaderboard.
     *
     * @param score the score to add.
     * @param date the date of the score.
     */
    public ScoreEntry addScore(int score, LocalDate date) {
        ScoreEntry entry = new ScoreEntry(score, date);
        scores.add(entry);
        scores.sort(Comparator.comparingInt(ScoreEntry::getScore).reversed()); // Sort scores array by score in descending order.

        // Update ranks based on sorted order.
        for (int i = 0; i < scores.size(); i++) {
            scores.get(i).setRank(i + 1);
        }

        // Only save scoresToSave number of scores.
        if (scores.size() > scoresToSave) {
            scores = new ArrayList<>(scores.subList(0, scoresToSave));
        }
        saveScores();
        return entry;
    }

    /**
     * Retrieves the top scores from the leaderboard.
     *
     * @return an array list of the top scores.
     */
    public List<ScoreEntry> getTopScores() {
        return new ArrayList<>(scores);
    }

    /**
     * Gets the scoresToSave value.
     *
     * @return the amount of scores to save in the leaderboard.
     */
    public int getScoresToSave() {
        return scoresToSave;
    }

    public String getLeaderboardFilePath() {
        return leaderboardFile;
    }

    /**
     * Loads scores from the leaderboardFile into the leaderboard.
     */
    private void loadScores() {
        try {
            ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(
                    new FileInputStream(getLeaderboardFilePath())));
            try {
                this.scores = (List<ScoreEntry>) input.readObject();
            } catch (Throwable e) {
                try {
                    input.close();
                } catch (Throwable e2) {
                    e.addSuppressed(e2);
                }

                throw e;
            }
        } catch (FileNotFoundException e3) {
            // Leaderboard file not found, start with empty leaderboard.
            scores = new ArrayList<>();
        } catch (ClassNotFoundException | IOException e4) {
            System.out.println("IO Error has occurred, leaderboard file may not be compatible: "+ e4.getMessage());
        }
    }

    /**
     * Saves the current scores to the leaderboardFile.
     */
    private void saveScores() {
        try {
            ObjectOutputStream output = new ObjectOutputStream(new BufferedOutputStream(
                    new FileOutputStream(getLeaderboardFilePath())));
            try {
                output.writeObject(scores);
            } catch (Throwable e) {
                try {
                    output.close();
                } catch (Throwable e2) {
                    e.addSuppressed(e2);
                }
                throw e;
            }
            output.close();
        } catch (IOException e3) {
            System.out.println("IO Error has occurred while saving leaderboard: " + e3.getMessage());
        }
    }

    /**
     * The ScoreEntry class represents a single entry in the leaderboard, containing a score, rank, and date.
     */
    public static class ScoreEntry implements Serializable {
        private int rank;
        private final int score;
        private final LocalDate date;

        /**
         * Constructs a new ScoreEntry.
         *
         * @param score the score of the entry.
         * @param date the date of the score.
         */
        public ScoreEntry(int score, LocalDate date) {
            this.score = score;
            this.date = date;
        }

        /**
         * Gets the rank of the current ScoreEntry.
         *
         * @return the rank.
         */
        public int getRank() {
            return rank;
        }

        /**
         * Gets the score of the current ScoreEntry.
         *
         * @return the score.
         */
        public int getScore() {
            return score;
        }

        /**
         * Gets the date of the current ScoreEntry.
         *
         * @return the date.
         */
        public LocalDate getDate() {
            return date;
        }

        /**
         * Sets the rank of the current ScoreEntry.
         *
         * @param rank the new rank.
         */
        private void setRank(int rank) {
            this.rank = rank;
        }
    }
}
