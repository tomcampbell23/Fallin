import Fallin.engine.CellTypes;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CellTypesTest {

    @Test
    void testEmpty() {
        CellTypes empty = new CellTypes.Empty();
        assertEquals("EMPTY", empty.getType());
        assertEquals("____", empty.getString());
        assertEquals("file:src/main/resources/Empty.png", empty.getImage());
    }

    @Test
    void testEntrance() {
        CellTypes entrance = new CellTypes.Entrance();
        assertEquals("ENTRANCE", entrance.getType());
        assertEquals("ENTR", entrance.getString());
        assertEquals("file:src/main/resources/Entrance.png", entrance.getImage());
    }

    @Test
    void testExit() {
        CellTypes exit = new CellTypes.Exit();
        assertEquals("EXIT", exit.getType());
        assertEquals("EXIT", exit.getString());
        assertEquals("file:src/main/resources/Exit.png", exit.getImage());
    }

    @Test
    void testTreasure() {
        CellTypes treasure = new CellTypes.Treasure();
        assertEquals("TREASURE", treasure.getType());
        assertEquals("TREA", treasure.getString());
        assertEquals("file:src/main/resources/Treasure.png", treasure.getImage());
    }

    @Test
    void testMedicalUnit() {
        CellTypes medicalUnit = new CellTypes.MedicalUnit();
        assertEquals("MEDICAL_UNIT", medicalUnit.getType());
        assertEquals("MEDI", medicalUnit.getString());
        assertEquals("file:src/main/resources/Medical-kit.png", medicalUnit.getImage());
    }

    @Test
    void testTrap() {
        CellTypes trap = new CellTypes.Trap();
        assertEquals("TRAP", trap.getType());
        assertEquals("TRAP", trap.getString());
        assertEquals("file:src/main/resources/Trap.png", trap.getImage());
    }

    @Test
    void testMutant() {
        CellTypes mutant = new CellTypes.Mutant();
        assertEquals("MUTANT", mutant.getType());
        assertEquals("MUTA", mutant.getString());
        assertEquals("file:src/main/resources/Mutant.png", mutant.getImage());
    }
}
