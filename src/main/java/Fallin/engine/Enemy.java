package Fallin.engine;

/**
 * The Enemy class represents an enemy character in the game, which extends the Person class.
 * An enemy has specific damage attributes and can move randomly on the game map.
 */
public class Enemy extends Person {

    /**
     * Constructs a new Enemy with the specified initial position.
     * The enemy is initialised with a damage amount of 4.
     *
     * @param x the initial x-coordinate of the enemy.
     * @param y the initial y-coordinate of the enemy.
     */
    public Enemy(int x, int y) {
        super(x, y);
        this.setDamage(4);
        this.setMaxHealth(1);
        this.setHealth(1);
    }
}