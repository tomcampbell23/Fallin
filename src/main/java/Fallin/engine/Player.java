package Fallin.engine;

/**
 * The Player class represents a player character in the game, which extends the Person class.
 * A player has specific health and damage attributes.
 */
public class Player extends Person {

    /**
     * Creates a new player at the specified initial position.
     *
     * @param x the initial x-coordinate of the player.
     * @param y the initial y-coordinate of the player.
     *
     */
    public Player(int x, int y) {
        super(x, y);
        this.setMaxHealth(10);
        this.setHealth(10);
        this.setDamage(2);
    }
}