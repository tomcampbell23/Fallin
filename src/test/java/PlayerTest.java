import Fallin.engine.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player(3, 3);
    }

    @Test
    void testInitialHealth() {
        assertEquals(10, player.getHealth());
        assertEquals(10, player.getMaxHealth());
    }

    @Test
    void testInitialDamage() {
        assertEquals(2, player.getDamage());
    }

    @Test
    void testMovement() {
        player.move("u");
        assertEquals(4, player.getY());
        player.move("d");
        assertEquals(3, player.getY());
        player.move("l");
        assertEquals(2, player.getX());
        player.move("r");
        assertEquals(3, player.getX());
    }
}
