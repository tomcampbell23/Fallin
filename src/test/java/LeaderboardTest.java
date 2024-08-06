import Fallin.engine.Leaderboard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeaderboardTest {
    @TempDir
    Path tempDir;
    private Leaderboard leaderboard;
    private Path leaderboardFile;

    @BeforeEach
    void setUp() {
        leaderboardFile = tempDir.resolve("leaderboard.dat");
        leaderboard = new Leaderboard() {
            @Override
            public String getLeaderboardFilePath() {
                return leaderboardFile.toString();
            }
        };
    }

    @Test
    void testAddScore() {
        Leaderboard.ScoreEntry rank1 = leaderboard.addScore(100, LocalDate.now());

        assertEquals(1, rank1.getRank());
        assertEquals(1, leaderboard.addScore(200, LocalDate.now()).getRank());
        assertEquals(2, rank1.getRank());
        assertEquals(3, leaderboard.addScore(50, LocalDate.now()).getRank());

        List<Leaderboard.ScoreEntry> topScores = leaderboard.getTopScores();
        assertEquals(3, topScores.size());
        assertEquals(200, topScores.get(0).getScore());
        assertEquals(100, topScores.get(1).getScore());
        assertEquals(50, topScores.get(2).getScore());
    }

    @Test
    void testGetTopScores() {
        leaderboard.addScore(100, LocalDate.now());
        leaderboard.addScore(200, LocalDate.now());
        leaderboard.addScore(50, LocalDate.now());

        List<Leaderboard.ScoreEntry> topScores = leaderboard.getTopScores();
        assertEquals(3, topScores.size());
        assertEquals(200, topScores.get(0).getScore());
        assertEquals(100, topScores.get(1).getScore());
        assertEquals(50, topScores.get(2).getScore());
    }

    @Test
    void testReloadScores() {
        leaderboard.addScore(150, LocalDate.now());
        leaderboard.addScore(190, LocalDate.now());
        leaderboard.addScore(60, LocalDate.now());

        // Reload leaderboard to simulate restart
        leaderboard = new Leaderboard() {
            @Override
            public String getLeaderboardFilePath() {
                return leaderboardFile.toString();
            }
        };

        List<Leaderboard.ScoreEntry> topScores = leaderboard.getTopScores();
        assertEquals(3, topScores.size());
        assertEquals(190, topScores.get(0).getScore());
        assertEquals(150, topScores.get(1).getScore());
        assertEquals(60, topScores.get(2).getScore());
    }
}
