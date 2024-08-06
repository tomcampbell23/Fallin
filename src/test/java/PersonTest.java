import Fallin.engine.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonTest {
    private Person p;

    @BeforeEach
    void setUp() {
        p = new Person(4,4);
    }

    @Test
    void testMoveY() {
        String result = p.move("u");
        assertEquals(5, p.getY());
        assertEquals("up was successful.", result);

        p.move("d");
        result = p.move("d");
        assertEquals(3, p.getY());
        assertEquals("down was successful.", result);
    }

    @Test
    void testMoveX() {
        String result = p.move("r");
        assertEquals(5, p.getX());
        assertEquals("right was successful.", result);

        p.move("l");
        result = p.move("l");
        assertEquals(3, p.getX());
        assertEquals("left was successful.", result);
    }

    @Test
    void testTakeDamage() {
        assertEquals(10, p.getHealth());
        p.takeDamage(3);
        assertEquals(7, p.getHealth());
    }

    @Test
    void testHeal() {
        p.takeDamage(5);
        assertEquals(5, p.getHealth());
        p.heal(3);
        assertEquals(8, p.getHealth());

        p.heal(10);
        assertEquals(p.getMaxHealth(), p.getHealth());
    }
}