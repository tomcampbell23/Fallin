import Fallin.engine.CellTypes;
import Fallin.engine.GameEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameEngineTest {
    private GameEngine gameEngine;

    @BeforeEach
    void setUp() {
        gameEngine = new GameEngine(5, 10, 100);
    }

    @Test
    void testInitialGameState() {
        assertNotNull(gameEngine.getMap());
        assertEquals(10, gameEngine.getSize());
        assertEquals(5, gameEngine.getDifficulty());
        assertEquals(100, gameEngine.getStepLimit());
        assertNotNull(gameEngine.getPlayer());
        assertTrue(gameEngine.getPlayer().getX() == 0 && gameEngine.getPlayer().getY() == 0); // Player starts at entrance
        assertInstanceOf(CellTypes.Exit.class, gameEngine.getMap()[9][9].getType());
        assertInstanceOf(CellTypes.Entrance.class, gameEngine.getMap()[0][0].getType());
    }

    @Test
    void testMovePlayerY() {
        gameEngine.playerInteraction("u");
        assertTrue(gameEngine.getPlayer().getX() == 0 && gameEngine.getPlayer().getY() == 1);
        assertTrue(gameEngine.getMap()[0][1].getPlayer());
        assertFalse(gameEngine.getMap()[0][0].getPlayer());
        gameEngine.playerInteraction("d");
        assertTrue(gameEngine.getPlayer().getX() == 0 && gameEngine.getPlayer().getY() == 0);
        assertInstanceOf(CellTypes.Empty.class, gameEngine.getMap()[0][1].getType());
        assertFalse(gameEngine.getMap()[0][1].getPlayer());
        assertTrue(gameEngine.getMap()[0][0].getPlayer());

        String result = gameEngine.playerInteraction("d");
        assertEquals("down was unsuccessful. ", result);
        assertTrue(gameEngine.getPlayer().getX() == 0 && gameEngine.getPlayer().getY() == 0); // Player should not move.

        // Move player to x= 0, y= 9
        for (int i = 0; i < 9; i++) {
            gameEngine.playerInteraction("u");
        }
        result = gameEngine.playerInteraction("u");
        assertEquals("up was unsuccessful. ", result);
        assertTrue(gameEngine.getPlayer().getX() == 0 && gameEngine.getPlayer().getY() == 9); // Player should not move.
    }

    @Test
    void testMovePlayerX() {
        gameEngine.playerInteraction("r");
        assertTrue(gameEngine.getPlayer().getX() == 1 && gameEngine.getPlayer().getY() == 0);
        assertTrue(gameEngine.getMap()[1][0].getPlayer());
        assertFalse(gameEngine.getMap()[0][0].getPlayer());
        gameEngine.playerInteraction("l");
        assertTrue(gameEngine.getPlayer().getX() == 0 && gameEngine.getPlayer().getY() == 0);
        assertInstanceOf(CellTypes.Empty.class, gameEngine.getMap()[1][0].getType());
        assertTrue(gameEngine.getMap()[0][0].getPlayer());
        assertFalse(gameEngine.getMap()[1][0].getPlayer());

        String result = gameEngine.playerInteraction("l");
        assertEquals("left was unsuccessful. ", result);
        assertTrue(gameEngine.getPlayer().getX() == 0 && gameEngine.getPlayer().getY() == 0); // Player should not move.

        // Move player to x= 9, y= 0
        for (int i = 0; i < 9; i++) {
            gameEngine.playerInteraction("r");
        }
        result = gameEngine.playerInteraction("r");
        assertEquals("right was unsuccessful. ", result);
        assertTrue(gameEngine.getPlayer().getX() == 9 && gameEngine.getPlayer().getY() == 0); // Player should not move.
    }

    @Test
    void testCheckStateGameWon() {
        // Move player to exit (9, 9)
        for (int i = 0; i < 9; i++) {
            gameEngine.playerInteraction("r");
            gameEngine.playerInteraction("u");
        }
        assertNotNull(gameEngine.checkState());
        System.out.println(gameEngine.checkState());
        assertTrue(gameEngine.checkState().contains("You won")); // Will fail occasionally due to player dying as map is random.
    }

    @Test
    void testSaveAndLoadGame() {
        // Modify game state
        gameEngine.playerInteraction("u");
        gameEngine.playerInteraction("u");
        assertTrue(gameEngine.getPlayer().getX() == 0 && gameEngine.getPlayer().getY() == 2);

        // Save game state
        String saveMessage = gameEngine.save();
        assertEquals("Successfully saved the game.", saveMessage);

        // Modify game state
        gameEngine.playerInteraction("d");
        gameEngine.playerInteraction("r");
        assertEquals(4 ,gameEngine.getSteps());
        assertTrue(gameEngine.getPlayer().getX() == 1 && gameEngine.getPlayer().getY() == 1);

        // Load game state
        String loadMessage = gameEngine.load();
        assertEquals("Successfully loaded the game.", loadMessage);
        assertTrue(gameEngine.getPlayer().getX() == 0 && gameEngine.getPlayer().getY() == 2);
    }

    @Test
    void testHelp() {
        String helpMessage = gameEngine.help();
        assertNotNull(helpMessage);
        assertTrue(helpMessage.contains("You need to reach the exit"));
    }

    @Test
    void testGameOverStepsExceedLimit() {
        for (int i = 0; i < 50; i++) {
            gameEngine.playerInteraction("u");
            gameEngine.playerInteraction("d");
        }
        assertEquals("Game over! You ran out of stamina.", gameEngine.checkState());
    }

    @Test
    void testGameOverHealth() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                gameEngine.playerInteraction("u");
            }
            gameEngine.playerInteraction("r");
            for (int j = 0; j < 9; j++) {
                gameEngine.playerInteraction("d");
            }
            gameEngine.playerInteraction("r");
        }
        assertEquals("Game over! You died.", gameEngine.checkState());
    }
}