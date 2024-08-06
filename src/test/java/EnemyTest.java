import Fallin.engine.Enemy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnemyTest {
    private Enemy enemy;

    @BeforeEach
    void setUp() {
        enemy = new Enemy(5, 5);
    }

    @Test
    void testInitialDamage() {
        assertEquals(4, enemy.getDamage());
    }

    @Test
    void testInitialHealth() {
        assertEquals(1, enemy.getHealth());
        assertEquals(1, enemy.getMaxHealth());
    }

    @Test
    void testMovement() {
        enemy.move("u");
        assertEquals(6, enemy.getY());
        enemy.move("d");
        assertEquals(5, enemy.getY());
        enemy.move("l");
        assertEquals(4, enemy.getX());
        enemy.move("r");
        assertEquals(5, enemy.getX());
    }
}
