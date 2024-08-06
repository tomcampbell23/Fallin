import Fallin.engine.Cell;
import Fallin.engine.CellTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CellTest {
    private Cell cell;

    @BeforeEach
    void setUp() {
        cell = new Cell(1, 1);
    }

    @Test
    void testInitialState() {
        assertEquals(1, cell.getX());
        assertEquals(1, cell.getY());
        assertInstanceOf(CellTypes.Empty.class, cell.getType());
        assertFalse(cell.getPlayer());
    }

    @Test
    void testSetType() {
        CellTypes newType = new CellTypes.Entrance();
        cell.setType(newType);
        assertEquals(newType, cell.getType());
        assertInstanceOf(CellTypes.Entrance.class, cell.getType());
    }

    @Test
    void testSetPlayer() {
        cell.setPlayer(true);
        assertTrue(cell.getPlayer());

        cell.setPlayer(false);
        assertFalse(cell.getPlayer());
    }
}
