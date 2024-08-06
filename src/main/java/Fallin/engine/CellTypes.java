package Fallin.engine;

import java.io.Serializable;

/**
 * Represents a type of cell in the game.
 */
public interface CellTypes {

    /**
     * Gets the type of the cell.
     *
     * @return the cell type.
     */
    String getType();

    /**
     * Gets the string representation of the cell type.
     *
     * @return the string representation.
     */
    String getString();

    /**
     * Gets the URL of the image associated with the cell type.
     *
     * @return the image URL.
     */
    String getImage();

    /**
     * Represents an empty cell type.
     */
    class Empty implements CellTypes, Serializable {
        @Override
        public String getType() {
            return "EMPTY";
        }
        @Override
        public String getString() {
            return "____";
        }
        @Override
        public String getImage() {
            return "file:src/main/resources/Empty.png";
        }
    }

    /**
     * Represents the entrance cell type.
     */
    class Entrance implements CellTypes, Serializable {
        @Override
        public String getType() {
            return "ENTRANCE";
        }
        @Override
        public String getString() {
            return "ENTR";
        }
        @Override
        public String getImage() {
            return "file:src/main/resources/Entrance.png";
        }
    }

    /**
     * Represents the exit cell type.
     */
    class Exit implements CellTypes, Serializable {
        @Override
        public String getType() {
            return "EXIT";
        }
        @Override
        public String getString() {
            return "EXIT";
        }
        @Override
        public String getImage() {
            return "file:src/main/resources/Exit.png";
        }
    }

    /**
     * Represents the treasure cell type.
     */
    class Treasure implements CellTypes, Serializable {
        @Override
        public String getType() {
            return "TREASURE";
        }
        @Override
        public String getString() {
            return "TREA";
        }
        @Override
        public String getImage() {
            return "file:src/main/resources/Treasure.png";
        }
    }

    /**
     * Represents the medical unit cell type.
     */
    class MedicalUnit implements CellTypes, Serializable {
        @Override
        public String getType() {
            return "MEDICAL_UNIT";
        }
        @Override
        public String getString() {
            return "MEDI";
        }
        @Override
        public String getImage() {
            return "file:src/main/resources/Medical-kit.png";
        }
    }

    /**
     * Represents the trap cell type.
     */
    class Trap implements CellTypes, Serializable {
        @Override
        public String getType() {
            return "TRAP";
        }
        @Override
        public String getString() {
            return "TRAP";
        }
        @Override
        public String getImage() {
            return "file:src/main/resources/Trap.png";
        }
    }

    /**
     * Represents the mutant cell type.
     */
    class Mutant implements CellTypes, Serializable {
        @Override
        public String getType() {
            return "MUTANT";
        }
        @Override
        public String getString() {
            return "MUTA";
        }
        @Override
        public String getImage() {
            return "file:src/main/resources/Mutant.png";
        }
    }
}