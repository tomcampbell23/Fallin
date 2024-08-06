package Fallin.engine;

import javafx.scene.layout.StackPane;

import java.io.Serializable;

/**
 * The Cell class represents a cell in the game grid.
 * It extends StackPane to utilise JavaFX for the GUI
 * and implements Serializable for saving the game state.
 */
public class Cell extends StackPane implements Serializable {
    private final int x, y;
    private CellTypes type;
    private boolean player;

    /**
     * Constructs a new Cell with the specified coordinates.
     * The default cell type is CellTypes.EMPTY.
     *
     * @param x the x-coordinate of the cell.
     * @param y the y-coordinate of the cell.
     */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.type = new CellTypes.Empty(); // Default cell type is empty
    }

    /**
     * Gets the type of the cell.
     *
     * @return the cell type.
     */
    public CellTypes getType() {
        return type;
    }

    /**
     * Sets the type of the cell.
     *
     * @param type the new cell type.
     */
    public void setType(CellTypes type) {
        this.type = type;
    }

    /**
     * Checks if the cell contains the player.
     *
     * @return true if the cell contains the player, false otherwise.
     */
    public boolean getPlayer() {
        return player;
    }

    /**
     * Sets the player presence in the cell.
     *
     * @param bool true if the cell contains a player, false otherwise.
     */
    public void setPlayer(boolean bool) {
        this.player = bool;
    }

    /**
     * Gets the x-coordinate of the cell.
     *
     * @return the x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the cell.
     *
     * @return the y-coordinate.
     */
    public int getY() {
        return y;
    }
}
